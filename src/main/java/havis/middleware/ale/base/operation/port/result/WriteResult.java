package havis.middleware.ale.base.operation.port.result;

import havis.middleware.ale.base.operation.port.Port;

/**
 * Class that represents a port write result object to describe a specific
 * result within a {@link Port} object.
 */
public class WriteResult extends Result {

	private static final long serialVersionUID = 1L;

	public WriteResult(Result.State state) {
        super(state);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof WriteResult))
            return false;
        return true;
    }

	@Override
	public String toString() {
		return "WriteResult [state()=" + getState() + "]";
	}
}