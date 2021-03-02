package havis.middleware.ale.base.operation.tag.result;

import havis.middleware.ale.base.operation.tag.Tag;

/**
 * Class that represents a tag lock result object to describe a specific result
 * within a {@link Tag } object.
 */
public class LockResult extends Result {

	private static final long serialVersionUID = 1L;

	public LockResult() {
		super();
	}

	public LockResult(ResultState state) {
		this();
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return obj instanceof LockResult;
	}

	@Override
	public String toString() {
		return "LockResult [state=" + state + "]";
	}
}