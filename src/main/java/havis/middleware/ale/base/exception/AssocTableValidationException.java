package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class AssocTableValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public AssocTableValidationException() {
	}

	public AssocTableValidationException(String reason) {
		super(reason);
	}
}