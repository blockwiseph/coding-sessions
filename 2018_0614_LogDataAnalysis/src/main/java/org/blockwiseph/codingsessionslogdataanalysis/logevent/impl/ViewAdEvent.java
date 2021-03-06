package org.blockwiseph.codingsessionslogdataanalysis.logevent.impl;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;

import lombok.Value;

@Value
public class ViewAdEvent implements LogEvent {
	String email;
	String adId;

	@Override
	public EventType getTag() {
		return EventType.VIEW_AD;
	}
}
