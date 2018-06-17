package org.blockwiseph.codingsessionslogdataanalysis.logevent.factory;

import org.blockwiseph.codingsessionslogdataanalysis.logevent.EventType;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.LogEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.CrashEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LoginEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.LogoutEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.PurchaseEvent;
import org.blockwiseph.codingsessionslogdataanalysis.logevent.impl.ViewAdEvent;

public class EventFactory {

	public LogEvent fromLogLine(String logline) throws InvalidLogLineException {
		try {
			return tryToParse(logline);
		} catch (Exception e) {
			throw new InvalidLogLineException("Invalid event" + logline);
		}
	}

	private static LogEvent tryToParse(String logline) throws InvalidLogLineException {
		String[] lineParts = logline.split(" ");
		String tag = lineParts[0];
		EventType eventType = EventType.valueOf(tag);
		switch (eventType) {
			case LOGIN:
				return new LoginEvent(lineParts[1]);
			case LOGOUT:
				return new LogoutEvent(lineParts[1]);
			case CRASH:
				return new CrashEvent(lineParts[1]);
			case PURCHASE:
				return new PurchaseEvent(lineParts[1], Integer.parseInt(lineParts[2]), Double.parseDouble(lineParts[3]));
			case VIEW_AD:
				return new ViewAdEvent(lineParts[1], lineParts[2]);
			default:
				throw new InvalidLogLineException("Invalid event" + logline);
		}
	}
}
