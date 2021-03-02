package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class NoSuchPropertyException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public NoSuchPropertyException() {
	}

	public NoSuchPropertyException(String reason) {
		super(reason);
	}
}