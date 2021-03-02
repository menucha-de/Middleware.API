package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class DuplicateClientIdentityException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public DuplicateClientIdentityException() {
	}

	public DuplicateClientIdentityException(String reason) {
		super(reason);
	}
}