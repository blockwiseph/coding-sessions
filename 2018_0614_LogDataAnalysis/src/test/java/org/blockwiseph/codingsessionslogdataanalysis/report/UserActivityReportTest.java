package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.factory.InvalidLogLineException;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.UserActivityReport;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class UserActivityReportTest {

	private static final String UNIQUE_LOGOUT_KEY = "uniqueLOGOUT";
	private static final String UNIQUE_LOGIN_KEY = "uniqueLOGIN";
	private UserActivityReport userActivityReport;

	@Before
	public void setup() throws JSONException {
		userActivityReport = new UserActivityReport();
	}

	@Test
	public void GetUserActivityReport_WhenLogEventIsEmpty_ShouldReturnJSONWithZeroValues() throws JSONException, InvalidLogLineException {
		testAndAssert(Collections.emptyList(), 0, 0);
	}

	@Test
	public void GetUserActivityReport_WhenLogEventIsCorrectAndNoDuplicate_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		List<LogEvent> inputLoginEvents = new ArrayList<LogEvent>();
		inputLoginEvents.addAll(loginEvents(1, 2, 3));
		inputLoginEvents.addAll(logoutEvents(4, 5, 6));

		testAndAssert(inputLoginEvents, 3, 3);

	}

	@Test
	public void GetUserActivityReport_WhenLogEventIsCorrectAndWithDuplicate_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		List<LogEvent> inputLoginEvents = new ArrayList<LogEvent>();
		inputLoginEvents.addAll(loginEvents(1, 2, 2, 3));
		inputLoginEvents.addAll(logoutEvents(2, 4, 4, 5, 6));

		testAndAssert(inputLoginEvents, 3, 4);
	}

	private void testAndAssert(List<LogEvent> inputLoginEvents, int expectedLogin, int expectedLogout) throws JSONException {
		JSONObject actualReport = userActivityReport.generateReport(inputLoginEvents);
		JSONAssert.assertEquals(expectedReport(expectedLogin, expectedLogout), actualReport, JSONCompareMode.STRICT);
	}

	private List<LogEvent> loginEvents(int... ids) {
		return IntStream.of(ids).mapToObj(id -> new LoginEvent(id + "@gmail.com")).collect(Collectors.toList());
	}

	private List<LogEvent> logoutEvents(int... ids) {
		return IntStream.of(ids).mapToObj(id -> new LogoutEvent(id + "@gmail.com")).collect(Collectors.toList());
	}

	private JSONObject expectedReport(int expectedLogin, int expectedLogout) throws JSONException {
		JSONObject expectedReport = new JSONObject();
		expectedReport.put(UNIQUE_LOGIN_KEY, expectedLogin);
		expectedReport.put(UNIQUE_LOGOUT_KEY, expectedLogout);
		return expectedReport;
	}

}
