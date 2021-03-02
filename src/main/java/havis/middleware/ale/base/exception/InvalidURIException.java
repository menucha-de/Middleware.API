package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class InvalidURIException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public InvalidURIException() {
	}

	public InvalidURIException(String reason) {
		super(reason);
	}
}