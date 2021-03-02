package havis.middleware.ale.base.operation.tag;

/**
 * Enumeration that represents the given lock operation types which are used to
 * describe a specific look operation through the {@link Operation} class.
 */
public enum LockType {
    /**
     * Unlock the field
     */
    UNLOCK,

    /**
     * Unlock the field permanently
     */
    PERMAUNLOCK,

    /**
     * Lock the field
     */
    LOCK,

    /**
     * Lock the field permanently
     */
    PERMALOCK
}