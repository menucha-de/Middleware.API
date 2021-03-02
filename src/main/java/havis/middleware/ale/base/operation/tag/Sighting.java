package havis.middleware.ale.base.operation.tag;

import java.io.Serializable;
import java.util.Date;

/**
 * Implements the sighting
 */
public class Sighting implements Serializable {

	private static final long serialVersionUID = 1L;

	private String host;

	private short antenna;

	private int strength;

	private Date timestamp;

	/**
	 * Gets the tag sighting host
	 * 
	 * @return The tag sighting host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Sets the tag sighting host
	 * 
	 * @param host
	 *            The tag sighting host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * Gets the tag sighting antenna id
	 * 
	 * @return The tag sighting antenna id
	 */
	public short getAntenna() {
		return antenna;
	}

	/**
	 * Sets the tag sighting antenna id
	 * 
	 * @param antenna
	 *            The tag sighting antenna id
	 */
	public void setAntenna(short antenna) {
		this.antenna = antenna;
	}

	/**
	 * Gets the tag sighting signal strength
	 * 
	 * @return The tag sighting signal strength
	 */
	public int getStrength() {
		return strength;
	}

	/**
	 * Sets the tag sighting signal strength
	 * 
	 * @param strength
	 *            The tag sighting signal strength
	 */
	public void setStrength(int strength) {
		this.strength = strength;
	}

	/**
	 * Gets the tag sighting timestamp
	 * 
	 * @return The tag sighting timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the tag sighting timestamp
	 * 
	 * @param timestamp
	 *            The tag sighting timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Create a new instance
	 */
	public Sighting() {
		this.timestamp = new Date();
	}

	/**
	 * Create a new instance
	 * 
	 * @param host
	 *            The host
	 * @param antenna
	 *            The antenna id
	 * @param strength
	 *            The strength
	 */
	public Sighting(String host, short antenna, int strength) {
		this.host = host;
		this.antenna = antenna;
		this.strength = strength;
		this.timestamp = new Date();
	}

	/**
	 * Create a new instance
	 * 
	 * @param host
	 *            The host
	 * @param antenna
	 *            The antenna id
	 * @param strength
	 *            The strength
	 * @param timestamp
	 *            The timestamp
	 */
	public Sighting(String host, short antenna, int strength, Date timestamp) {
		this.host = host;
		this.antenna = antenna;
		this.strength = strength;
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + antenna;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + strength;
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Sighting))
			return false;
		Sighting other = (Sighting) obj;
		if (antenna != other.antenna)
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (strength != other.strength)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
}