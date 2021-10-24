package de.softquadrat.square1.exception;

public class Square1Exception extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7123763999531614789L;

	public Square1Exception() {
	}

	public Square1Exception(String message) {
		super(message);
	}

	public Square1Exception(Throwable cause) {
		super(cause);
	}

	public Square1Exception(String message, Throwable cause) {
		super(message, cause);
	}
}
