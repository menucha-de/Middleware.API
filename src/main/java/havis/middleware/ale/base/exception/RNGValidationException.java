package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class RNGValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public RNGValidationException() {
	}

	public RNGValidationException(String reason) {
		super(reason);
	}
}