package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class DuplicateRoleException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public DuplicateRoleException() {
	}

	public DuplicateRoleException(String reason) {
		super(reason);
	}
}