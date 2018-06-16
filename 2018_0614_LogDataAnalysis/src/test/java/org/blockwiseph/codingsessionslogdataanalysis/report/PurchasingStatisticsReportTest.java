package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.PurchasingStatisticsReport;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class PurchasingStatisticsReportTest {
	private PurchasingStatisticsReport eventReport = new PurchasingStatisticsReport();

	@Test
	public void PurchasingStatisticsReport_WhenEmpty() throws JSONException {
		JSONObject report = eventReport.generateReport(Collections.emptyList());

		JSONObject expectedReport = new JSONObject("{}");
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void purchasingStatisticsReport_whenNoPurchasingEvents() throws JSONException {
		JSONObject report = eventReport.generateReport(noPurchasingEvents());

		JSONObject expectedReport = new JSONObject("{}");
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void purchasingStatisticsReport_whenOnlyPurchasingEvents() throws JSONException {
		List<Integer> quantities = Arrays.asList(1, 2, 3);
		List<Double> amounts = Arrays.asList(10.5, 20.2, 30.5);
		JSONObject report = eventReport.generateReport(onlyPurchasingEvents(quantities, amounts));

		JSONObject expectedReport = getExpectedReport(20.4, 10.2, 2.0);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void purchasingStatisticsReport_wheBothPurchasingAndOtherEvents() throws JSONException {
		List<Integer> quantities = Arrays.asList(2, 4, 3);
		List<Double> amounts = Arrays.asList(20.5, 20.2, 40.5);
		List<LogEvent> allTypesOfEvents = onlyPurchasingEvents(quantities, amounts);
		allTypesOfEvents.addAll(noPurchasingEvents());
		Collections.shuffle(allTypesOfEvents);

		JSONObject report = eventReport.generateReport(allTypesOfEvents);

		JSONObject expectedReport = getExpectedReport(27.07, 9.02, 3.0);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	private List<LogEvent> noPurchasingEvents() {
		List<LogEvent> logEvents = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			logEvents.add(new LoginEvent("dummy"));
		}
		for (int i = 0; i < 3; i++) {
			logEvents.add(new LogoutEvent("dummy"));
		}
		return logEvents;
	}

	private JSONObject getExpectedReport(Double averagePurchaseAmount, Double averagePricePerItem, Double averageItemsPerPurchase) throws JSONException {
		JSONObject expectedReport = new JSONObject();
		expectedReport.put("averagePurchaseAmount", averagePurchaseAmount);
		expectedReport.put("averagePricePerItem", averagePricePerItem);
		expectedReport.put("averageItemsPerPurchase", averageItemsPerPurchase);
		return expectedReport;
	}

	private List<LogEvent> onlyPurchasingEvents(List<Integer> quantityList, List<Double> amountList) {
		List<LogEvent> logEvents = new ArrayList<>();
		for (int i = 0; i < quantityList.size(); i++) {
			Integer quantity = quantityList.get(i);
			Double amount = amountList.get(i);
			logEvents.add(new PurchaseEvent("dummy", quantity, amount));
		}
		return logEvents;
	}
}
