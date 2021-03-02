package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NoSuchIdException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NoSuchIdException() {
	}

	public NoSuchIdException(String reason) {
		super(reason);
	}
}