package havis.middleware.ale.base.operation.port;

import havis.middleware.ale.base.IO;
import havis.middleware.ale.base.operation.Data;
import havis.middleware.ale.base.operation.port.result.Result;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents the port object for port notification from
 * Havis.Middleware.ALE.Reader.IReaderConnector implementations.
 *
 * This class is used to transfer port data from a
 * Havis.Middleware.ALE.Reader.IReaderConnector implementation to the
 * middleware. It contains the port information and a set of {link Result}. This
 * class overrides GetHashCode and Equals method, so that two port with equal
 * name and Pin are equal.
 */
public class Port extends Data implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private Pin pin;

    /**
     * The name of the logical base reader instance that contain the described
     * port.
     */
    private String name;

    private Map<Integer, Result> result;

    public Port() {
    }

    public Port(Map<Integer, Result> result) {
        this.result = result;
    }

    public Port(Pin pin, String name, Map<Integer, Result> result) {
        this.pin = pin;
        this.name = name;
        this.result = result;
    }

    /**
     * Gets the name of the logical base reader instance that contain the
     * described port.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the specific pin that is described by this object.
     */
    public Pin getPin() {
        return pin;
    }

    /**
     * Set the pin
     *
     * @param pin
     *            the pin to set
     */
    public void setPin(Pin pin) {
        this.pin = pin;
    }

    /**
     * A set of results produced for this port.
     */
    public Map<Integer, Result> getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pin == null) ? 0 : pin.hashCode());
        result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (!(obj instanceof Port))
            return false;
        Port other = (Port) obj;
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
        if (result == null) {
            if (other.result != null)
                return false;
        } else if (!result.equals(other.result))
            return false;
        return true;
    }

    /**
     * Method to retrieve the string representation of this port object.
     *
     * Returns the string representation of port
     *
     * @return The string representation
     */
    @Override
    public String toString() {
        if (pin instanceof Pin) {
            String type;
            switch (pin.getType()) {
            case INPUT:
                type = "in";
                break;
            case OUTPUT:
                type = "out";
                break;
            default:
                type = null;
                break;
            }
            return "urn:havis:ale:port:" + (name == null ? "" : name) + "." + (type == null ? "" : type) + "." + pin.getId();
        } else {
            return null;
        }
    }

    public havis.middleware.ale.base.Port port() {
        havis.middleware.ale.base.Pin pin = null;
        if (this.pin != null) {
            pin = new havis.middleware.ale.base.Pin(this.pin.getId(), this.pin.getType() == Pin.Type.INPUT ? IO.INPUT : IO.OUTPUT);
        }
        return new havis.middleware.ale.base.Port(pin, this.name);
    }

    @Override
    public Port clone() {
        Pin pin = null;
        if (this.pin != null) {
            pin = new Pin(this.pin.getId(), this.pin.getType());
        }
        return new Port(pin, this.name, new HashMap<Integer, Result>(this.result));
    }
}
