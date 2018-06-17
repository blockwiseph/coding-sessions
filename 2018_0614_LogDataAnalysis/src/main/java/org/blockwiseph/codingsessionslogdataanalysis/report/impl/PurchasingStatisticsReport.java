package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONObject;

public class PurchasingStatisticsReport implements LogEventsReport {

	private static final String REPORT_NAME = "purchasingTypes";

	private final DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		List<PurchaseEvent> purchaseEvents = logEvents.stream().filter(e -> e.getTag() == EventType.PURCHASE).map(e -> (PurchaseEvent) e)
				.collect(Collectors.toList());

		if (purchaseEvents.isEmpty()) {
			return new JSONObject();
		}

		return getReport(purchaseEvents);
	}

	private JSONObject getReport(List<PurchaseEvent> purchaseEvents) {
		Double totalAmount = purchaseEvents.stream().map(PurchaseEvent::getAmount).reduce((a, b) -> a + b).get();
		Integer totalItems = purchaseEvents.stream().map(PurchaseEvent::getQuantity).reduce((a, b) -> a + b).get();
		int purchaseEventSize = purchaseEvents.size();

		HashMap<String, Double> purchaseReport = new HashMap<>();
		purchaseReport.put("averagePurchaseAmount", formatDouble(totalAmount / purchaseEventSize));
		purchaseReport.put("averagePricePerItem", formatDouble(totalAmount / totalItems));
		purchaseReport.put("averageItemsPerPurchase", formatDouble(totalItems / purchaseEventSize));
		return new JSONObject(purchaseReport);
	}

	private Double formatDouble(double number) {
		return Double.valueOf(twoDecimalPlaces.format(number));
	}

	@Override
	public String getName() {
		return REPORT_NAME;
	}
}
