package havis.middleware.ale.base.operation.port;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a port operation
 */
public class PortOperation implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Operation> operations;

	/**
	 * Creates a new port operation
	 * @param operations the operations
	 */
	public PortOperation(List<Operation> operations) {
		this.operations = new ArrayList<Operation>(operations);
	}

	/**
	 * @return the operations
	 */
	public List<Operation> getOperations() {
		return this.operations;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((operations == null) ? 0 : operations.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof PortOperation))
            return false;
        PortOperation other = (PortOperation) obj;
        if (operations == null) {
            if (other.operations != null)
                return false;
        } else if (!operations.equals(other.operations))
            return false;
        return true;
    }
}
