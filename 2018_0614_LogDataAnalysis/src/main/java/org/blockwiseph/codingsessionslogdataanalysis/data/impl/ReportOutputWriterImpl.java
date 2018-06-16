package org.blockwiseph.codingsessionslogdataanalysis.data.impl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.blockwiseph.codingsessionslogdataanalysis.data.ReportOutputWriter;
import org.json.JSONObject;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReportOutputWriterImpl implements ReportOutputWriter {

	private String filename;

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
