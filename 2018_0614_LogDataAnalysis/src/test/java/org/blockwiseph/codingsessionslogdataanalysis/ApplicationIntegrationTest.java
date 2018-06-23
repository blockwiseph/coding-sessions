package org.blockwiseph.codingsessionslogdataanalysis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.blockwiseph.codingsessionslogdataanalysis.controller.LogDataAnalysisController;
import org.blockwiseph.codingsessionslogdataanalysis.di.LogEventsModule;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.google.inject.Guice;

import lombok.val;

public class ApplicationIntegrationTest {

	private static final String INTEGRATION_TEST_CASES_DIRECTORY = "integration_test_files/";

	@Test
	public void runAnalysis_whenInputFileNotExists_shouldGenerateEmptyReport() throws JSONException, IOException {
		runIntegrationTest("case1");
	}

	@Test
	public void runAnalysis_sampleData1() throws JSONException, IOException {
		runIntegrationTest("case2");
	}

	@Test
	public void runAnalysis_sampleData2() throws JSONException, IOException {
		runIntegrationTest("case3");
	}

	private void runIntegrationTest(String testCase) throws JSONException, IOException {
		String testCaseDirectory = INTEGRATION_TEST_CASES_DIRECTORY + testCase;
		String inputFile = testCaseDirectory + "/input.txt";
		String outputFile = testCaseDirectory + "/output.txt";
		String expectedOutputFile = testCaseDirectory + "/expectedOutput.txt";

		try {
			testAndAssert(inputFile, outputFile, expectedOutputFile);
		} finally {
			new File(outputFile).delete();
		}
	}

	private void testAndAssert(String inputFile, String outputFile, String expectedOutputFile) throws JSONException, IOException {
		val logEventsModule = new LogEventsModule(inputFile, outputFile);
		val controller = Guice.createInjector(logEventsModule).getInstance(LogDataAnalysisController.class);

		controller.runAnalysis();

		JSONAssert.assertEquals(toJson(expectedOutputFile), toJson(outputFile), JSONCompareMode.STRICT);
	}

	private JSONObject toJson(String filename) throws JSONException, IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filename));
		String fileContents = new String(encoded);
		return new JSONObject(fileContents);
	}
}
