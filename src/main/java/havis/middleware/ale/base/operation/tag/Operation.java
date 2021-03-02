package havis.middleware.ale.base.operation.tag;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class that represents the operation object for tag.
 * 
 * This class is used by the {@link TagOperation} to specify a port operation.
 * 
 */
public class Operation implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Retrieve the tag operation id.
	 */
	int id;

	/**
	 * Retrieves the tag operation type.
	 */
	OperationType type;

	/**
	 * Retrieves the field of the tag operation.
	 */
	Field field;

	/**
	 * Retrieves the data to transfer to the reader connector i.e. data to write
	 * or the access password
	 */
	byte[] data;

	/**
	 * Retrieves the length in bits of the data
	 */
	int bitLength;

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The tag operation id
	 * @param type
	 *            The tag operation type
	 * @param field
	 *            The tag operation field
	 * @param data
	 *            The tag operation data
	 * @param bitLength
	 *            The length in bits of the data
	 */
	public Operation(int id, OperationType type, Field field, byte[] data, int bitLength) {
		this.id = id;
		this.type = type;
		this.field = field;
		this.data = data;
		this.bitLength = bitLength;
	}

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The tag operation id
	 * @param type
	 *            The tag operation type
	 * @param data
	 *            The tag operation data
	 * @param bitLength
	 *            The length in bits of the data
	 */
	public Operation(int id, OperationType type, byte[] data, int bitLength) {
		this(id, type, null, data, bitLength);
	}

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The tag operation id
	 * @param type
	 *            The tag operation type
	 * @param field
	 *            The tag operation field
	 * @param data
	 *            The tag operation data
	 */
	public Operation(int id, OperationType type, Field field, byte[] data) {
		this(id, type, field, data, data != null ? data.length * 8 : 0);
	}

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The tag operation id
	 * @param type
	 *            The tag operation type
	 * @param data
	 *            The tag operation data
	 */
	public Operation(int id, OperationType type, byte[] data) {
		this(id, type, null, data, data != null ? data.length * 8 : 0);
	}

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The tag operation id
	 * @param type
	 *            The tag operation type
	 * @param field
	 *            The tag operation field
	 */
	public Operation(int id, OperationType type, Field field) {
		this(id, type, field, null, 0);
	}

	/**
	 * Creates a new instance
	 * 
	 * @param id
	 *            The tag operation id
	 */
	public Operation(int id) {
		this(id, null, null, null, 0);
	}

	/**
	 * Creates a new instance
	 */
	public Operation() {
		this(0, null, null, null, 0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getBitLength() {
		return bitLength;
	}

	public void setBitLength(int bitLength) {
		this.bitLength = bitLength;
	}

	/**
	 * Returns the operation data
	 * 
	 * @return The operation data
	 */
	public byte[] getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bitLength;
		result = prime * result + Arrays.hashCode(data);
		result = prime * result + ((field == null) ? 0 : field.hashCode());
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
		if (!(obj instanceof Operation))
			return false;
		Operation other = (Operation) obj;
		if (bitLength != other.bitLength)
			return false;
		if (!Arrays.equals(data, other.data))
			return false;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (id != other.id)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
