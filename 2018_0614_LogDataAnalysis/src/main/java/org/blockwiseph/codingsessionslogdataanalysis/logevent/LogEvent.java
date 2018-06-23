package org.blockwiseph.codingsessionslogdataanalysis.logevent;

public interface LogEvent {
	
	public EventType getTag();

	public String getEmail();
}
