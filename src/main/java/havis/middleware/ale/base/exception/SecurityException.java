package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class SecurityException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public SecurityException() {
	}

	public SecurityException(String reason) {
		super(reason);
	}
}