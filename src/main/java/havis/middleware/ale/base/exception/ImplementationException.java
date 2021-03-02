package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class ImplementationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public ImplementationException() {
	}

	public ImplementationException(String reason) {
		super(reason);
	}

	public ImplementationException(Exception e) {
		super(e);
	}
}