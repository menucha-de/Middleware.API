package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class InvalidPatternException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public InvalidPatternException() {
	}

	public InvalidPatternException(String reason) {
		super(reason);
	}
}