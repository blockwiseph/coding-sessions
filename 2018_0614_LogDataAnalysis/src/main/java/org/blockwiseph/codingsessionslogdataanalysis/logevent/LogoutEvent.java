package org.blockwiseph.codingsessionslogdataanalysis.logevent;

import lombok.Value;

@Value
public class LogoutEvent implements LogEvent{
	String email;

	public EventType getTag() {
		return EventType.LOGOUT;
	}
}
