package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class InvalidEPCException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public InvalidEPCException() {
	}

	public InvalidEPCException(String reason) {
		super(reason);
	}
}