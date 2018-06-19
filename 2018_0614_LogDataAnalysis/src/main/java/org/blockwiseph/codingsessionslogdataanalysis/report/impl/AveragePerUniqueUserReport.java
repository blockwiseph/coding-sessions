package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONObject;

import lombok.val;

public class AveragePerUniqueUserReport implements LogEventsReport {

	private static final String REPORT_NAME = "averagePerUniqueUser";

	private final DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		Map<EventType, List<String>> eventTypeToEmails = new HashMap<>();
		for (LogEvent logEvent : logEvents) {
			List<String> existingEmailsForThisEvent = eventTypeToEmails.get(logEvent.getTag());
			List<String> emailList = Optional.ofNullable(existingEmailsForThisEvent).orElseGet(ArrayList::new);
			emailList.add(logEvent.getEmail());

			eventTypeToEmails.put(logEvent.getTag(), emailList);
		}
		return generateReport(eventTypeToEmails);
	}

	private JSONObject generateReport(Map<EventType, List<String>> eventTypeToEmails) {
		Map<String, Double> report = new HashMap<>();
		for (val mapEntry : eventTypeToEmails.entrySet()) {
			String eventName = mapEntry.getKey().name();
			List<String> emails = mapEntry.getValue();

			int totalEmails = emails.size();
			int uniqueEmails = new HashSet<>(emails).size();
			Double average = (double) totalEmails / uniqueEmails;

			report.put(eventName, formatDouble(average));
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
