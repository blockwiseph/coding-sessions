package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;
import org.json.JSONObject;

public interface LogEventsReport {
	public JSONObject generateReport(List<LogEvent> logEvents);

	public String getName();
}
