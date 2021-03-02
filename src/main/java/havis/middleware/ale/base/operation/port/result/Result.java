package havis.middleware.ale.base.operation.port.result;

import havis.middleware.ale.base.operation.port.Port;

import java.io.Serializable;

/**
 * Class that represents a port result object to describe a abstract result
 * within a {@link Port} object.
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * This enumeration provides the port result states
	 * 
	 * Enumeration that represents the given results states which are used to
	 * describe a specific port result through the {@link Result} class.
	 */
	public enum State {

		/**
		 * Describe the success state.
		 */
		SUCCESS,

		/**
		 * An error occurred during operation. The reader is unchanged
		 */
		MISC_ERROR_TOTAL,

		/**
		 * An error occurred during operation. The reader is partial changed
		 */
		MISC_ERROR_PARTIAL,

		/**
		 * Port was not found
		 */
		PORT_NOT_FOUND_ERROR,

		/**
		 * Operation is not possible because of reader limitation
		 */
		OP_NOT_POSSIBLE_ERROR
	}

	private State state;

	public Result(Result.State state) {
		this.state = state;
	}

	/**
	 * The state of the specific port result.
	 */
	public State getState() {
		return state;
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
