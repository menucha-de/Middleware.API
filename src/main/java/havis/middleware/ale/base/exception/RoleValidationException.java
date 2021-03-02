package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class RoleValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public RoleValidationException() {
	}

	public RoleValidationException(String reason) {
		super(reason);
	}
}