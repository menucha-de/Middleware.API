package havis.middleware.ale.base.operation.tag.result;

import havis.middleware.ale.base.operation.tag.Tag;

import java.util.Arrays;

/**
 * Class that represents a tag read result object to describe a specific result
 * within a {@link Tag} object. It contains the data that were read from the
 * tag.
 */
public class ReadResult extends Result {

	private static final long serialVersionUID = 1L;

	/**
	 * The read data
	 */
	protected byte[] data;

	public ReadResult() {
		super();
	}

	public ReadResult(ResultState state) {
		super(state);
	}

	public ReadResult(ResultState state, byte[] data) {
		this(state);
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(data);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ReadResult))
			return false;
		ReadResult other = (ReadResult) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReadResult [data=" + Arrays.toString(data) + ", state=" + state + "]";
	}
}
