package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NoSuchPathException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NoSuchPathException() {
	}

	public NoSuchPathException(String reason) {
		super(reason);
	}
}