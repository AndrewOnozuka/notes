import java.util.Comparator;
import java.util.List;

/**
 * 
 *
 * @param <K> The type of the keys in this DefaultMap
 * @param <V> The type of the values in this DefaultMap
 */
public interface DefaultMap<K, V> {
	public static final Comparator myComparator = null;
	/**
	 * Internal Representation of every (key, value) pair in this DefaultMap
	 */
	interface Entry<K, V> {
		K getKey();
		V getValue();
		
		void setValue(V value);
	}

	/**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean put(K key, V value) throws IllegalArgumentException;
	
	
	/**
	 * @return the value corresponding to the specified key, null if key doesn't 
	 * exist in hash map
	 * @throws IllegalArgument exception if the key is null
	 */
	V get(K key) throws IllegalArgumentException;
	
	/**
	 * 
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
	int size();
	
	/**
	 * 
	 * @return true iff this.size() == 0 is true
	 */
	boolean isEmpty();
	
	
	/**
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgument exception if the key is null
	 */
	boolean containsKey(K key) throws IllegalArgumentException;

}
