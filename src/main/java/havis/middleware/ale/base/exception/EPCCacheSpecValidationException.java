package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class EPCCacheSpecValidationException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public EPCCacheSpecValidationException() {
	}

	public EPCCacheSpecValidationException(String reason) {
		super(reason);
	}
}