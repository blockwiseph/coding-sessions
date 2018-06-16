package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.json.JSONException;
import org.json.JSONObject;

public class UserActivityReport implements LogEventsReport {

	private static final String name = "userActivity";
	private static final String uniqueLoginKey = "uniqueLOGIN";
	private static final String uniqueLogoutKey = "uniqueLOGOUT";

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		JSONObject jsonReport = new JSONObject();
		try {
			Set<String> uniqueLoginSet = new HashSet<String>();
			Set<String> uniqueLogoutSet = new HashSet<String>();
			for (LogEvent logEvent : logEvents) {
				EventType eventType = logEvent.getTag();
				if (eventType == EventType.LOGIN) {
					uniqueLoginSet.add(logEvent.getEmail());
				} else if (eventType == EventType.LOGOUT) {
					uniqueLogoutSet.add(logEvent.getEmail());
				}
			}
			jsonReport = new JSONObject(generateJSONString(uniqueLoginSet.size(), uniqueLogoutSet.size()));
		} catch (JSONException e) {
		}
		return jsonReport;
	}

	private String generateJSONString(int uniqueLoginCount, int uniqueLogoutCount) {
		return "{\"" + uniqueLoginKey + "\":" + uniqueLoginCount + ",\"" + uniqueLogoutKey + "\":" + uniqueLogoutCount + "}";
	}

	@Override
	public String getName() {
		return name;
	}

}
