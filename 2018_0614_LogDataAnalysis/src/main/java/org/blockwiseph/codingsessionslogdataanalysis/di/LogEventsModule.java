package org.blockwiseph.codingsessionslogdataanalysis.di;

import com.google.inject.AbstractModule;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LogEventsModule extends AbstractModule {
	String inputFile;
	String outputFile;
}
