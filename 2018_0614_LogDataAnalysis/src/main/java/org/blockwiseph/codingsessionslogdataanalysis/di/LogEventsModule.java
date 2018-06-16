package org.blockwiseph.codingsessionslogdataanalysis.di;

import java.util.ArrayList;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.data.LogFileReader;
import org.blockwiseph.codingsessionslogdataanalysis.data.ReportOutputWriter;
import org.blockwiseph.codingsessionslogdataanalysis.data.impl.LogFileReaderImpl;
import org.blockwiseph.codingsessionslogdataanalysis.data.impl.ReportOutputWriterImpl;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.EventTypesReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.PurchasingStatisticsReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.UserActivityReport;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogEventsModule extends AbstractModule {
	String inputFile;
	String outputFile;

	@Provides
	@Singleton
	public LogFileReader getFileReader() {
		return new LogFileReaderImpl(inputFile);
	}

	@Provides
	@Singleton
	public ReportOutputWriter getOutputWriter() {
		return new ReportOutputWriterImpl(outputFile);
	}

	@Provides
	@Singleton
	public List<LogEventsReport> getReports() {
		List<LogEventsReport> logEventReports = new ArrayList<>();
		logEventReports.add(new EventTypesReport());
		logEventReports.add(new UserActivityReport());
		logEventReports.add(new PurchasingStatisticsReport());
		return logEventReports;
	}
}
