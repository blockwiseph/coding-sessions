package org.blockwiseph.codingsessionslogdataanalysis.report.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.ViewAdEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.val;

public class AdStatisticsReport implements LogEventsReport {

	public static final String REPORT_NAME = "adStatistics";

	private final DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");

	@Override
	public JSONObject generateReport(List<LogEvent> logEvents) {
		List<ViewAdEvent> viewAdEvents = logEvents.stream().filter(e -> e.getTag() == EventType.VIEW_AD).map(e -> (ViewAdEvent) e)
				.collect(Collectors.toList());

		if (viewAdEvents.isEmpty()) {
			return new JSONObject();
		}

		try {
			return getAdStatisticsReport(getAdIdToEmails(viewAdEvents));
		} catch (JSONException e) {
			return new JSONObject();
		}
	}

	private static Map<String, List<String>> getAdIdToEmails(List<ViewAdEvent> viewAdEvents) {
		Map<String, List<String>> adIdToEmails = new HashMap<>();
		for (ViewAdEvent viewAdEvent : viewAdEvents) {
			String adId = viewAdEvent.getAdId();
			String email = viewAdEvent.getEmail();

			List<String> emailsForThisAdId = Optional.ofNullable(adIdToEmails.get(adId)).orElseGet(ArrayList::new);
			emailsForThisAdId.add(email);

			adIdToEmails.put(adId, emailsForThisAdId);
		}
		return adIdToEmails;
	}

	private JSONObject getAdStatisticsReport(Map<String, List<String>> adIdToEmails) throws JSONException {
		Map<String, Integer> adIdsToViews = new HashMap<>();
		Map<String, Integer> adIdsToUniqueViews = new HashMap<>();
		double totalViews = 0;
		double totalUniqueViews = 0;

		for (val adIdToEmail : adIdToEmails.entrySet()) {
			String adId = adIdToEmail.getKey();
			List<String> emails = adIdToEmail.getValue();

			int viewsForThisAd = emails.size();
			int uniqueViewsForThisAd =(int) emails.stream().distinct().count();

			adIdsToViews.put(adId, viewsForThisAd);
			adIdsToUniqueViews.put(adId, uniqueViewsForThisAd);
			totalViews += viewsForThisAd;
			totalUniqueViews += uniqueViewsForThisAd;
		}

		double numAds = adIdToEmails.size();
		double averageViewsPerAd = totalViews / numAds;
		double averageUniqueViewsPerAd = totalUniqueViews / numAds;

		return generateAdStatisticsReport(averageViewsPerAd, averageUniqueViewsPerAd, adIdsToViews, adIdsToUniqueViews);
	}

	private JSONObject generateAdStatisticsReport(double averageViewsPerAd, double averageUniqueViewsPerAd, Map<String, Integer> adIdsToViews, Map<String, Integer> adIdsToUniqueViews) throws JSONException {
		JSONObject adStatisticsReport = new JSONObject();
		adStatisticsReport.put("averageViewsPerAd", formatDouble(averageViewsPerAd));
		adStatisticsReport.put("averageUniqueViewsPerAd", formatDouble(averageUniqueViewsPerAd));
		adStatisticsReport.put("adIdToViews", new JSONObject(adIdsToViews));
		adStatisticsReport.put("adIdToUniqueViews", new JSONObject(adIdsToUniqueViews));
		return adStatisticsReport;
	}

	private Double formatDouble(double number) {
		return Double.valueOf(twoDecimalPlaces.format(number));
	}

	@Override
	public String getName() {
		return REPORT_NAME;
	}
}
