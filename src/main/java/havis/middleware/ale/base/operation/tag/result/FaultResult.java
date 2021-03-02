package havis.middleware.ale.base.operation.tag.result;

/**
 * Implements the tag fault result
 * 
 * This class is used for an operation that could not be send to the reader
 * because of validation problems in previous steps
 */
public class FaultResult extends Result {

	private static final long serialVersionUID = 1L;

	public FaultResult(ResultState state) {
		super(state);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return obj instanceof FaultResult;
	}

	@Override
	public String toString() {
		return "FaultResult [state=" + state + "]";
	}
}