package org.blockwiseph.codingsessionslogdataanalysis.logevent;

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
