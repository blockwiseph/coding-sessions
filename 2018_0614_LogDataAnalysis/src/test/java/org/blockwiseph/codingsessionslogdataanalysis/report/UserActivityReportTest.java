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
	private static final int CORRECT_LOGIN_UNIQUE_COUNT = 3;
	private static final int CORRECT_LOGOUT_UNIQUE_COUNT = 2;

	@Before
	public void setup() throws JSONException {
		eventFactory = new EventFactory();
		userActivityReport = new UserActivityReport();
//		setupExpectedReport();
	}

	private List<LogEvent> correctLogEventsWithNoDuplicate() {
		List<LogEvent> correctLogEvents = new ArrayList<LogEvent>();
		for (int i = 0; i < CORRECT_LOGIN_UNIQUE_COUNT; i++) {
			correctLogEvents.add(new LoginEvent(i + "@gmail.com"));
		}
		for (int i = 0; i < CORRECT_LOGOUT_UNIQUE_COUNT; i++) {
			correctLogEvents.add(new LogoutEvent(i + "@gmail.com"));
		}
		return correctLogEvents;
	}

//	private void setupExpectedReport() throws JSONException {
//	}

	@Test
	public void GetUserActivityReport_WhenLogEventIsEmpty_ShouldReturnJSONWithZeroValues() throws JSONException, InvalidLogLineException {
		JSONObject expectedReport = new JSONObject();
		expectedReport.put("uniqueLOGIN", 0);
		expectedReport.put("uniqueLOGOUT", 0);
		JSONObject report = userActivityReport.generateReport(new ArrayList<LogEvent>());
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void GetUserActivityReport_WhenLogEventIsCorrectAndNoDuplicate_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		JSONObject expectedReport = new JSONObject("{\"uniqueLOGOUT\":2,\"uniqueLOGIN\":3}");
		System.out.println(expectedReport);
		expectedReport.put("uniqueLOGIN", CORRECT_LOGIN_UNIQUE_COUNT);
		expectedReport.put("uniqueLOGOUT", CORRECT_LOGOUT_UNIQUE_COUNT);
		JSONObject report = userActivityReport.generateReport(correctLogEventsWithNoDuplicate());
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}
	
	@Test
	public void GetUserActivityReport_WhenLogEventIsCorrectAndWithDuplicate_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		JSONObject expectedReport = new JSONObject();
		expectedReport.put("uniqueLOGIN", CORRECT_LOGIN_UNIQUE_COUNT);
		expectedReport.put("uniqueLOGOUT", CORRECT_LOGOUT_UNIQUE_COUNT);
		List<LogEvent> correctLogEvents = correctLogEventsWithNoDuplicate();
		correctLogEvents.add(new LoginEvent("0@gmail.com"));
		correctLogEvents.add(new LogoutEvent("0@gmail.com"));
		correctLogEvents.add(new LoginEvent("1@gmail.com"));
		JSONObject report = userActivityReport.generateReport(correctLogEvents);
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}
}
