package org.blockwiseph.codingsessionslogdataanalysis.logevent.impl;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;

import lombok.Value;

@Value
public class CrashEvent implements LogEvent{
	String email;

	public EventType getTag() {
		return EventType.CRASH;
	}
}
