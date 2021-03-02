package havis.middleware.ale.base.operation.tag;

/**
 * Enumeration that represents the given tag operation types which are used to
 * describe a specific operation through the {@link Operation} class.
 */
public enum OperationType {

	/**
	 * Describe a read operation to read from a tag.
	 */
	READ,
	/**
	 * Describe a write operation to write on a tag.
	 */
	WRITE,
	/**
	 * Describe a kill operation to kill a tag.
	 */
	KILL,
	/**
	 * Describe a lock operation to lock parts of the tag.
	 */
	LOCK,
	/**
	 * Describe a password operation to gain access to locked tag parts.
	 */
	PASSWORD,
	/**
	 * Describe a custom operation.
	 */
	CUSTOM
}
