package havis.middleware.ale.base.operation.tag;

import java.io.Serializable;

/**
 * Represents a field for a tag operation
 * 
 * It contains the bank, length and offset of the field.
 */
public class Field implements Serializable {

	private static final long serialVersionUID = 1L;

	private int hashCode = -1;

	private String name;

	private int bank;

	private int offset;

	private int length;

	public Field(String name, int bank, int offset, int length) {
		this.name = name;
		this.bank = bank;
		this.offset = offset;
		this.length = length;
	}

	/**
	 * Gets the name of the field i.e. password field i.e. killPasswd or
	 * lockPassword
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the field i.e. password field i.e. killPasswd or
	 * lockPassword
	 */
	public void setName(String name) {
		this.name = name;
		this.hashCode = -1;
	}

	/**
	 * Gets the tag bank
	 */
	public int getBank() {
		return bank;
	}

	/**
	 * Sets the tag bank
	 */
	public void setBank(int bank) {
		this.bank = bank;
		this.hashCode = -1;
	}

	/**
	 * Gets the offset within the bank
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Sets the offset within the bank
	 */
	public void setOffset(int offset) {
		this.offset = offset;
		this.hashCode = -1;
	}

	/**
	 * Gets the length of data on bank
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Sets the length of data on bank
	 */
	public void setLength(int length) {
		this.length = length;
		this.hashCode = -1;
	}

	/**
	 * Returns if fields are equal
	 * 
	 * @param field
	 *            The field to compare
	 * @return True if objects are equal, false otherwise
	 */
	boolean equals(Field field) {
		return ((name instanceof String) && name.equals(field.name)) && (bank == field.bank) && (offset == field.offset) && (length == field.length);
	}

	/**
	 * Returns if objects are equal
	 * 
	 * @param obj
	 *            The object to compare
	 * @return True if objects are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj == this) || (obj.hashCode() == hashCode()) && (obj instanceof Field) && equals((Field) obj);
	}

	/**
	 * Retrieves the hash code
	 * 
	 * @return The hash code
	 */
	@Override
	public int hashCode() {
		if (hashCode == -1) {
			hashCode = ((bank * 41 + length) * 41 + offset) * 41 + (name == null ? 0 : name.hashCode());
		}
		return hashCode;
	}
}
