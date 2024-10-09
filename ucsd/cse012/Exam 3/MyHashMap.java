import java.util.List;
import java.util.Objects;

import java.util.ArrayList;
import java.util.Comparator;

public class MyHashMap<K, V> implements DefaultMap<K, V> {

    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    public static final String ILLEGAL_ARG_CAPACITY = 
                                        "Initial Capacity must be non-negative";
    public static final String ILLEGAL_ARG_NULL_KEY = "Keys must be non-null";

    private double loadFactor;
    private int capacity;
    private int size;	
    private Comparator myComparator;
    private Character[] sections;

    // Use this instance variable for Separate Chaining conflict resolution
    //private List<HashMapEntry<K, V>>[] buckets;  
    private List<MaxHeap<K, V>> buckets;

    // Use this instance variable for Linear Probing
    //	private HashMapEntry<K, V>[] entries; 

    public MyHashMap() {
        this(DEFAULT_INITIAL_CAPACITY, null);}

    @SuppressWarnings("unchecked")
        public MyHashMap(int initialCapacity, Comparator myComparator)
            throws IllegalArgumentException {

            //constructor for the hashMap
    	if (initialCapacity < 0) {
    		throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
    	}
    	this.capacity = initialCapacity;
    	this.size = 0;
    	this.myComparator = myComparator;
    	this.buckets = new ArrayList<MaxHeap<K, V>>();
    	for (int i = 0; i < this.capacity; i++) {
    		this.buckets.add(null);
    	}
    } 		
            
        
    // Runtime: O(log n)
    @Override
        public boolean put(K key, V value) throws IllegalArgumentException {

           //Method to add the key value pair to the hashMap
		if (containsValue(key, value) == false) {
			return false;
		}
		if (this.buckets.get(bucketIdx(key)) == null) {
			this.buckets.set(bucketIdx(key), new MaxHeap<>(10, myComparator));
		}
		this.buckets.get(bucketIdx(key)).add(key, value);
		this.size++;
		return true;
        }

    	public int bucketIdx(K key) {
    		return (key.hashCode()) % capacity;
    	}

    // Runtime: O(1)
    @Override
        public V get(K key) throws IllegalArgumentException {

            //Method to get the value of given key
    		if (!containsKey(key)) {
    			return null;
    		}
    		return this.buckets.get(bucketIdx(key)).peek().getValue();
        }

    @Override
        public boolean containsKey(K key) throws IllegalArgumentException {
            //Method to check if key is present
    		if (key == null) {
    			throw new IllegalArgumentException(ILLEGAL_ARG_CAPACITY);
    		}
    		if (this.buckets.get(bucketIdx(key)) == null) {
    			return false;
    		}
    		for (int i = 0; i < this.buckets.get(bucketIdx(key)).entries.size(); i++) {
    			if (this.buckets.get(bucketIdx(key)).entries.get(i).getKey().equals(key)) {
    				return true;
    			}
    		}
    		return false;
        }
    
    	public boolean containsValue(K key, V value) {
    		if (containsKey(key)) {
    			for (int i = 0; i < this.buckets.get(bucketIdx(key)).entries.size(); i++) {
        			if (this.buckets.get(bucketIdx(key)).entries.get(i).getValue().equals(value)) {
        				return false;
        			}
        		}
    		}
    		return true;
    	}

    @Override
        public int size() {
            //Method to get size of the hashMap\
    		return this.size;
        }

    @Override
        public boolean isEmpty() {
            //Method to check if hashMap is empty
    		return this.size == 0;
        }

    protected static class HashMapEntry<K, V> implements DefaultMap.Entry<K, V> {

        K key;
        V value;

        public HashMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
            public K getKey() {
                return this.key;
            }

        @Override
            public V getValue() {
                return this.value;
            }

        @Override
            public void setValue(V value) {
                this.value = value;
            }

        @Override
            @SuppressWarnings("unchecked")
            public boolean equals(Object o) {
                if (o instanceof MyHashMap.HashMapEntry<?, ?>) {
                    HashMapEntry<K, V> other = null;
                    try {
                        other = (HashMapEntry<K, V>) o;
                    } catch (ClassCastException e) {
                        return false;
                    }

                    return Objects.equals(key, other.key);
                }


                return false;
            }

    }
}