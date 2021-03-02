package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class ParameterException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public ParameterException() {
	}

	public ParameterException(String reason) {
		super(reason);
	}
}