package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.PurchaseEvent;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class PurchasingStatisticsReportTest {
	private PurchasingStatisticsReport eventReport = new PurchasingStatisticsReport();
	private List<LogEvent> correctLogEvents;
	private static final int numberOfPurchases = 5;
	private static int averagePurhcaseAmount = 0;
	private static Double averagePurchaseAmount = 0.0;
	private static Double averagePricePerItem = 0.0;
	private static Double averageItemsPerPurchase = 0.0;
	private static JSONObject expectedReport;
	
	@Before
	public void setup() {
		correctLogEvents = new ArrayList<LogEvent>();
		Random rand = new Random();
		Double totalAmount = 0.0;
		Double totalItems = 0.0;

		for(int i = 1 ; i < numberOfPurchases+1; i++) {
			int noItems = rand.nextInt(i*10);
			Double noAmount = rand.nextDouble()*i*100;
			correctLogEvents.add(new PurchaseEvent(i + "gmail.com", noItems, noAmount));
			totalAmount +=noAmount;
			totalItems += noItems;
		}
		DecimalFormat df = new DecimalFormat("#.##");
		averagePurchaseAmount = Double.valueOf(df.format(totalAmount / numberOfPurchases));
		averagePricePerItem =  Double.valueOf(df.format(totalAmount / totalItems));
		averageItemsPerPurchase =  Double.valueOf(df.format(totalItems /numberOfPurchases));	
	}
	
	private void setupExpectedReport() {
		expectedReport = new JSONObject();
		if(numberOfPurchases > 0) {
			try {
				expectedReport.put("averagePurchaseAmount", averagePurchaseAmount);
				expectedReport.put("averagePricePerItem", averagePricePerItem);
				expectedReport.put("averageItemsPerPurchase", averageItemsPerPurchase);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void PurchasingStatisticsReport_WhenEmpty() throws JSONException{
		JSONObject report = eventReport.generateReport(new ArrayList<LogEvent>());	
		expectedReport = new JSONObject("{}");
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}
	
	@Test
	public void PurchasingStatisticsReport() throws JSONException{
		JSONObject report = eventReport.generateReport(correctLogEvents);	
		setupExpectedReport();
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}	
}
