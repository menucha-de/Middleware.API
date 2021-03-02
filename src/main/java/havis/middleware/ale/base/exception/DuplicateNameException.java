package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class DuplicateNameException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public DuplicateNameException() {
	}

	public DuplicateNameException(String reason) {
		super(reason);
	}
}