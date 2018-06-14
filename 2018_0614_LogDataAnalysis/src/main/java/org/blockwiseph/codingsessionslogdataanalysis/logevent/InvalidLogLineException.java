package org.blockwiseph.codingsessionslogdataanalysis.logevent;

public class InvalidLogLineException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidLogLineException(String message) {
		super(message);
	}
}
