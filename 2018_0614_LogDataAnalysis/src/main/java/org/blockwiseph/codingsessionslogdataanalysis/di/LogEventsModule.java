package org.blockwiseph.codingsessionslogdataanalysis.di;

import java.util.ArrayList;
import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.data.LogFileReader;
import org.blockwiseph.codingsessionslogdataanalysis.data.ReportOutputWriter;
import org.blockwiseph.codingsessionslogdataanalysis.data.impl.LogFileReaderImpl;
import org.blockwiseph.codingsessionslogdataanalysis.data.impl.ReportOutputWriterImpl;
import org.blockwiseph.codingsessionslogdataanalysis.report.LogEventsReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.AdStatisticsReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.AveragePerUniqueUserReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.EventTypesReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.PurchasingStatisticsReport;
import org.blockwiseph.codingsessionslogdataanalysis.report.impl.UserActivityReport;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogEventsModule extends AbstractModule {
	String inputFile;
	String outputFile;

	@Override
	protected void configure() {
		super.configure();
		bind(String.class).annotatedWith(Names.named("inputFile")).toInstance(inputFile);
		bind(String.class).annotatedWith(Names.named("outputFile")).toInstance(outputFile);

		bind(LogFileReader.class).to(LogFileReaderImpl.class);
		bind(ReportOutputWriter.class).to(ReportOutputWriterImpl.class);
	}

	@Provides
	public List<LogEventsReport> getReports() {
		List<LogEventsReport> logEventReports = new ArrayList<>();
		logEventReports.add(new EventTypesReport());
		logEventReports.add(new UserActivityReport());
		logEventReports.add(new AveragePerUniqueUserReport());
		logEventReports.add(new PurchasingStatisticsReport());
		logEventReports.add(new AdStatisticsReport());
		return logEventReports;
	}
}
