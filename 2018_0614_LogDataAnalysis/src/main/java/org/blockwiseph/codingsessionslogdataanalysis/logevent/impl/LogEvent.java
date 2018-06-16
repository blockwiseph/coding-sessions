package org.blockwiseph.codingsessionslogdataanalysis.logevent.impl;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;

public interface LogEvent {
	
	public EventType getTag();

	public String getEmail();
}
