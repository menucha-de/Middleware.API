package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class PermissionValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public PermissionValidationException() {
	}

	public PermissionValidationException(String reason) {
		super(reason);
	}
}