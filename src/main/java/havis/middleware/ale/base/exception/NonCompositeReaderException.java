package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NonCompositeReaderException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NonCompositeReaderException() {
	}

	public NonCompositeReaderException(String reason) {
		super(reason);
	}
}