package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class UnsupportedOperationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public UnsupportedOperationException() {
	}

	public UnsupportedOperationException(String reason) {
		super(reason);
	}
}