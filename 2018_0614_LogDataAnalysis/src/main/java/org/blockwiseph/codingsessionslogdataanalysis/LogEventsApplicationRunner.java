package org.blockwiseph.codingsessionslogdataanalysis;

import org.blockwiseph.codingsessionslogdataanalysis.controller.LogDataAnalysisController;
import org.blockwiseph.codingsessionslogdataanalysis.di.LogEventsModule;
import org.json.JSONException;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class LogEventsApplicationRunner {
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new LogEventsModule("log.txt", "report.txt"));
		LogDataAnalysisController controller = injector.getInstance(LogDataAnalysisController.class);
		try {
			controller.runAnalysis();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
