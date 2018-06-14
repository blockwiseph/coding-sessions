package org.blockwiseph.codingsessionslogdataanalysis.logevent;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EventFactoryTest {

	EventFactory eventFactory;

	@Before
	public void setup() {
		eventFactory = new EventFactory();
	}

	@Test
	public void loginEvent() throws InvalidLogLineException {
		LoginEvent expectedEvent = new LoginEvent("test@gmail.com");
		LogEvent actualEvent = eventFactory.fromLogLine("LOGIN test@gmail.com");
		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = InvalidLogLineException.class)
	public void loginEventInvalid() throws InvalidLogLineException {
		eventFactory.fromLogLine("LOGIN");
	}

	@Test
	public void logoutEvent() throws InvalidLogLineException {
		LogoutEvent expectedEvent = new LogoutEvent("test3@gmail.com");
		LogEvent actualEvent = eventFactory.fromLogLine("LOGOUT test3@gmail.com");
		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = InvalidLogLineException.class)
	public void logoutEventInvalid() throws InvalidLogLineException {
		eventFactory.fromLogLine("LOGOUT");
	}

	@Test
	public void crashEvent() throws InvalidLogLineException {
		CrashEvent expectedEvent = new CrashEvent("test@gmail.com");
		LogEvent actualEvent = eventFactory.fromLogLine("CRASH test@gmail.com");
		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = InvalidLogLineException.class)
	public void crashEventInvalid() throws InvalidLogLineException {
		eventFactory.fromLogLine("CRASH");
	}

	@Test
	public void purchaseEvent() throws InvalidLogLineException {
		PurchaseEvent expectedEvent = new PurchaseEvent("test@gmail.com", 10, 24.5);
		LogEvent actualEvent = eventFactory.fromLogLine("PURCHASE test@gmail.com 10 24.5");
		assertEquals(expectedEvent, actualEvent);
	}

	@Test(expected = InvalidLogLineException.class)
	public void purchaseEventInvalid() throws InvalidLogLineException {
		eventFactory.fromLogLine("PURCHASE test@gmail.com asd asds");
	}

	@Test(expected = InvalidLogLineException.class)
	public void nullLine_shouldThrowException() throws InvalidLogLineException {
		eventFactory.fromLogLine(null);
	}

	@Test(expected = InvalidLogLineException.class)
	public void blankLine_shouldThrowException() throws InvalidLogLineException {
		eventFactory.fromLogLine("");
	}

	@Test(expected = InvalidLogLineException.class)
	public void blankLineUntrimmed_shouldThrowException() throws InvalidLogLineException {
		eventFactory.fromLogLine(" ");
	}

	@Test(expected = InvalidLogLineException.class)
	public void invalidEventType_shouldThrowException() throws InvalidLogLineException {
		eventFactory.fromLogLine("INVALID ");
	}

	@Test(expected = InvalidLogLineException.class)
	public void invalidEventTypeWithEmail_shouldThrowException() throws InvalidLogLineException {
		eventFactory.fromLogLine("INVALID test@gmail.com");
	}
}
