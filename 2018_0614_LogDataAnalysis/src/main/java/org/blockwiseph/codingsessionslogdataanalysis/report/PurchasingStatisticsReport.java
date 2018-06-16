package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.PurchaseEvent;
import org.json.JSONObject;

public class PurchasingStatisticsReport implements LogEventsReport {

	private static final String name = "purchasingTypes";
	private static Double totalAmount = 0.0;
	private static Double totalItems = 0.0;

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		HashMap<String, Double> purchaseReport = new HashMap<>();
		if(logEvents.size() > 0) {
			List<PurchaseEvent> purchaseEvents = getAllPurchaseStatReportAndSetup(logEvents);
			int purchaseEventSize = purchaseEvents.size();
			DecimalFormat df = new DecimalFormat("#.##");
			purchaseReport.put("averagePurchaseAmount",Double.valueOf(df.format(totalAmount / purchaseEventSize)));
			purchaseReport.put("averagePricePerItem", Double.valueOf(df.format(totalAmount / totalItems)));
			purchaseReport.put("averageItemsPerPurchase",Double.valueOf(df.format(totalItems / purchaseEventSize)));
		}
		JSONObject report = new JSONObject(purchaseReport);
		return report;
	}

	private List<PurchaseEvent> getAllPurchaseStatReportAndSetup(List<LogEvent> logEvents) {
		List<PurchaseEvent> purchaseEvents = new ArrayList<>();
		for (LogEvent logEvent : logEvents) {
			if (logEvent.getTag() == EventType.PURCHASE) {
				PurchaseEvent purchaseEvent = (PurchaseEvent) logEvent;
				purchaseEvents.add(purchaseEvent);
				totalAmount += purchaseEvent.getAmount();
				totalItems += purchaseEvent.getQuantity();
			}
		}
		return purchaseEvents;
	}

	@Override
	public String getName() {
		return name;
	}
}
