package havis.middleware.ale.base.operation.tag.result;

import havis.middleware.ale.base.operation.tag.Tag;

/**
 * Class that represents a tag kill result object to describe a specific result
 * within a {@link Tag} object.
 */
public class KillResult extends Result {

	private static final long serialVersionUID = 1L;

	public KillResult() {
		super();
	}

	public KillResult(ResultState state) {
		this();
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return obj instanceof KillResult;
	}

	@Override
	public String toString() {
		return "KillResult [state=" + state + "]";
	}
}