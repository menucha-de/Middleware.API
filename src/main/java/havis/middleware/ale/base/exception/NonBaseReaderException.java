package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NonBaseReaderException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NonBaseReaderException() {
	}

	public NonBaseReaderException(String reason) {
		super(reason);
	}
}