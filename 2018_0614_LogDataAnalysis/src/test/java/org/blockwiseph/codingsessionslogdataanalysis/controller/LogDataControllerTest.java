package org.blockwiseph.codingsessionslogdataanalysis.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.blockwiseph.codingsessionslogdataanalysis.data.LogFileReader;
import org.blockwiseph.codingsessionslogdataanalysis.data.ReportOutputWriter;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.CrashEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

@RunWith(MockitoJUnitRunner.class)
public class LogDataControllerTest {

	private static int NUM_REPORTS = 10;

	@Mock
	LogFileReader mockFileReader;

	@Mock
	ReportOutputWriter mockReportWriter;

	LogDataAnalysisController logDataAnalysisController;

	@Before
	public void setup() {
		doReturn(mockInputEvents()).when(mockFileReader).getEventsFromFile();
	}

	@Test
	public void runAnalysis_ShouldWriteCombinedJsonObjectToFile() throws JSONException {
		logDataAnalysisController = new LogDataAnalysisController(mockFileReader, mockReportGenerators(), mockReportWriter);
		testAndAssert(expectedOutputFromMockReportGenerators());
	}

	@Test
	public void runAnalysis_whenNoReports_shouldWriteBlankJsonObjectsToFile() throws JSONException {
		logDataAnalysisController = new LogDataAnalysisController(mockFileReader, Collections.emptyList(), mockReportWriter);
		JSONObject expectedOutput = new JSONObject();
		testAndAssert(expectedOutput);
	}

	private void testAndAssert(JSONObject expectedOutput) throws JSONException {
		doAnswer(new Answer<Void>() {
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				JSONObject actualArg = invocation.getArgumentAt(0, JSONObject.class);
				JSONAssert.assertEquals(expectedOutput, actualArg, JSONCompareMode.STRICT);
				return null;
			}
		}).when(mockReportWriter).writeToFile(any(JSONObject.class));
		logDataAnalysisController.runAnalysis();
		verify(mockReportWriter, times(1)).writeToFile(any(JSONObject.class));
		verifyNoMoreInteractions(mockReportWriter);
	}

	private List<LogEvent> mockInputEvents() {
		String email = "test" + this.hashCode();
		return Arrays.asList(new LoginEvent(email), new LogoutEvent(email), new CrashEvent(email), new PurchaseEvent(email, 4, 500.0));
	}

	private List<LogEventsReport> mockReportGenerators() {
		return IntStream.rangeClosed(1, NUM_REPORTS).mapToObj(t -> this.newReport(t)).collect(Collectors.toList());
	}

	private JSONObject expectedOutputFromMockReportGenerators() throws JSONException {
		JSONObject expected = new JSONObject();
		for (int i = 1; i <= NUM_REPORTS; i++) {
			expected.put(reportName(i), dummyReport(i));
		}
		return expected;
	}

	private LogEventsReport newReport(int id) {
		return new LogEventsReport() {

			public String getName() {
				return reportName(id);
			}

			public JSONObject generateReport(List<LogEvent> logEvents) {
				return dummyReport(id);
			}
		};
	}

	private String reportName(int id) {
		return id + "report" + this.hashCode();
	}

	private JSONObject dummyReport(int id) {
		JSONObject dummyReturn = new JSONObject();
		try {
			dummyReturn.put("id", String.valueOf(this.hashCode() + id));
			dummyReturn.put("test", this.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dummyReturn;
	}
}