// Import necessary packages
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Class for the MyHashMap object.
 * Used for a hash map with key value pairs,
 * implements DefaultMap.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */

public class MyHashMap<K, V> implements DefaultMap<K, V> {
	public static final double DEFAULT_LOAD_FACTOR = 0.75;
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	public static final String ILLEGAL_ARG_CAPACITY = "Initial Capacity must be non-negative";
	public static final String ILLEGAL_ARG_LOAD_FACTOR = "Load Factor must be positive";
	public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";
	
	private double loadFactor;
	private int capacity;
	private int size;

	// Use this instance variable for Separate Chaining conflict resolution
	private List<HashMapEntry<K, V>>[] buckets;  
	
	// Use this instance variable for Linear Probing
	private HashMapEntry<K, V>[] entries; 	

	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
	}
	
	/**
	 * 
	 * @param initialCapacity the initial capacity of this MyHashMap
	 * @param loadFactor the load factor for rehashing this MyHashMap
	 * @throws IllegalArgumentException if initialCapacity is negative or loadFactor not
	 * positive
	 */
	@SuppressWarnings("unchecked")
	public MyHashMap(int initialCapacity, double loadFactor) throws IllegalArgumentException {

		icCheck(initialCapacity);
		lfCheck(loadFactor);
		
		this.loadFactor = loadFactor;
		this.capacity = initialCapacity;
		this.size = 0;
		
		// if you use Separate Chaining
		buckets = (List<HashMapEntry<K, V>>[]) new List<?>[capacity];

		// if you use Linear Probing
		entries = (HashMapEntry<K, V>[]) new HashMapEntry<?, ?>[initialCapacity];
	}

	/** 
	 * put method checks to see if a key already exists,
	 * adds if it does not already currently exist.
	 * Returns true if successfully added, false otherwise.
	 * 
	 * @param key
	 * @param value
	 */
	
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		// Checks to see if key already exists.
		if (containsKey(key)) { return false; }
		// Checks if there is space, if not expands capacity.
		if (size/this.capacity > loadFactor) { expandCapacity(); }
		int keyHash = Objects.hashCode(key);
		int idx = Math.abs(keyHash % this.buckets.length);
		// Adds the key value pair.
		if (this.buckets[idx] == null) {
			this.buckets[idx] = new LinkedList<HashMapEntry<K, V>>();
			this.buckets[idx].add(new HashMapEntry<K, V>(key, value));
		} else {
			this.buckets[idx].add(new HashMapEntry<K, V>(key, value));
		}
		size++;
		return true;
	}

	/** 
	 * expandCapacity method to increase the size of the list
	 * when space runs out.
	 */
	
	@SuppressWarnings("unchecked")
	private void expandCapacity() {
		List<HashMapEntry<K, V>>[] store = this.buckets;
		this.capacity *= 2;
		this.buckets = (List<HashMapEntry<K,V>>[]) new List<?>[this.capacity];
		this.size = 0;
		// Copies the old buckets into new list.
		for (int i = 0; i < store.length; i++) {
			if (store[i] != null) {
				for (Entry e: store[i]) {
					set((K) e.getKey(), (V) e.getValue());
				}
			}
		}
	}

	/** 
	 * replace method updates the value in a key value pair.
	 * returns true if replaces successfully, false otherwise. 
	 * 
	 * @param key
	 * @param newValue
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		// Checks to make sure key already exists to replace.
		if (!containsKey(key)) { return false; }
		int keyHash = Objects.hashCode(key);
		int idx = Math.abs(keyHash % this.buckets.length);
		// Loops to find where the old value is using the key
		for (HashMapEntry<K, V> e : this.buckets[idx]) {
			if (e.getKey().equals(key)) {
				e.setValue(newValue);
				return true;
			}
		}
		return false;
	}

	/** 
	 * remove method removes a key value pair using a given key.
	 * Returns true if successful, false otherwise.
	 * 
	 * @param key
	 */
	
	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		// Checks to make sure key exists to remove.
		if (!containsKey(key)) { return false; }
		else {
			int keyHash = Objects.hashCode(key);
			int idx = Math.abs(keyHash % this.buckets.length);
			// Loops through to find the key value pair using key.
			for (int i = 0; i < this.buckets[idx].size(); i++) {
				if (this.buckets[idx].get(i).getKey().equals(key)) {
					this.buckets[idx].remove(i);
					this.size--;
					return true;
				}
			}
		} return false;
	}

	/**
	 * set method calls replace(k, v) if key already exists,
	 * put(k, v) if key does not already exist. 
	 * 
	 * @param key
	 * @param value
	 */
	
	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		// replace() if key already exists
		if (containsKey(key)) { replace(key, value); }
		// set() if key DNE yet
		else { put(key, value); }
	}

	/** 
	 * get method finds the value at the given key.
	 * Returns true if successful, false otherwise.
	 * 
	 * @param key
	 */
	
	@Override
	public V get(K key) throws IllegalArgumentException {
		// Checks to make sure key exists.
		if (!containsKey(key)) { return null; }
		int keyHash = Objects.hashCode(key);
		int idx = Math.abs(keyHash % this.buckets.length);
		// Loops through to get value for given key.
		for (HashMapEntry<K, V> e : this.buckets[idx]) {
			if (e.getKey().equals(key)) {
				return (V) e.getValue();
			}
		} return null;
	}

	// method to return the size.
	@Override
	public int size() {
		return this.size;
	}

	// method to check if empty.
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	// Additional Methods
	
	// checks IllegalArgumentException for initialCapacity
	public void icCheck(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
		}
	}
	
	// checks IllegalArgumentException for loadFactor
	public void lfCheck(double loadFactor) {
		if (loadFactor <= 0) {
			throw new IllegalArgumentException(ILLEGAL_ARG_LOAD_FACTOR);
		}
	}
	
	// checks IllegalArgumentException for key
	public void nullCheck(K key) {
		if (key == null) {
            throw new IllegalArgumentException(ILLEGAL_ARG_NULL_KEY);
        }
	}
	
	// returns the hash code index using the key and capacity.
	public int getIdx(K key) {
		return (key.hashCode()) % capacity;
	}
	
