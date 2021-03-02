package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class ClientIdentityValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public ClientIdentityValidationException() {
	}

	public ClientIdentityValidationException(String reason) {
		super(reason);
	}
}