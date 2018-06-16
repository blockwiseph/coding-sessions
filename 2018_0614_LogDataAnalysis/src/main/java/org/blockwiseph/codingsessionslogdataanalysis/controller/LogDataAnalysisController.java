package org.blockwiseph.codingsessionslogdataanalysis.controller;

import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.data.LogFileReader;
import org.blockwiseph.codingsessionslogdataanalysis.data.ReportOutputWriter;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.inject.Inject;

import lombok.AllArgsConstructor;

@AllArgsConstructor(onConstructor = @__(@Inject))
public class LogDataAnalysisController {
	private LogFileReader logFileReader;
	private List<LogEventsReport> reports;
	private ReportOutputWriter reportOutputWriter;
	
	public void runAnalysis() throws JSONException {
		List<LogEvent> eventsFromFile = logFileReader.getEventsFromFile();
		JSONObject wholeReport = new JSONObject();
		for(LogEventsReport report : reports) {
			JSONObject reportObject = report.generateReport(eventsFromFile);
			wholeReport.put(report.getName(), reportObject);
		}
		reportOutputWriter.writeToFile(wholeReport);
	}
}
