package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventFactory;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.InvalidLogLineException;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogoutEvent;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class UserActivityReportTest {

	EventFactory eventFactory;
	private UserActivityReport userActivityReport;
	private List<LogEvent> correctLogEvents;
	private JSONObject expectedReport;
	private int loginCount = 2;
	private int logoutCount = 2;
	private int correctUniqueLoginCount = 2;
	private int correctUniqueLogoutCount = 2;

	@Before
	public void setup() throws JSONException {
		eventFactory = new EventFactory();
		userActivityReport = new UserActivityReport();
		setupCorrectLogEvents();
		setupExpectedReport();
	}

	private void setupCorrectLogEvents() {
		correctLogEvents = new ArrayList<LogEvent>();
		for (int i = 0; i < loginCount; i++) {
			correctLogEvents.add(new LoginEvent(i + "@gmail.com"));
		}
		for (int i = 0; i < logoutCount; i++) {
			correctLogEvents.add(new LogoutEvent(i + "@gmail.com"));
		}
		for (int i = 0; i < loginCount; i++) {
			correctLogEvents.add(new LoginEvent(i + "@gmail.com"));
		}
		for (int i = 0; i < logoutCount; i++) {
			correctLogEvents.add(new LogoutEvent(i + "@gmail.com"));
		}
	}

	private void setupExpectedReport() throws JSONException {
		expectedReport = new JSONObject();
		expectedReport.put("uniqueLOGIN", correctUniqueLoginCount);
		expectedReport.put("uniqueLOGOUT", correctUniqueLogoutCount);
	}

	@Test
	public void UserActivityReportIfEmpty() throws JSONException, InvalidLogLineException {
		correctUniqueLoginCount = correctUniqueLogoutCount = 0;
		setupExpectedReport();
		JSONObject report = userActivityReport.generateReport(new ArrayList<LogEvent>());
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void UserActivityReport() throws JSONException, InvalidLogLineException {
		JSONObject report = userActivityReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void UserActivityReportAllZero() throws JSONException, InvalidLogLineException {
		loginCount = logoutCount = correctUniqueLoginCount = correctUniqueLogoutCount = 0;
		setupExpectedReport();
		setupCorrectLogEvents();
		JSONObject report = userActivityReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}
}
