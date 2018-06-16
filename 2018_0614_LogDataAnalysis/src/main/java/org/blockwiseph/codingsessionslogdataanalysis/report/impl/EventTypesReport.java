package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONException;
import org.json.JSONObject;

public class EventTypesReport implements LogEventsReport {

	private static final String REPORT_NAME = "eventTypes";
	
	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		HashMap<String, Integer> eventCounter = new HashMap<>();
		for (LogEvent logEvent : logEvents) {
			String eventName = logEvent.getTag().name();
			Integer existingEventCount = Optional.ofNullable(eventCounter.get(eventName)).orElse(0);
			
			eventCounter.put(eventName, existingEventCount + 1);
		}
		return new JSONObject(eventCounter);
	}

	@Override
	public String getName() {
		return REPORT_NAME;
	}
}
