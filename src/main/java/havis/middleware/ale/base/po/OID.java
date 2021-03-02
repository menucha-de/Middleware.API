package havis.middleware.ale.base.po;

/**
 * This class specified the iso15962 oid object
 */
public class OID {

	private String oid;

	private String prefix;

	/**
	 * Creates a new instance
	 * 
	 * @param oid
	 *            The oid
	 */
	public OID(String oid) {
		this.oid = oid;
		this.prefix = createPrefix(oid);
	}

	private String createPrefix(String oid) {
		// prefix is OID without asterisk, if any
		if (oid != null && oid.length() > 0 && oid.charAt(oid.length() - 1) == '*') {
			return oid.substring(0, oid.length() - 1);
		}
		return null;
	}

	/**
	 * @return the oid
	 */
	public String getOid() {
		return oid;
	}
	
	/**
	 * Returns whether the OID matches this OID
	 * 
	 * @param oid the OID to match
	 * @return true if the OID matches this OID
	 */
	public boolean matches(String oid) {
		if (oid != null) {
			return this.prefix != null ? oid.startsWith(this.prefix) : oid.equals(this.oid);
		}
		return false;
	}

	/**
	 * Returns whether this OID is a pattern
	 * 
	 * @return whether this OID is a pattern, e.g. urn:oid:1.0.15961.9.&#42;
	 */
	public boolean isPattern() {
		return this.prefix != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof OID))
			return false;
		OID other = (OID) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OID [oid=" + oid + "]";
	}
}
