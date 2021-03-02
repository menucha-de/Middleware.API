package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class DuplicateSubscriptionException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public DuplicateSubscriptionException() {
	}

	public DuplicateSubscriptionException(String reason) {
		super(reason);
	}
}