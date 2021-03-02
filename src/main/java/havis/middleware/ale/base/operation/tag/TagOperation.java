package havis.middleware.ale.base.operation.tag;

import java.io.Serializable;
import java.util.List;

/**
 * Implements the tag operation
 */
public class TagOperation implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id = System.identityHashCode(this);

	/**
	 * A set of {@link Operation}.
	 */
	private List<Operation> operations;

	/**
	 * A set of {@link Filter} to specify on which tags the {@link Operation}
	 * should be executed on.
	 */
	private List<Filter> filter;

	public TagOperation(List<Operation> operations) {
		this.operations = operations;
	}

	public TagOperation(List<Operation> operations, List<Filter> filter) {
		this.operations = operations;
		this.filter = filter;
	}

	public int getId() {
		return id;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public List<Filter> getFilter() {
		return filter;
	}

	public void setFilter(List<Filter> filter) {
		this.filter = filter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((filter == null) ? 0 : filter.hashCode());
		result = prime * result + ((operations == null) ? 0 : operations.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TagOperation))
			return false;
		TagOperation other = (TagOperation) obj;
		if (filter == null) {
			if (other.filter != null)
				return false;
		} else if (!filter.equals(other.filter))
			return false;
		if (operations == null) {
			if (other.operations != null)
				return false;
		} else if (!operations.equals(other.operations))
			return false;
		return true;
	}
}
