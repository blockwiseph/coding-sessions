package org.blockwiseph.codingsessionslogdataanalysis.data;

import org.json.JSONObject;

public interface ReportOutputWriter {
	public void writeToFile(JSONObject jsonObject);
}
