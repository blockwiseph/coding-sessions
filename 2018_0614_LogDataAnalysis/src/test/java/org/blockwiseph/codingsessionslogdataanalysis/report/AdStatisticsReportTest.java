package org.blockwiseph.codingsessionslogdataanalysis.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.ViewAdEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.AdStatisticsReport;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class AdStatisticsReportTest {
	private AdStatisticsReport adStatisticsReport = new AdStatisticsReport();

	@Test
	public void adStatisticsReport_WhenEmpty() throws JSONException {
		JSONObject report = adStatisticsReport.generateReport(Collections.emptyList());

		JSONObject expectedReport = new JSONObject("{}");
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void adStatisticsReport_whenNoViewAdEvents() throws JSONException {
		JSONObject report = adStatisticsReport.generateReport(noViewAdEvents());

		JSONObject expectedReport = new JSONObject("{}");
		JSONAssert.assertEquals(expectedReport, report, JSONCompareMode.STRICT);
	}

	@Test
	public void adStatisticsReport_whenOnlyAdEvents() throws JSONException {
		List<Integer> adIds = Arrays.asList(1, 2, 3, 4, 1, 2, 3, 4);
		List<String> emails = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h");

		JSONObject report = adStatisticsReport.generateReport(onlyAdEvents(adIds, emails));

		String expectedReport = "{ \"averageViewsPerAd\": 2,";
		expectedReport += "\"averageUniqueViewsPerAd\": 2,";
		expectedReport += "\"adIdToViews\": { \"1\": 2, \"2\": 2, \"3\": 2, \"4\": 2 },";
		expectedReport += "\"adIdToUniqueViews\": { \"1\": 2, \"2\": 2, \"3\": 2, \"4\": 2 }}";

		JSONAssert.assertEquals(new JSONObject(expectedReport), report, JSONCompareMode.STRICT);
	}

	@Test
	public void adStatisticsReport_wheBothAdAndOtherEvents() throws JSONException {
		List<Integer> adIds = Arrays.asList(1, 2, 1, 2, 3, 4, 4, 4, 3);
		List<String> emails = Arrays.asList("a", "b", "c", "b", "d", "a", "a", "b", "b");
		List<LogEvent> allTypesOfEvents = onlyAdEvents(adIds, emails);
		allTypesOfEvents.addAll(noViewAdEvents());
		Collections.shuffle(allTypesOfEvents);

		JSONObject report = adStatisticsReport.generateReport(onlyAdEvents(adIds, emails));

		String expectedReport = "{ \"averageViewsPerAd\": 2.25,";
		expectedReport += "\"averageUniqueViewsPerAd\": 1.75,";
		expectedReport += "\"adIdToViews\": { \"1\": 2, \"2\": 2, \"3\": 2, \"4\": 3 },";
		expectedReport += "\"adIdToUniqueViews\": { \"1\": 2, \"2\": 1, \"3\": 2, \"4\": 2 }}";

		JSONAssert.assertEquals(new JSONObject(expectedReport), report, JSONCompareMode.STRICT);
	}

	private List<LogEvent> noViewAdEvents() {
		List<LogEvent> logEvents = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			logEvents.add(new LoginEvent("dummy"));
		}
		for (int i = 0; i < 3; i++) {
			logEvents.add(new LogoutEvent("dummy"));
		}
		for (int i = 0; i < 2; i++) {
			logEvents.add(new PurchaseEvent("dummy", 12, 12.0));
		}
		Collections.shuffle(logEvents);
		return logEvents;
	}

	private List<LogEvent> onlyAdEvents(List<Integer> adIds, List<String> emails) {
		List<LogEvent> logEvents = new ArrayList<>();
		for (int i = 0; i < adIds.size(); i++) {
			Integer adId = adIds.get(i);
			String email = emails.get(i);
			logEvents.add(new ViewAdEvent(email, String.valueOf(adId)));
		}
		return logEvents;
	}
}