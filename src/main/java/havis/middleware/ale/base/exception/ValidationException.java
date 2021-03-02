package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class ValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public ValidationException() {
	}

	public ValidationException(String reason) {
		super(reason);
	}
}