package org.blockwiseph.codingsessionslogdataanalysis.logevent.impl;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;

import lombok.Value;

@Value
public class PurchaseEvent implements LogEvent {
	String email;
	Integer quantity;
	Double amount;

	public EventType getTag() {
		return EventType.PURCHASE;
	}
}
