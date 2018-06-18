package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONObject;

public class AveragePerUniqueUserReport implements LogEventsReport {

	private static final String REPORT_NAME = "averagePerUniqueUser";

	private final DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		Map<EventType, List<String>> eventTypeToTotalCount = new HashMap<>();
		for (LogEvent logEvent : logEvents) {
			List<String> emailList = eventTypeToTotalCount.containsKey(logEvent.getTag()) ? eventTypeToTotalCount.get(logEvent.getTag()) : new ArrayList<String>();
			emailList.add(logEvent.getEmail());
			eventTypeToTotalCount.put(logEvent.getTag(), emailList);
		}
		return generateReport(eventTypeToTotalCount);
	}

	private JSONObject generateReport(Map<EventType, List<String>> eventTypeToTotalCount) {
		Map<String, Double> report = new HashMap<>();
		for (Map.Entry<EventType, List<String>> mapEntry : eventTypeToTotalCount.entrySet()) {
			Double average = mapEntry.getValue().size() == 0 ? 0 : (double) mapEntry.getValue().size() / new HashSet<>(mapEntry.getValue()).size();
			report.put(mapEntry.getKey().name(), formatDouble(average));
		}
		return new JSONObject(report);
	}

	private Double formatDouble(double number) {
		return Double.valueOf(twoDecimalPlaces.format(number));
	}

	@Override
	public String getName() {
		return REPORT_NAME;
	}
}
