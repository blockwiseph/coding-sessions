package org.blockwiseph.codingsessionslogdataanalysis.di;

import org.blockwiseph.codingsessionslogdataanalysis.controller.LogDataAnalysisController;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceLogEventModuleTest {
	@Test
	public void guice_shouldCreateController_withoutException() {
		Injector injector = Guice.createInjector(new LogEventsModule("log.txt", "report.txt"));
		injector.getInstance(LogDataAnalysisController.class);
	}
}
