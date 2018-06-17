package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.factory.InvalidLogLineException;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.CrashEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.AveragePerUniqueUserReport;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class AveragePerUniqueUserReportTest {

	private static final String LOGOUT_KEY = "LOGOUT";
	private static final String LOGIN_KEY = "LOGIN";
	private static final String PURCHASE_KEY = "PURCHASE";
	private static final String CRASH_KEY = "CRASH";
	private AveragePerUniqueUserReport averagePerUniqueUserReport;

	@Before
	public void setup() throws JSONException {
		averagePerUniqueUserReport = new AveragePerUniqueUserReport();
	}

	@Test
	public void GetAveragePerUniqueUserReport_WhenLogEventIsEmpty_ShouldReturnJSONWithZeroValues() throws JSONException, InvalidLogLineException {
		testAndAssert(Collections.emptyList(), 0, 0, 0, 0);
	}

	@Test
	public void GetAveragePerUniqueUserReport_WhenLogEventIsCorrectAndNoDuplicate_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		List<LogEvent> inputEvents = new ArrayList<LogEvent>();
		inputEvents.addAll(loginEvents(1, 2, 3));
		inputEvents.addAll(logoutEvents(4, 5, 6));
		inputEvents.addAll(purchaseEvents(7, 8, 9));
		inputEvents.addAll(crashEvents(10, 11, 12));
		testAndAssert(inputEvents, 1, 1, 1, 1);
	}

	@Test
	public void GetAveragePerUniqueUserReport_WhenLogEventIsCorrectAndWithDuplicate_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		List<LogEvent> inputEvents = new ArrayList<LogEvent>();
		inputEvents.addAll(loginEvents(2, 3, 3, 3, 2, 2));
		inputEvents.addAll(logoutEvents(3, 2, 3, 2, 3));
		inputEvents.addAll(purchaseEvents(3, 2, 2, 3, 3));
		inputEvents.addAll(crashEvents(2, 3, 3, 3));
		testAndAssert(inputEvents, 3, 2.5, 2.5, 2);
	}

	@Test
	public void GetAveragePerUniqueUserReport_WhenNotAllLogEventIsPresent_ShouldReturnCorrectJSON() throws JSONException, InvalidLogLineException {
		List<LogEvent> inputEvents = new ArrayList<LogEvent>();
		inputEvents.addAll(loginEvents(2, 3, 3, 3, 2, 2));
		inputEvents.addAll(purchaseEvents(3, 2, 2, 3, 3));
		testAndAssert(inputEvents, 3, 0, 2.5, 0);
	}

	private void testAndAssert(List<LogEvent> inputLoginEvents, double expectedLogin, double expectedLogout, double expectedPurchase, double expectedCrash) throws JSONException {
		JSONObject actualReport = averagePerUniqueUserReport.generateReport(inputLoginEvents);
		JSONAssert.assertEquals(expectedReport(expectedLogin, expectedLogout, expectedPurchase, expectedCrash), actualReport, JSONCompareMode.STRICT);
	}

	private List<LogEvent> loginEvents(int... ids) {
		return IntStream.of(ids).mapToObj(id -> new LoginEvent(id + "@gmail.com")).collect(Collectors.toList());
	}

	private List<LogEvent> logoutEvents(int... ids) {
		return IntStream.of(ids).mapToObj(id -> new LogoutEvent(id + "@gmail.com")).collect(Collectors.toList());
	}

	private List<LogEvent> purchaseEvents(int... ids) {
		return IntStream.of(ids).mapToObj(id -> new PurchaseEvent(id + "@gmail.com", 0, 0.0)).collect(Collectors.toList());
	}

	private List<LogEvent> crashEvents(int... ids) {
		return IntStream.of(ids).mapToObj(id -> new CrashEvent(id + "@gmail.com")).collect(Collectors.toList());
	}

	private JSONObject expectedReport(double expectedLogin, double expectedLogout, double expectedPurchase, double expectedCrash) throws JSONException {
		JSONObject expectedReport = new JSONObject();
		expectedReport.put(LOGIN_KEY, expectedLogin);
		expectedReport.put(LOGOUT_KEY, expectedLogout);
		expectedReport.put(PURCHASE_KEY, expectedPurchase);
		expectedReport.put(CRASH_KEY, expectedCrash);
		return expectedReport;
	}

}
