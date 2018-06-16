package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONObject;

public class AveragePerUniqueUserReport implements LogEventsReport {

	private static final String REPORT_NAME = "averagePerUniqueUser";
	private static final String LOGIN_KEY = "LOGIN";
	private static final String LOGOUT_KEY = "LOGOUT";
	private static final String PURCHASE_KEY = "PURCHASE";
	private static final String CRASH_KEY = "CRASH";

	private final DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		Set<String> uniqueLoginSet = new HashSet<String>();
		Set<String> uniqueLogoutSet = new HashSet<String>();
		Set<String> uniquePurchaseSet = new HashSet<String>();
		Set<String> uniqueCrashSet = new HashSet<String>();
		Map<String, Double> eventCount = new HashMap<>();

		logEvents.stream().filter(logEvent -> logEvent.getTag() == EventType.LOGIN).forEach(logEvent -> processLogEvent(logEvent, uniqueLoginSet, eventCount));
		logEvents.stream().filter(logEvent -> logEvent.getTag() == EventType.LOGOUT).forEach(logEvent -> processLogEvent(logEvent, uniqueLogoutSet, eventCount));
		logEvents.stream().filter(logEvent -> logEvent.getTag() == EventType.PURCHASE).forEach(logEvent -> processLogEvent(logEvent, uniquePurchaseSet, eventCount));
		logEvents.stream().filter(logEvent -> logEvent.getTag() == EventType.CRASH).forEach(logEvent -> processLogEvent(logEvent, uniqueCrashSet, eventCount));

		return generateReport(eventCount, uniqueLoginSet.size(), uniqueLogoutSet.size(), uniquePurchaseSet.size(), uniqueCrashSet.size());
	}

	private void processLogEvent(LogEvent LogEvent, Set<String> set, Map<String, Double> eventCount) {
		set.add(LogEvent.getEmail());
		eventCount.compute(LogEvent.getTag().toString(), (k, v) -> v == null ? 1 : v + 1);
	}

	private JSONObject generateReport(Map<String, Double> eventCount, int uniqueLoginCount, int uniqueLogoutCount, int uniquePurchaseCount, int uniqueCrashCount) {
		Map<String, Double> report = new HashMap<>();
		report.put(LOGIN_KEY, eventCount.isEmpty() ? 0 : formatDouble(eventCount.get(LOGIN_KEY) / uniqueLoginCount));
		report.put(LOGOUT_KEY, eventCount.isEmpty() ? 0 : formatDouble(eventCount.get(LOGOUT_KEY) / uniqueLogoutCount));
		report.put(PURCHASE_KEY, eventCount.isEmpty() ? 0 : formatDouble(eventCount.get(PURCHASE_KEY) / uniquePurchaseCount));
		report.put(CRASH_KEY, eventCount.isEmpty() ? 0 : formatDouble(eventCount.get(CRASH_KEY) / uniqueCrashCount));
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
