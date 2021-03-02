package havis.middleware.ale.base.operation;

/**
 * Class to aggregate data objects used by a
 * Havis.Middleware.ALE.ReaderIReaderConnector implementation. This class is
 * used by the Havis.Middleware.ALE.Reader.IReaderConnector to notify
 * Havis.Middleware.ALE.Reader.Tag, Havis.Middleware.ALE.Reader.Port and
 * Havis.Middleware.ALE.Reader.Error object through the
 * Havis.Middleware.ALE.Reader.Callback to the middleware.
 */
public class Data {

	/**
	 * Retrieves the uri
	 */
	private String uri;

	/**
	 * Retrieves true if if all requested operation are completed, false
	 * otherwise.
	 */
	private boolean isCompleted;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Data))
            return false;
        Data other = (Data) obj;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        return true;
    }

}
