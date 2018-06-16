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

public class LogFileReaderImpl implements LogFileReader {

	private String filename;

	@Inject
	EventFactory eventFactory;

	public LogFileReaderImpl(String filename) {
		this.filename = filename;
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
