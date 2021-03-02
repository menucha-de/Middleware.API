package havis.middleware.ale.base.exception;

/**
 * Implements the corresponding ALEException
 */
public class InvalidAssocTableEntryException extends ALEException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new instance
	 */
	public InvalidAssocTableEntryException() {
	}

	public InvalidAssocTableEntryException(String reason) {
		super(reason);
	}
}