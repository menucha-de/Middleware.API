package havis.middleware.ale.base.operation.port;

import java.io.Serializable;

/**
 * Class that represents the operation object for a port. This class is used by
 * the {@link PortOperation} to specify a port operation.
 */
public class Operation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Enumeration that represents the given port operation types which are used
	 * to describe a specific portOperation through the {@link Operation} class.
	 */
	public enum Type {

		/**
		 * Describe a read operation on a pin port.
		 */
		READ,

		/**
		 * Describe a write operation on a pin port.
		 */
		WRITE
	}

	private int id;
	private String name;
	private Type type;
	private Pin pin;
	private Byte data;
	private Long duration;

	public Operation(String name, Type type, Byte data, Long duration, Pin pin) {
		this.name = name;
		this.type = type;
		this.data = data;
		this.duration = duration;
		this.pin = pin;
	}

	/**
	 * Gets the port operation id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the port operation id
	 *
	 * @param id
	 *            The operation id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the port operation name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the port operation type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Gets the port pin
	 */
	public Pin getPin() {
		return pin;
	}

	/**
	 * Gets the port data
	 */
	public Byte getData() {
		return data;
	}

	/**
	 * Gets the port operation duration
	 */
	public Long getDuration() {
		return duration;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pin == null) ? 0 : pin.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Operation))
            return false;
        Operation other = (Operation) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (pin == null) {
            if (other.pin != null)
                return false;
        } else if (!pin.equals(other.pin))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
}
