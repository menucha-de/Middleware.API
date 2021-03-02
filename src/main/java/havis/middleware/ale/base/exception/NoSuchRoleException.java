package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NoSuchRoleException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NoSuchRoleException() {
	}

	public NoSuchRoleException(String reason) {
		super(reason);
	}
}