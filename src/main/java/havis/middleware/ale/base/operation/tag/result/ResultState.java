package havis.middleware.ale.base.operation.tag.result;

/**
 * This enumeration provides the reader operation result states Enumeration that
 * represents the given results states which are used to describe a specific tag
 * result through the {@link Result} class.
 */
public enum ResultState {

	/**
	 * Operation completed successfully
	 */
	SUCCESS,

	/**
	 * An error ocurred during operation. The tag is unchanged
	 */
	MISC_ERROR_TOTAL,

	/**
	 * An error ocurred during operation. The tag is partial changed
	 */
	MISC_ERROR_PARTIAL,

	/**
	 * Tag refused the access
	 */
	PERMISSION_ERROR,

	/**
	 * The supplied password is incorrect
	 */
	PASSWORD_ERROR,

	/**
	 * Field was not found
	 */
	FIELD_NOT_FOUND_ERROR,

	/**
	 * Operation is not possible because of tag limitation
	 */
	OP_NOT_POSSIBLE_ERROR,

	/**
	 * Value could not be encoded using the available number of bits
	 */
	OUT_OF_RANGE_ERROR,

	/**
	 * Field already exists in memory
	 */
	FIELD_EXISTS_ERROR,

	/**
	 * Changes overflows memory
	 */
	MEMORY_OVERFLOW_ERROR,

	/**
	 * The check operation failed
	 */
	MEMORY_CHECK_ERROR,

	/**
	 * The value retrieved from association table was invalid
	 */
	ASSOCIATION_TABLE_VALUE_INVALID,

	/**
	 * The association table did not contain a value for this tag
	 */
	ASSOCIATION_TABLE_VALUE_MISSING,

	/**
	 * The epc cache was empty
	 */
	EPC_CACHE_DEPLETED
}
