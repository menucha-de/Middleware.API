package havis.middleware.ale.base.operation.port.result;

import havis.middleware.ale.base.operation.port.Port;

/**
 * Class that represents a port read result object to describe a specific result
 * within a {@link Port} object. It contains the data that were read from the
 * tag.
 */
public class ReadResult extends Result {

	private static final long serialVersionUID = 1L;

	private byte data;

    public ReadResult(Result.State state) {
        super(state);
    }

    public ReadResult(Result.State state, byte data) {
        this(state);
        this.data = data;
    }

    /**
     * The information that were read during a port operation or observation.
     */
    public byte getData() {
        return data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + data;
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
        if (data != other.data)
            return false;
        return true;
    }

	@Override
	public String toString() {
		return "ReadResult [data=" + data + ", state()=" + getState() + "]";
	}
}