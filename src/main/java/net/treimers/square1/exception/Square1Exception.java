package net.treimers.square1.exception;

/**
 * Instances of this class are used to signal errors in Square-1 application.
 */
public class Square1Exception extends Exception {
	/** The serial version uuid. */
	private static final long serialVersionUID = -7123763999531614789L;

	/**
	 * Creates a new instance.
	 */
	public Square1Exception() {
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param message the exception message.
	 */
	public Square1Exception(String message) {
		super(message);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param cause the cause exception.
	 */
	public Square1Exception(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance.
	 * 
	 * @param message the exception message.
	 * @param cause the cause exception.
	 */
	public Square1Exception(String message, Throwable cause) {
		super(message, cause);
	}
}
