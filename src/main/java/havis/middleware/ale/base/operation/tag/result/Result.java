package havis.middleware.ale.base.operation.tag.result;

import java.io.Serializable;

/**
 * Class that represents a tag result object to describe a abstract result
 * within a tag. It contains the operation result as specified in LLRP 1.1
 * (16.2.1.5.7). All reader results should inherit from this class.
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The result of the reader operation
	 */
	protected ResultState state;

	public Result() {
		super();
	}

	public Result(ResultState state) {
		this();
		this.state = state;
	}

	public ResultState getState() {
		return state;
	}

	public void setState(ResultState state) {
		this.state = state;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Result))
			return false;
		Result other = (Result) obj;
		if (state != other.state)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Result [state=" + state + "]";
	}
}
