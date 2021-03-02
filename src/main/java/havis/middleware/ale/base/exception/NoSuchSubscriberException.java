package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NoSuchSubscriberException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NoSuchSubscriberException() {
	}

	public NoSuchSubscriberException(String reason) {
		super(reason);
	}
}