package havis.middleware.ale.base.exception;

import java.util.Arrays;

/**
 * Implements the corresponding ALEException
 */
public class ALEException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * The reason
	 */
	String reason;

	/**
	 * Creates a new instance
	 */
	public ALEException() {
	}

	/**
	 * Creates a new instance
	 *
	 * @param reason
	 *            The reason
	 */
	public ALEException(String reason) {
		this();
		this.reason = reason;
	}

	/**
	 * Creates a new instance
	 *
	 * @param e
	 *            The exception
	 */
	public ALEException(Exception e) {
		this();
		this.reason = String.format("[%s]\n\r%s\n\r%s", e.getClass().getName(),
				e.getMessage(), Arrays.asList(e.getStackTrace()));
	}

	/**
	 * Gets the reason
	 *
	 * @return The reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Sets the reason
	 *
	 * @param reason
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Gets the reason
	 */
	@Override
	public String getMessage() {
		return reason;
	}
}