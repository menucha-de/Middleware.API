package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class DuplicatePermissionException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public DuplicatePermissionException() {
	}

	public DuplicatePermissionException(String reason) {
		super(reason);
	}
}