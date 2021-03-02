package havis.middleware.ale.base.operation.tag.result;

import havis.middleware.ale.base.operation.tag.Tag;

/**
 * Class that represents a tag password result object to describe a specific
 * result within a {@link Tag} object.
 */
public class PasswordResult extends Result {

	private static final long serialVersionUID = 1L;

	public PasswordResult() {
		super();
	}

	public PasswordResult(ResultState state) {
		this();
		this.state = state;
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return obj instanceof PasswordResult;
	}

	@Override
	public String toString() {
		return "PasswordResult [state=" + state + "]";
	}
}