package org.blockwiseph.codingsessionslogdataanalysis.logevent;

public class EventFactory {
	public LogEvent fromLogLine(String logline) throws InvalidLogLineException {
		try {
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
				default:
					throw new InvalidLogLineException("Invalid event" + logline);
			}
		} catch (Exception e) {
			throw new InvalidLogLineException("Invalid event" + logline);
		}
	}
}
