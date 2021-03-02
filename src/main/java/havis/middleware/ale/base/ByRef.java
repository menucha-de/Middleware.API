package havis.middleware.ale.base;

/**
 * Wrapper to pass arguments by reference (modify the reference in the method)
 *
 * @param <T> the type of the argument
 */
public class ByRef<T> {

    private T value;

    /**
     * @param value the value
     */
    public ByRef(T value) {
        this.value = value;
    }

    /**
     * @return the value
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Set the value
     *
     * @param value the value
     */
    public void setValue(T value) {
        this.value = value;
    }
}
