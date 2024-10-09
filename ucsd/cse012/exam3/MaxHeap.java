
import java.util.*;

public class MaxHeap<K, V> {

List<HeapEntry<K,V>> entries;
int capacity;
int heapSize = 0;
Comparator comparator;

    public MaxHeap(int capacity, Comparator comparator){
        // Constructor for the max heap
       this.entries = new ArrayList<>(capacity);
       this.capacity = capacity;
       this.heapSize = 0;
       this.comparator = comparator;
    }

    /*	Insert a new entry with the given key and value to the end of the heap.
     *	Then, bubbleUp so that the heap properties are not violated */
	public void add(K key, V value) {
        // Method to add the key value pair in the heap, remember to satisfy max heap Property
		if (this.heapSize > this.capacity) { return; }
		this.entries.add(new HeapEntry<K, V>(key, value));
        bubbleUp(this.heapSize);
        this.heapSize++;
    }

	/* Return the root element of the heap.
	 * If the size is zero, throw NoSuchElementException()
	 */
	public HeapEntry<K,V> peek() {
		// Method to return the max element in the heap
		if (this.entries.size() == 0) {
			throw new NoSuchElementException();
		} return this.entries.get(0);
	}
	
	public HeapEntry<K,V> remove() {
		//Method to remove the max element in the heap, remember to satisfy max heap Property
		if (this.entries.size() == 0) {
			return null;
		} 
		this.heapSize--;
		return deleteIdx(0);
	}
	
	// Additional Methods
	
	/* Deletes at given index and returns what is deleted.
	 */
	public HeapEntry<K, V> deleteIdx(int index) {
		swap(index, this.entries.size()-1);
		HeapEntry<K, V> delete = this.entries.get(this.entries.size()-1);
		this.entries.remove(this.entries.size()-1);
		bubbleDown(index);
		return delete;
	}
	
//	/* Return the list of entries.
//	 */
//    List<HeapEntry<K,V>> toArray(){
//    	
//    }

    /* If the List of entries is empty, return true.
     * Otherwise, return false.
     */
    public boolean isEmpty() {
		return this.entries.isEmpty();
	}
	
    /* Return the parent index.
	 */
	public int parent(int index) {
		return (index-1)/2;
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
    	HeapEntry<K, V> i1Idx = this.entries.get(i1);
    	this.entries.set(i1, this.entries.get(i2));
    	this.entries.set(i2, i1Idx);
    }
	
	// Adapted from PA8, originally from Pre-Lecture 20.
    /* A recursive method that moves the entry at the specified index to a
     * smaller index (up the tree) while maintaining the heap structure. In
     * the case where the element is equal to the parent, you should not swap.
     */
	public void bubbleUp(int index) {
    	if (index <= 0) { return; }
    	HeapEntry<K, V> e = this.entries.get(index);
    	HeapEntry<K, V> parent = this.entries.get(parent(index));
    	int comp = this.comparator.compare(e.getValue(), parent.getValue());
    	if (comp > 0) {
    		swap(index, parent(index));
    		bubbleUp(parent(index));
    	} else { return; }
    }
	
	// Adapted from PA8, originally from Pre-Lecture 20.
	/* A recursive method that moves the entry at the specified index to a
     * larger index (down the tree) while maintaining the heap structure. Swap
     * with the child with higher priority. If both children are equal and
     * swapping is needed, swap with the left child. In the case where the
     * element is equal to the smaller child, you should not swap. However,
     * if the child with high priority has greater priority than the parent,
     * you still must swap.
     */
	public void bubbleDown(int index) {
    	if (index >= this.entries.size()) { return; }
    	int leftIndex = left(index);
    	if (leftIndex >= this.entries.size()) { return; }
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
	
	// Adapted from method in PA8.
	/* Returns true if the entry at index1 is greater than that at index2
     * (Note: Both entries at the specified indices must exists for this to
     * be true). Return false otherwise.
     */
	public boolean existsAndGreater(int index1, int index2) {
		// Checks if both indexes are in bounds and exist
    	if (index1 < this.entries.size() && index2 < this.entries.size() &&
		this.entries.get(index1) != null && this.entries.get(index2) != null) {
			V e1 = this.entries.get(index1).value;
    		V e2 = this.entries.get(index2).value;
    		int comp = this.comparator.compare(e1, e2);
    		if (comp > 0) { return true; }	
		} return false;
    }
	
	/* Returns the number of elements in entries.
     */
    public int size() {
    	 return heapSize;
    }
//    
//    /* Returns a string representation of the elements in entries
//     * (this method is helpful for debugging)
//     */
//    public String toString() {
//    	
//    }
//    
//	public V get(int index, K key) {
//		if (index >= this.entries.size()) { return null; }
//		HeapEntry<K, V> entry = entries.get(index);
//		if (entry.key.equals(key)) { return entry.value; }
//		V leftResult = get(left(index), key);
//		V rightResult = get(right(index), key);
//		if (leftResult != null) { return leftResult; }
//		if (rightResult != null) { return rightResult; }
//		return null;
//	}
}

class HeapEntry<K, V> implements DefaultMap.Entry<K,V>{
	K key;
	V value;

	HeapEntry(K key, V value){
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}
	
	public V getValue() {
		return value;
	}

	public void setValue(V value){
		this.value = value;
	}
}