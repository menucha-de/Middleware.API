package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NoSuchPermissionException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NoSuchPermissionException() {
	}

	public NoSuchPermissionException(String reason) {
		super(reason);
	}
}