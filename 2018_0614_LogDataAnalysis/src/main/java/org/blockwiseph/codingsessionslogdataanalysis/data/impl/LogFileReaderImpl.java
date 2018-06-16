package org.blockwiseph.codingsessionslogdataanalysis.data.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.blockwiseph.codingsessionslogdataanalysis.data.LogFileReader;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.factory.EventFactory;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.factory.InvalidLogLineException;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogEvent;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class LogFileReaderImpl implements LogFileReader {

	private final String filename;
	private final EventFactory eventFactory;

	@Inject
	public LogFileReaderImpl(@Named("inputFile") String filename, EventFactory eventFactory) {
		this.filename = filename;
		this.eventFactory = eventFactory;
	}

	@Override
	public List<LogEvent> getEventsFromFile() {
		List<LogEvent> eventsFromFile = new ArrayList<LogEvent>();
		try {
			Scanner sc = new Scanner(new File(filename));
			readEventsFromScanner(sc, eventsFromFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return eventsFromFile;
	}

	private void readEventsFromScanner(Scanner sc, List<LogEvent> eventsFromFile) {
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			try {
				LogEvent logEvent = eventFactory.fromLogLine(line);
				eventsFromFile.add(logEvent);
			} catch (InvalidLogLineException e) {
			}
		}
	}

}
