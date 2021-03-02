package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class ImmutableReaderException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public ImmutableReaderException() {
	}

	public ImmutableReaderException(String reason) {
		super(reason);
	}
}