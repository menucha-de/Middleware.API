package havis.middleware.ale.base.operation.tag.result;

import havis.middleware.ale.base.operation.tag.Tag;

import java.util.Arrays;

/**
 * Class that represents a virtual tag read result object to describe a specific
 * result within a {@link Tag} object.
 */
public class VirtualReadResult extends ReadResult {

	private static final long serialVersionUID = 1L;

	public VirtualReadResult() {
		super();
	}

	public VirtualReadResult(ResultState state) {
		super(state);
	}

	public VirtualReadResult(ResultState state, byte[] data) {
		super(state, data);
	}

	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return obj instanceof VirtualReadResult;
	}

	@Override
	public String toString() {
		return "VirtualReadResult [data=" + Arrays.toString(data) + ", state=" + state + "]";
	}
}