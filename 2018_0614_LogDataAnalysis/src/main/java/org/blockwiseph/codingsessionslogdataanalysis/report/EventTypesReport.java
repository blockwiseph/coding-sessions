package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.json.JSONException;
import org.json.JSONObject;

public class EventTypesReport implements LogEventsReport {

	private static final String name = "eventTypes";

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		HashMap<String, Integer> eventCounter = new HashMap<>();
		for (LogEvent logEvent : logEvents) {
			EventType eventType = logEvent.getTag();
			Integer i = Optional.ofNullable(eventCounter.get(eventType.name())).orElse(0);
			eventCounter.put(eventType.name(), i + 1);
		}
		JSONObject report = new JSONObject(eventCounter);
		return report;
	}

	@Override
	public String getName() {
		return name;
	}
}
