// Import packages
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;

/* 
 * class Heap using key value pairs, implementing methods
 * found in PriorityQueue.java. Has additional helper
 * methods to implement heap. Can be used for both max
 * and min heaps.
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang 
 */

public class Heap<K, V> implements PriorityQueue<K, V>{
	
	public List<Entry<K, V>> entries;
	public Comparator<K> comparator;

	/* 
	 * Constructs initializes instance variables.
	 */
	
	public Heap(Comparator<K> comparator) {
		this.entries = new ArrayList<Entry<K, V>>();
		this.comparator = comparator;
	}
	/* Insert a new entry with the given key and value to the end of the heap.
	 * Then, bubbleUp so that the heap properties are not violated
	 */
	public void add(K k, V v) {
		 this.entries.add(new Entry<K, V>(k, v));
		 bubbleUp(this.entries.size()-1);
	}
	
	/* Remove and return the root element in the heap.
	 * Set the last entry in the heap to the root.
	 * Use bubbleDown to fix the heap after the removal.
	 * If the size is zero, throw NoSuchElementException()
	 */
    public Entry<K, V> poll(){
    	if (size() == 0) { throw new NoSuchElementException(); }
    	Entry<K, V> root = peek();
    	swap(0, size() - 1);
    	this.entries.remove(size() - 1);
    	bubbleDown(0);
		return root;
    }

	/* Return the root element of the heap.
	 * If the size is zero, throw NoSuchElementException()
	 */
	public Entry<K,V> peek() {
		// Method to return the max element in the heap
		return this.entries.get(0);
	}

	/* Return the list of entries.
	 */
    public List<Entry<K,V>> toArray(){
    	return this.entries;
    }
    
    /* If the List of entries is empty, return true.
     * Otherwise, return false.
     */
    public boolean isEmpty() {
		return this.entries.size() == 0;
	}
	
	/* Return the parent index.
	 */
	public int parent(int index) {
		return (int) ((index-1)/2);
	}
	
	/* Return the left child index.
	 */
	public int left(int index) {
    	return (2*index)+1;
    }
	
    /* Return the right child index.
     */
    public int right(int index) {
    	return (2*index)+2;
    }
    
    /* Takes the index of two entries and swaps them.
     */
    public void swap(int i1, int i2) {
    	Entry<K, V> temp = this.entries.get(i1);
    	this.entries.set(i1, this.entries.get(i2));
    	this.entries.set(i2, temp);
    }

	// From Pre-Lecture 20.
    /* A recursive method that moves the entry at the specified index to a
     * smaller index (up the tree) while maintaining the heap structure. In
     * the case where the element is equal to the parent, you should not swap.
     */
    public void bubbleUp(int index) {
    	if (index <= 0) { return; }
    	K e = this.entries.get(index).key;
    	K parent = this.entries.get(parent(index)).key;
    	int comp = this.comparator.compare(e, parent);
    	if (comp > 0) {
    		swap(index, parent(index));
    		bubbleUp(parent(index));
    	} else { return; }
    }

    // From Pre-Lecture 20.
    /* A recursive method that moves the entry at the specified index to a
     * larger index (down the tree) while maintaining the heap structure. Swap
     * with the child with higher priority. If both children are equal and
     * swapping is needed, swap with the left child. In the case where the
     * element is equal to the smaller child, you should not swap. However,
     * if the child with high priority has greater priority than the parent,
     * you still must swap.
     */
    public void bubbleDown(int index) {
    	if (index >= size()) { return; }
    	int leftIndex = left(index);
    	if (leftIndex >= size()) { return; }
    	int largerChildIndex = leftIndex;
    	int rightIndex = right(index);
    	if (existsAndGreater(rightIndex, leftIndex)) {
    		largerChildIndex = rightIndex;
    	}
    	if (existsAndGreater(largerChildIndex, index)) {
    		swap(index, largerChildIndex);
    		bubbleDown(largerChildIndex);
    	}
    }

    /* Returns true if the entry at index1 is greater than that at index2
     * (Note: Both entries at the specified indices must exists for this to
     * be true). Return false otherwise.
     */
	public boolean existsAndGreater(int i1, int i2) {
		// Checks if both indexes are in bounds and exist
    	if (i1 >= size() || i2 >= size()) { return false; }
    	if (this.entries.get(i1).key == null || this.entries.get(i2).key == null) { return false; }
    	return this.comparator.compare(	this.entries.get(i2).key,
    									this.entries.get(i1).key) < 0;
    }
	
    /* Returns the number of elements in entries.
     */
    public int size() {
    	return this.entries.size();
    }
    
    /* Returns a string representation of the elements in entries
     * (this method is helpful for debugging)
     */
    public String toString() {
    	return this.entries.toString();
    }
}

class Entry<K, V> {
    K key; // aka the _priority_
    V value;
    public Entry(K k, V v) { this.key = k; this.value = v; }
    public String toString() {
        return key + ": " + value;
    }
}