package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONObject;

public class UserActivityReport implements LogEventsReport {

	private static final String REPORT_NAME = "userActivity";
	private static final String UNIQUE_LOGIN_KEY = "uniqueLogin";
	private static final String UNIQUE_LOGOUT_KEY = "uniqueLogout";

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		Set<String> uniqueLoginSet = logEvents.stream().filter(logEvent -> logEvent.getTag() == EventType.LOGIN).map(LogEvent::getEmail)
				.collect(Collectors.toSet());
		
		Set<String> uniqueLogoutSet = logEvents.stream().filter(logEvent -> logEvent.getTag() == EventType.LOGOUT).map(LogEvent::getEmail)
				.collect(Collectors.toSet());
		
		return generateReport(uniqueLoginSet.size(), uniqueLogoutSet.size());
	}

	private static JSONObject generateReport(int uniqueLoginCount, int uniqueLogoutCount) {
		Map<String, Integer> report = new HashMap<>();
		report.put(UNIQUE_LOGIN_KEY, uniqueLoginCount);
		report.put(UNIQUE_LOGOUT_KEY, uniqueLogoutCount);
		return new JSONObject(report);
	}

	@Override
	public String getName() {
		return REPORT_NAME;
	}
}
