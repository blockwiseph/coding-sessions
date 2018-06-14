package org.blockwiseph.codingsessionslogdataanalysis.di;

import org.blockwiseph.codingsessionslogdataanalysis.data.LogFileReader;
import org.blockwiseph.codingsessionslogdataanalysis.data.impl.LogFileReaderImpl;

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
}
