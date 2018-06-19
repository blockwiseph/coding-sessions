package org.blockwiseph.codingsessionslogdataanalysis.data.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

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
			OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8);
			writer.write(jsonObject.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
