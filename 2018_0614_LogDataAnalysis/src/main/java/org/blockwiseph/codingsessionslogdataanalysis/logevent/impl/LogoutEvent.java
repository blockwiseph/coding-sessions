package org.blockwiseph.codingsessionslogdataanalysis.logevent.impl;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;

import lombok.Value;

@Value
public class LogoutEvent implements LogEvent{
	String email;

	public EventType getTag() {
		return EventType.LOGOUT;
	}
}
