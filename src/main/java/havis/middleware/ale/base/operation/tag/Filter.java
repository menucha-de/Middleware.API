package havis.middleware.ale.base.operation.tag;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class that represents a filter for a tag operation. This class is used by the
 * Havis.Middleware.ALE.Reader.ReaderController to bound a tag operations to a
 * specific tag or a set of tags for a
 * Havis.Middleware.ALE.Reader.IReaderConnector implementation.
 *
 * It contains the bank, length, offset and mask to determine equality.
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = 1L;

	private int bank;

	private int length;

	private int offset;

	private byte[] mask;

	/**
	 * Creates a new instance
	 *
	 * @param bank
	 *            The tag memory bank
	 * @param length
	 *            The field bit length
	 * @param offset
	 *            The field bit offset
	 * @param mask
	 *            The bit mask
	 */
	public Filter(int bank, int length, int offset, byte[] mask) {
		this.bank = bank;
		this.length = length;
		this.offset = offset;
		this.mask = mask;
	}

	/**
	 * Gets the bank of the tag
	 */
	public int getBank() {
		return bank;
	}

	/**
	 * Gets the length of the filter
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Gets the offset within the bank
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * Gets Bitwise tag mask to determine which bits will be compared
	 */
	public byte[] getMask() {
		return mask;
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bank;
        result = prime * result + length;
        result = prime * result + Arrays.hashCode(mask);
        result = prime * result + offset;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Filter))
            return false;
        Filter other = (Filter) obj;
        if (bank != other.bank)
            return false;
        if (length != other.length)
            return false;
        if (!Arrays.equals(mask, other.mask))
            return false;
        if (offset != other.offset)
            return false;
        return true;
    }
}
