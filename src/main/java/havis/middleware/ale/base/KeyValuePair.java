package havis.middleware.ale.base;

import java.util.Map;
import java.util.Objects;

/**
 * Simple key value pair
 *
 * @param <K> type of the keys
 * @param <V> type of the values
 */
public class KeyValuePair<K, V> implements Map.Entry<K, V> {

    private K key;
    private V value;

    /**
     * Creates an empty key value pair
     */
    public KeyValuePair() {
        super();
    }

    /**
     * Creates a key value pair with the specified key and value
     * @param key the key
     * @param value the value
     */
    public KeyValuePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * @return the key
     */
    @Override
    public K getKey()
    {
        return this.key;
    }

    /**
     * @return the value
     */
    @Override
    public V getValue()
    {
        return this.value;
    }

    /**
     * @param key the key
     * @return the set key
     */
    public K setKey(K key)
    {
        return this.key = key;
    }

    /**
     * @param value the value
     * @return the set value
     */
    @Override
    public V setValue(V value)
    {
        return this.value = value;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Map.Entry))
            return false;
        @SuppressWarnings("unchecked")
        Map.Entry<K, V> e = (Map.Entry<K, V>) o;
        Object k1 = getKey();
        Object k2 = e.getKey();
        if (k1 == k2 || (k1 != null && k1.equals(k2))) {
            Object v1 = getValue();
            Object v2 = e.getValue();
            if (v1 == v2 || (v1 != null && v1.equals(v2)))
                return true;
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    @Override
    public final String toString() {
        return getKey() + "=" + getValue();
    }
}