package havis.middleware.ale.base.operation.tag;

import havis.middleware.ale.base.operation.Data;
import havis.middleware.ale.base.operation.ICount;
import havis.middleware.ale.base.operation.ISightings;
import havis.middleware.ale.base.operation.ITimestamps;
import havis.middleware.ale.base.operation.tag.result.Result;
import havis.middleware.ale.base.operation.tag.result.ResultState;
import havis.middleware.utils.data.Calculator;
import havis.middleware.utils.data.Comparison;
import havis.middleware.utils.data.Converter;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class that represents the tag object for tag notification from
 * Havis.Middleware.ALE.Reader.IReaderConnector implementations
 * 
 * This class is used to transfer tag data from a
 * Havis.Middleware.ALE.Reader.IReaderConnector implementation to the
 * middleware. It contains the epc code, the pc and the xpc parameter and a set
 * of <see cref="Havis.Middleware.ALE.Base.Operation.Tag.Result"/>. It also
 * contains datetime values about sightings and readers which has seen the tag.
 * This class overrides GetHashCode and Equals method, so that two tags with
 * equal epc code value are equal.
 */
public class Tag extends Data implements ITimestamps, ICount, ISightings, Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Property {
		TAG_INFO
	}

	public static interface Decoder<T> {
		Object decode(int bank, T data);
	}

	private volatile static boolean extended;

	/**
	 * The raw EPC of this tag.
	 */
	private byte[] code;

	/**
	 * The original raw EPC of this tag.
	 */
	private byte[] originalCode;

	/**
	 * The PC of this tag.
	 */
	private byte[] pc;

	/**
	 * The length of the EPC code on tag.
	 * 
	 * @return The length from PC or zero
	 */
	public int getLength() {
		return (getPc() != null) && (getPc().length > 0) ? (getPc()[0] & 0xFF) >> 3 : 0;
	}

	public void setLength(int length) {
		if ((getPc() != null) && (getPc().length > 0)) {
			getPc()[0] = (byte) ((length << 3) | (0xFF >> 5 & (getPc()[0] & 0xFF)));
		} else {
			setPc(new byte[] { (byte) (length << 3), 0x00 });
		}
	}

	/**
	 * The XPC of this tag.
	 */
	private byte[] xpc;

	/**
	 * The TID of this tag.
	 */
	private byte[] tid;

	/**
	 * The timestamp when this tag was seen the first time.
	 */
	private Date firstTime;

	/**
	 * The timestamp when this tag was seen the last time.
	 */
	private Date lastTime;

	/**
	 * Retrieves the sighting.
	 */
	private Sighting sighting;

	/**
	 * The counter how often this tag was seen.
	 */
	private int count;

	/**
	 * Retrieves a set of sightings per reader for this tag.
	 */
	private Map<String, List<Sighting>> sightings;

	private int timeout;

	/**
	 * A timeout in milliseconds, that specify the timespan in which this tag is
	 * still considered as within the readers field. This timeout is used by the
	 * tag smoothing implementation
	 * 
	 * @return The timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * Sets the timeout
	 * 
	 * @param timeout
	 *            The timeout
	 */
	public void setTimeout(int timeout) {
		if (timeout > this.timeout)
			this.timeout = timeout;
	}

	/**
	 * Retrieves a set of requested operation results for this tag.
	 */
	private Map<Integer, Result> result;

	private int hashCode = 0;

	private int id = System.identityHashCode(this);

	private Map<Property, Object> properties = new HashMap<>();

	private Decoder<byte[]> tagInfoDecoder;

	private Map<Integer, Object> itemData;

	public Tag() {
		itemData = new HashMap<Integer, Object>();
		firstTime = lastTime = new Date();
		sightings = new HashMap<String, List<Sighting>>();
		count = 0;
	}

	/**
	 * Creates a new instance of {@link Tag}, using a byte array to specify the
	 * tag code.
	 * 
	 * @param code
	 *            The epc for the new tag instance.
	 */
	public Tag(byte[] code) {
		this();
		this.code = code;
	}

	/**
	 * Creates a new instance of {@link Tag}, using a byte array to specify the
	 * tag code and initial TID.
	 * 
	 * @param code
	 *            The epc for the new tag instance.
	 * @param tid
	 *            The TID
	 */
	public Tag(byte[] code, byte[] tid) {
		this(code);
		this.tid = tid;
	}

	/**
	 * Creates a new instance of {@link Tag}, using a internal filter array.
	 * 
	 * Internally the first filter value is always the epc in raw hex format,
	 * this value will by used the create the code of this tag object.
	 * 
	 * @param filters
	 *            The filters
	 */
	public Tag(List<Filter> filters) {
		this();
		apply(filters);
	}

	/**
	 * Set the tag info decoder
	 * 
	 * @param decoder
	 *            the decoder to use
	 */
	public void setTagInfoDecoder(Decoder<byte[]> decoder) {
		this.tagInfoDecoder = decoder;
	}

	/**
	 * @param property
	 *            the property to get
	 * @return the property value
	 */
	public <T> T getProperty(Property property) {
		@SuppressWarnings("unchecked")
		T result = (T) this.properties.get(property);
		if (result == null && !this.properties.containsKey(property) && this.tagInfoDecoder != null) {
			switch (property) {
			case TAG_INFO:
				synchronized (this) {
					Object value = null;
					if (this.originalCode == null) {
						value = this.tagInfoDecoder.decode(1, this.code);
					} else {
						value = this.tagInfoDecoder.decode(1, this.originalCode);
					}
					@SuppressWarnings("unchecked")
					T newResult = (T) value;
					result = newResult;
					this.properties.put(property, result);
				}
				break;
			}
		}
		return result;
	}

	/**
	 * Set a property value
	 * 
	 * @param property
	 *            the property to set
	 * @param value
	 *            the value to set
	 */
	public <T> void setProperty(Property property, T value) {
		this.properties.put(property, value);
	}

	/**
	 * Retrieves the uri
	 */
	@Override
	public String getUri() {
		return toString();
	}

	public byte[] getPc() {
		return pc;
	}

	public void setXPC(byte[] xpc) {
		this.xpc = xpc;
	}

	public byte[] getXPC() {
		return xpc;
	}

	public void setPc(byte[] pc) {
		this.pc = pc;
	}

	/**
	 * Check whether item data have been decoded for the specified bank
	 * 
	 * @param bank
	 *            the bank
	 * @return true if the packed objects have been decoded, false otherwise
	 */
	public boolean hasItemData(int bank) {
		return this.itemData.containsKey(Integer.valueOf(bank));
	}

	/**
	 * Gets the item data on the specified bank
	 * 
	 * @param bank
	 *            the bank
	 * @return the item data
	 */
	public <T> T getItemData(int bank) {
		@SuppressWarnings("unchecked")
		T result = (T) this.itemData.get(Integer.valueOf(bank));
		return result;
	}

	public void stat(String reader) {
		stat(reader, null);
	}

	/**
	 * Method to set the statistic data for a tag i.e. Readers, Count, LastSeen
	 * and RSSI
	 * 
	 * @param reader
	 *            The name of the reader that has collected this additional
	 *            statistic data
	 * @param tag
	 *            The tag object that contains the additional statistic data
	 */
	public void stat(String reader, Tag tag) {

		if (tag == null) {
			count = 1;
			if (sighting != null) {
				List<Sighting> list = new ArrayList<Sighting>();
				list.add(sighting);
				sightings.put(reader, list);
			}
		} else {
			count++;
			if (firstTime == null)
				firstTime = tag.firstTime;
			lastTime = tag.lastTime;
			if (tag.sighting != null) {
				List<Sighting> sightings = this.sightings.get(reader);
				if (sightings != null) {
					sightings.add(tag.sighting);
				} else {
					List<Sighting> list = new ArrayList<Sighting>();
					list.add(tag.sighting);
					this.sightings.put(reader, list);
				}
			}
		}
	}

	/**
	 * Method to merge the tag info of this tag with a given tag.
	 * 
	 * @param tag
	 *            The tag to obtain the tag info from.
	 * @return This tag after the merge process is completed.
	 */
	public Tag merge(Tag tag) {
		this.properties = tag.properties;
		if (this.tagInfoDecoder == null) {
			this.tagInfoDecoder = tag.tagInfoDecoder;
		}
		return this;
	}

	/**
	 * Method to clear the tag statistic information
	 */
	public void clear() {
		firstTime = null;
		count = 0;
		sightings = new HashMap<String, List<Sighting>>();
	}

	/**
	 * Gets a set of filter values.
	 * 
	 * @return The filter set
	 */
	public List<Filter> getFilter() {
		Filter filter = new Filter(1, code.length * 8, 32, code.clone());
		if (extended) {
			return Arrays.asList(filter, new Filter(2, tid.length * 8, 0, tid.clone()));
		}
		return Arrays.asList(filter);
	}

	/**
	 * Method to apply a new code on this tag. This method will store the
	 * original code in the property OriginalCode.
	 * 
	 * @param code
	 *            The epc code
	 */
	public void apply(byte[] code) {
		if (originalCode == null) {
			originalCode = this.code;
		}
		this.code = code;
		hashCode = 0;
		properties.remove(Property.TAG_INFO);
	}

	/**
	 * Method to apply a new code based on a set of filter values on this tag.
	 * This method will store the original code in the property OriginalCode.
	 * Internally the first filter value is always the epc in raw hex format,
	 * this value will by used the create the code of this tag object.
	 * 
	 * @param filters
	 *            The set of filter values.
	 */
	public void apply(List<Filter> filters) {
		if (originalCode == null) {
			originalCode = code;
		}
		code = filters.get(0).getMask();
		if (extended) {
			try {
				tid = filters.get(filters.size() - 1).getMask();
			} catch (Exception e) {
			}
		}
		hashCode = 0;
	}

	/**
	 * Method to apply a new code based on a reader write operation. This method
	 * will store the original code in the property OriginalCode.
	 * 
	 * @param operation
	 *            The reader write operation.
	 * @return True, if operation is write operation, false otherwise
	 */
	public boolean apply(Operation operation) {
		@SuppressWarnings("unused")
		int length;
		byte[] data;
		Field field;
		if ((operation.type == OperationType.WRITE) && ((field = operation.field).getBank() == 1) && ((data = operation.data) != null) && (data.length > 0)
				&& ((length = (field.getOffset() + (field.getLength() == 0 ? data.length * 8 : field.getLength()))) > 32)) {
			if (originalCode == null) {
				originalCode = code.clone();
			}
			byte[] bytes = code;
			if (field.getOffset() < 32) {
				code = Converter.set(bytes, Calculator.strip(operation.getData(), 32 - field.getOffset(), 0), 0, (field.getLength() > 0 ? field.getLength()
						: operation.data.length * 8) - 32 + field.getOffset());
			} else {
				code = Converter.set(bytes, operation.data, field.getOffset() - 32, (field.getLength() > 0 ? field.getLength() : operation.data.length * 8));
			}
			hashCode = 0;
			properties.remove(Property.TAG_INFO);
			return true;
		} else {
			return false;
		}
	}

	public void apply(Tag tag) {
		apply(tag, false);
	}

	/**
	 * Method to apply the value of each bank from a given tag.
	 * 
	 * @param tag
	 *            The tag
	 * @param force
	 *            True, to force apply, false only if result is better i.e.
	 *            state of one existing result is lesser then success
	 */
	public void apply(Tag tag, boolean force) {
		if (force) {
			if (originalCode == null) {
				originalCode = code;
			}
			code = tag.code;
			pc = tag.pc;
			xpc = tag.xpc;
			tid = tag.tid;
			hashCode = 0;
			result = tag.result;
			properties.remove(Property.TAG_INFO);
		} else {
			if (tag.result != null) {
				if (result == null) {
					apply(tag, true);
				} else {
					for (Entry<Integer, Result> pair : tag.result.entrySet()) {
						Result currentResult = result.get(pair.getKey());
						if (currentResult != null) {
							if (pair.getValue().getState() == ResultState.SUCCESS) {
								if (currentResult.getState() != ResultState.SUCCESS) {
									apply(tag, true);
									break;
								}
							} else if (currentResult.getState() == ResultState.SUCCESS) {
								break;
							}
						} else {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Decode and set the item data
	 * 
	 * @param bank
	 *            the bank
	 * @param data
	 *            the data to decode
	 */
	public void decodeItemData(int bank, byte[] data, Decoder<byte[]> decoder) {
		if (data != null) {
			try {
				switch (bank) {
				case 1: // epc bank
					if (data.length >= 5) {
						// we need the AFI + data (starting at 18h)
						byte[] dataToDecode = new byte[data.length - 3];
						System.arraycopy(data, 3, dataToDecode, 0, dataToDecode.length);
						this.itemData.put(Integer.valueOf(bank), decoder.decode(bank, dataToDecode));
					}
					break;
				case 3: // user bank
					this.itemData.put(Integer.valueOf(bank), decoder.decode(bank, data));
					break;
				default:
					break;
				}
			} catch (Exception e) {
				// failed to decode
			}
		}
	}

	/**
	 * Reset the item data for the specified bank
	 * 
	 * @param bank
	 *            the bank
	 */
	public void resetItemData(int bank) {
		this.itemData.remove(Integer.valueOf(bank));
	}

	/**
	 * Returns if a tag is equal to another. This method first compares the
	 * references and at least the hash codes.
	 * 
	 * @param obj
	 *            The other tag object
	 * @return Returns true if equals, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		boolean b = (obj == this) || (obj.hashCode() == hashCode()) && (obj instanceof Tag) && compare(code, ((Tag) obj).code)
				&& (!extended || compare(tid, ((Tag) obj).tid));
		return b;
	}

	static boolean compare(byte[] a, byte[] b) {
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		if (a.length != b.length)
			return false;
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i])
				return false;
		}
		return true;
	}

	/**
	 * Returns the hash code of a tag based on the epc code
	 * 
	 * @return The hash code
	 */
	@Override
	public int hashCode() {
		if (hashCode == 0) {
			hashCode = Comparison.hashCode((extended && (tid != null)) ? Calculator.concat(code, tid) : code);
		}
		return hashCode;
	}

	/**
	 * @return The internal ID for a tag
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the string representation
	 * 
	 * @return The string representation
	 */
	@Override
	public String toString() {
		Object info = getProperty(Property.TAG_INFO);
		if (info != null) {
			return info.toString();
		}
		return null;
	}

	@Override
	public Map<String, List<Sighting>> getSightings() {
		return sightings;
	}

	@Override
	public void setSightings(Map<String, List<Sighting>> sightings) {
		this.sightings = sightings;
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void setCount(int count) {
		this.count = count;

	}

	@Override
	public Date getFirstTime() {
		return firstTime;
	}

	@Override
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	@Override
	public Date getLastTime() {
		return lastTime;
	}

	@Override
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public byte[] getEpc() {
		return code;
	}

	/**
	 * Sets the extended state
	 */
	public static void setExtended(boolean extended) {
		Tag.extended = extended;
	}

	/**
	 * Gets the extended state
	 * 
	 * @return True if tag used the EPC and TID bank for uniqueness, false if
	 *         only EPC is used.
	 */
	public static boolean isExtended() {
		return extended;
	}

	public byte[] getTid() {
		return tid;
	}

	public void setTid(byte[] tid) {
		this.tid = tid;
	}

	public Map<Integer, Result> getResult() {
		return result;
	}

	public void setResult(Map<Integer, Result> result) {
		this.result = result;
	}

	public Sighting getSighting() {
		return sighting;
	}

	public void setSighting(Sighting sighting) {
		this.sighting = sighting;
	}

	public havis.middleware.ale.base.Tag tag() {
		havis.middleware.ale.base.Tag tag = new havis.middleware.ale.base.Tag();
		tag.setEpc(getEpc());
		tag.setTid(getTid());
		tag.setUri(getUri());
		tag.setFirstSeen(firstTime);
		tag.setLastSeen(lastTime);
		tag.setCount(getCount());
		return tag;
	}

	@Override
	public Tag clone() {
		Tag tag = null;
		try {
			tag = (Tag) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new UnsupportedOperationException("Clone not supported");
		}

		// deep clone item data
		if (this.itemData != null) {
			tag.itemData = new HashMap<>();
			Method cloneMethod = null;
			for (Entry<Integer, Object> entry : this.itemData.entrySet()) {
				Object value = entry.getValue();
				Object clone;
				try {
					if (cloneMethod == null) {
						cloneMethod = value.getClass().getDeclaredMethod("clone");
					}
					clone = cloneMethod.invoke(value);
				} catch (Exception e) {
					throw new UnsupportedOperationException(e);
				}
				tag.itemData.put(entry.getKey(), clone);
			}
		}

		// only change references of cloned fields

		if (tag.getSighting() != null) {
			tag.setSighting(new Sighting(tag.getSighting().getHost(), tag.getSighting().getAntenna(), tag.getSighting().getStrength(), tag.getSighting().getTimestamp()));
		}

		if (tag.getSightings() != null) {
			Map<String, List<Sighting>> sightings = new HashMap<>();
			for (Map.Entry<String, List<Sighting>> entry : tag.getSightings().entrySet()) {
				List<Sighting> list = new ArrayList<Sighting>();
				for (Sighting sighting : entry.getValue()) {
					list.add(new Sighting(sighting.getHost(), sighting.getAntenna(), sighting.getStrength(), sighting.getTimestamp()));
				}
				sightings.put(entry.getKey(), list);
			}
			tag.setSightings(sightings);
		}

		if (tag.getResult() != null) {
			tag.setResult(new HashMap<Integer, Result>(tag.getResult()));
		}

		tag.properties.remove(Property.TAG_INFO);
		tag.tagInfoDecoder = this.tagInfoDecoder;
		tag.hashCode = 0;

		return tag;
	}
}