//	public List<HashMapEntry<K, V>> getBucket(K key) {
//		int idx = getIdx(key);
//		List<MyHashMap.HashMapEntry<K, V>> list = buckets[idx];
//		if (list == null) {
//			ArrayList<MyHashMap.HashMapEntry<K, V>> newList = new ArrayList<HashMapEntry<K, V>>();
//			buckets[idx] = newList;
//			return newList;
//		} return list;
//	}
	
	/** 
	 * method to check if key exists.
	 * Returns true if key is found, false otherwise.
	 * 
	 * @param key
	 */
	
	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		// Checks if key is null.
		nullCheck(key);
		int keyHash = Objects.hashCode(key);
		int idx = Math.abs(keyHash % this.buckets.length);
		if (this.buckets[idx] == null) { return false; }
		// Loops through to check if key exists.
		for (HashMapEntry<K, V> e : this.buckets[idx]) {
			if (e.getKey().equals(key)) {
				return true;
			}
		} return false;
	}

	/* 
	 * method to return a list of the keys.
	 */
	
	@Override
	public List<K> keys() {
		List<K> keys = new ArrayList<K>();
		// Loops through and adds the keys to a list.
		for (int i = 0; i < this.buckets.length; i++) {
			if (this.buckets[i]!= null) {
				for (int j = 0; j < this.buckets[i].size(); j++) {
					keys.add(this.buckets[i].get(j).getKey());
				}
			}
		} return keys;
	}
	
	private static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		
		private HashMapEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
		
		@Override
		public void setValue(V value) {
			this.value = value;
		}
	}
}
