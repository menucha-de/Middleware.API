package havis.middleware.ale.base.operation.tag.result;

import havis.middleware.ale.base.operation.tag.Tag;

/**
 * Class that represents a tag write result object to describe a specific result
 * within a {@link Tag} object. It contains the number of word that were
 * written.
 */
public class WriteResult extends Result {

	private static final long serialVersionUID = 1L;

	private int numWordsWritten;

	public WriteResult() {
		super();
	}

	public WriteResult(ResultState state) {
		super(state);
	}

	public WriteResult(ResultState state, int numWordsWritten) {
		this(state);
		this.numWordsWritten = numWordsWritten;
	}

	public void setNumWordsWritten(int numWordsWritten) {
		this.numWordsWritten = numWordsWritten;
	}

	/**
	 * Gets the number of words written
	 */
	public int getNumWordsWritten() {
		return numWordsWritten;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + numWordsWritten;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof WriteResult))
			return false;
		WriteResult other = (WriteResult) obj;
		if (numWordsWritten != other.numWordsWritten)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WriteResult [numWordsWritten=" + numWordsWritten + ", state=" + state + "]";
	}
}