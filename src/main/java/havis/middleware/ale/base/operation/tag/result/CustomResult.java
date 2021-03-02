package havis.middleware.ale.base.operation.tag.result;

import java.util.Arrays;

/**
 * Class that represents a result from a custom operation, containing the data
 * returned from the reader.
 */
public class CustomResult extends Result {

	private static final long serialVersionUID = 1L;

	/**
	 * The response data
	 */
	protected byte[] data;

	public CustomResult() {
		super();
	}

	public CustomResult(ResultState state) {
		super(state);
	}

	public CustomResult(ResultState state, byte[] data) {
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
		if (!(obj instanceof CustomResult))
			return false;
		CustomResult other = (CustomResult) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomResult [data=" + Arrays.toString(data) + ", state=" + state + "]";
	}
}
