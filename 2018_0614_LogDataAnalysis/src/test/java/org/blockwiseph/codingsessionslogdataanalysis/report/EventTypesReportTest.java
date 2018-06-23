package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.CrashEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.EventTypesReport;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class EventTypesReportTest {

	EventTypesReport eventTypesReport;
	List<LogEvent> correctLogEvents;
	JSONObject expectedReport;
	private int loginCount = 2;
	private int logoutCount = 3;
	private int purchaseCount = 4;
	private int crashCount = 5;

	@Before
	public void setup() throws JSONException {
		eventTypesReport = new EventTypesReport();
	}

	private void setupCorrectLogEvents() {
		correctLogEvents = new ArrayList<LogEvent>();
		for (int userId = 0; userId < loginCount; userId++) {
			correctLogEvents.add(new LoginEvent(userId + "gmail.com"));
		}
		for (int userId = 0; userId < logoutCount; userId++) {
			correctLogEvents.add(new LogoutEvent(userId + "gmail.com"));
		}
		for (int userId = 0; userId < purchaseCount; userId++) {
			correctLogEvents.add(new PurchaseEvent(userId + "gmail.com", userId, Double.valueOf(userId * 100)));
		}
		for (int userId = 0; userId < crashCount; userId++) {
			correctLogEvents.add(new CrashEvent(userId + "gmail.com"));
		}
		Collections.shuffle(correctLogEvents);
	}

	private void setupExpectedReport() throws JSONException {
		expectedReport = new JSONObject();
		putIfGreaterThanZero(loginCount, "LOGIN");
		putIfGreaterThanZero(crashCount, "CRASH");
		putIfGreaterThanZero(logoutCount, "LOGOUT");
		putIfGreaterThanZero(purchaseCount, "PURCHASE");
	}

	private void putIfGreaterThanZero(int count, String eventName) throws JSONException {
		if (count > 0) {
			expectedReport.put(eventName, count);
		}
	}

	@Test
	public void eventTypesReport_whenReportsEmpty() throws JSONException {
		JSONObject report = eventTypesReport.generateReport(new ArrayList<LogEvent>());
		String expectedOutput = "{}";
		JSONAssert.assertEquals(new JSONObject(expectedOutput), report, JSONCompareMode.STRICT);
	}

	@Test
	public void eventTypesReport_whenAllEventsPresent() throws JSONException {
		setupCorrectLogEvents();
		setupExpectedReport();
		JSONObject report = eventTypesReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void eventTypesReport_whenAllEventsZero() throws JSONException {
		loginCount = logoutCount = purchaseCount = crashCount = 0;
		setupCorrectLogEvents();
		setupExpectedReport();
		JSONObject report = eventTypesReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}
}
