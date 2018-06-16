package org.blockwiseph.codingsessionslogdataanalysis.data.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.blockwiseph.codingsessionslogdataanalysis.data.ReportOutputWriter;
import org.json.JSONObject;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class ReportOutputWriterImpl implements ReportOutputWriter {

	private final String filename;

	@Inject
	public ReportOutputWriterImpl(@Named("outputFile") String filename) {
		this.filename = filename;
	}

	@Override
	public void writeToFile(JSONObject jsonObject) {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
			bufferedWriter.write(jsonObject.toString());
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
