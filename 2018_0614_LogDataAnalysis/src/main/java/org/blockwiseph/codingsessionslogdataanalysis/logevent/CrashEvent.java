package org.blockwiseph.codingsessionslogdataanalysis.logevent;

import lombok.Value;

@Value
public class CrashEvent implements LogEvent{
	String email;

	public EventType getTag() {
		return EventType.CRASH;
	}
}
