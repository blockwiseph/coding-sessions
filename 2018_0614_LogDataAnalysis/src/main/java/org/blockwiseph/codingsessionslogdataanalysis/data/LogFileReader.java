package org.blockwiseph.codingsessionslogdataanalysis.data;

import java.util.List;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;

public interface LogFileReader {
	List<LogEvent> getEventsFromFile();
}
