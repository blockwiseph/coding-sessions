package org.blockwiseph.codingsessionslogdataanalysis.logevent;

import lombok.Value;

@Value
public class LoginEvent implements LogEvent{
	String email;

	public EventType getTag() {
		return EventType.LOGIN;
	}
}
