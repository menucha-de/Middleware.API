package havis.middleware.ale.base.operation.port;

import java.io.Serializable;

/**
 * Class that represents the port pin object to describe a specific pin within a
 * {@link Port} object.
 * 
 * It contains the id and the type of the specific pin port.
 */
public class Pin implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Enumeration that represents the given pin types which are used to
	 * describe a specific pin through the {@link Pin} class.
	 */
	public enum Type {

		/**
		 * Describe a input pin.
		 */
		INPUT,

		/**
		 * Describe a output pin.
		 */
		OUTPUT
	}

	private int id;

	private Type type;

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The pin id
	 * @param type
	 *            The pin type
	 */
	public Pin(int id, Type type) {
		this.id = id;
		this.type = type;
	}

	/**
	 * Gets the id of an specific pin, which starts with 1.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the type of the specific pin.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns if a pin matches another.
	 * 
	 * @param other
	 *            The other pin object
	 * @return true if other matches, false otherwise
	 */
	public boolean matches(Pin other) {
		if (other != null) {
			return (this.type == other.type) && ((this.id == -1) || (other.id == -1) || (this.id == other.id));
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pin))
			return false;
		Pin other = (Pin) obj;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
