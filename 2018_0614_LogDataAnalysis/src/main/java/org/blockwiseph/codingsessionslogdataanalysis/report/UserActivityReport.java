package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.json.JSONObject;

public class UserActivityReport implements LogEventsReport{

	private static final String name = "userActivity";
	
	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		Set<String> uniqueEventEmailSet = new HashSet<String>();
		HashMap<String, Integer> uniqueEventMap = new HashMap<String, Integer>();
		uniqueEventMap.put("uniqueLOGIN", 0);
		uniqueEventMap.put("uniqueLOGOUT", 0);
		for (LogEvent logEvent : logEvents) {
			EventType eventType = logEvent.getTag();
			if(eventType == EventType.LOGIN || eventType == EventType.LOGOUT) {
				String uniqueEventTypeAndEmail = eventType + " " + logEvent.getEmail();
				if(uniqueEventEmailSet.add(uniqueEventTypeAndEmail)) {
					Integer count = uniqueEventMap.get("unique"+eventType);
					uniqueEventMap.put("unique"+eventType, count+1);
				}
			}
		}
		JSONObject jsonReport = new JSONObject(uniqueEventMap);
		return jsonReport;
	}

	@Override
	public String getName() {
		return name;
	}

}
