package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class InUseException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public InUseException() {
	}

	public InUseException(String reason) {
		super(reason);
	}
}