package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.CrashEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.InvalidLogLineException;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.PurchaseEvent;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class EventTypesReportTest {

	EventTypesReport eventReport;
	List<LogEvent> correctLogEvents;
	JSONObject expectedReport;
	private int loginCount = 2;
	private int logoutCount = 3;
	private int purchaseCount = 4;
	private int crashCount = 5;

	@Before
	public void setup() throws JSONException {
		eventReport = new EventTypesReport();
	}

	private void setupCorrectLogEvents() {
		correctLogEvents = new ArrayList<LogEvent>();
		for (int i = 0; i < loginCount; i++) {
			correctLogEvents.add(new LoginEvent(i + "gmail.com"));
		}
		for (int i = 0; i < logoutCount; i++) {
			correctLogEvents.add(new LogoutEvent(i + "gmail.com"));
		}
		for (int i = 0; i < purchaseCount; i++) {
			correctLogEvents.add(new PurchaseEvent(i + "gmail.com", i, Double.valueOf(i * 100)));
		}
		for (int i = 0; i < crashCount; i++) {
			correctLogEvents.add(new CrashEvent(i + "gmail.com"));
		}
		Collections.shuffle(correctLogEvents);
	}

	private void setupExpectedReport() throws JSONException {
		expectedReport = new JSONObject();
		if (crashCount > 0) {
			expectedReport.put("CRASH", crashCount);
		}
		if (loginCount > 0) {
			expectedReport.put("LOGIN", loginCount);
		}
		if (logoutCount > 0) {
			expectedReport.put("LOGOUT", logoutCount);
		}
		if (purchaseCount > 0) {
			expectedReport.put("PURCHASE", purchaseCount);
		}
	}

	@Test
	public void EventTypesReportIfEmpty() throws JSONException {
		JSONObject report = eventReport.generateReport(new ArrayList<LogEvent>());
		String expectedOutput = "{}";
		JSONAssert.assertEquals(new JSONObject(expectedOutput), report, JSONCompareMode.STRICT);
	}

	@Test
	public void EventTypesReport() throws JSONException{
		setupCorrectLogEvents();
		setupExpectedReport();
		JSONObject report = eventReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void EventTypesReportAllZero() throws JSONException{
		loginCount = logoutCount = purchaseCount = crashCount = 0;
		setupCorrectLogEvents();
		setupExpectedReport();
		JSONObject report = eventReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}
}
