package havis.middleware.ale.base.operation.port;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a port observation
 */
public class PortObservation implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id = System.identityHashCode(this);

	private List<Pin> pins;

	/**
	 * Creates a new empty port observation
	 */
	public PortObservation() {
		this.pins = new ArrayList<Pin>();
	}

	/**
	 * Creates a new port observation
	 * 
	 * @param pins
	 *            a set of {@link Pin} that should be observed.
	 */
	public PortObservation(List<Pin> pins) {
		this.pins = new ArrayList<Pin>(pins);
	}

	public int getId() {
		return id;
	}

	/**
	 * @return set of {@link Pin} that should be observed.
	 */
	public List<Pin> getPins() {
		return pins;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pins == null) ? 0 : pins.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PortObservation))
			return false;
		PortObservation other = (PortObservation) obj;
		if (pins == null) {
			if (other.pins != null)
				return false;
		} else if (!pins.equals(other.pins))
			return false;
		return true;
	}
}
