package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class ReaderLoopException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public ReaderLoopException() {
	}

	public ReaderLoopException(String reason) {
		super(reason);
	}
}