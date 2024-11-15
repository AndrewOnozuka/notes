import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Class for BST object.
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 * 
 * @author Andrew Onozuka
 * received help from Benjamin Liang
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	/* 
	 * TODO: Add instance variables 
	 * You may add any instance variables you need, but 
	 * you may NOT use any class that implements java.util.SortedMap
	 * or any other implementation of a binary search tree
	 */
	public Node<K, V> root;
	public int size;
	public Comparator<K> comparator;
	
	/* 
     * Constructor that creates a new BST object and
     * initializes its instance variables.
     */
	
	public BST() {
		this.root = null;
		this.size = 0;
	}

	/** 
     * Constructor that creates a new BST object with
     * the given Node root.
     * 
     * @param root
     */
	
	public BST(Node<K, V> root) {
		this.root = root;
		this.size = 1;
	}
	
	/** 
	 * put method checks to see if a key already exists,
	 * puts if it does not already currently exist.
	 * Returns true if successfully added, false otherwise.
	 * 
	 * @param key
	 * @param value
	 */
	
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if (containsKey(key)) { return false; }
		this.root = put(this.root, key, value);
		return true;
	}

	/** 
	 * put method checks to see if a node is currently null,
	 * puts in the node corresponding to location.
	 * Returns new node.
	 * 
	 * @param node
	 * @param key
	 * @param value
	 */
	
	public Node<K, V> put(Node<K, V> node, K key, V value) {
		if (node == null) {
			this.size++;
			return new Node<K, V>(key, value, null, null);
		}
		if (node.key.compareTo(key) < 0) {
			node.right = put(node.right, key, value);
			return node;
		} else if (node.key.compareTo(key) > 0) {
			node.left = put(node.left, key, value);
			return node;
		} else { return node; }
	}
	
	/** 
	 * replace method checks if a value can be replaced.
	 * returns false if does not contain key, returns
	 * other replace method otherwise. 
	 * 
	 * @param key
	 * @param newValue
	 */
	
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		if (!containsKey(key)) { return false; }
		return replace(this.root, key, newValue);
	}
	
	/** 
	 * replace method checks if value can be replaced.
	 * returns true if successfully replaced, returns
	 * other false otherwise. 
	 * 
	 * @param node
	 * @param key
	 * @param newValue
	 */
	
	public boolean replace(Node<K, V> node, K key, V value) {
		if (node == null) { return false; }
		else if (node.key.equals(key)) {
			node.setValue(value);
			return true;
		} return replace(node.left, key, value) || replace(node.right, key, value);
	}

	/** 
	 * remove method removes a key value pair using a given key.
	 * Returns true if successful, false otherwise.
	 * 
	 * @param key
	 */
	
	@Override
	public boolean remove(K key) throws IllegalArgumentException {
		if (!containsKey(key)) { return false; }
		this.root = remove(this.root, key);
		size--;
		return true;
	}

	/** 
	 * remove method removes a key value pair using a given key.
	 * Returns node removed.
	 * 
	 * @param node
	 * @param key
	 * @return node
	 */
	
	public Node<K,V> remove(Node<K, V> node, K key) {
		if (node == null) { // if passed in node is null, return false
			return node;
		} else if (key.compareTo(node.key) < 0) { // if key is < node's key recursively call remove 
			node.left =  remove(node.left, key);
		} else if (key.compareTo(node.key) > 0) { // if key is > node's key recursively call remove
			node.right =  remove(node.right, key);
		} else { // means that we found the node to remove
			if (node.left == null && node.right == null) {
				return null;
			} else if (node.left != null && node.right == null) {
				return node.left;
			} else if (node.right != null && node.left == null) {
				return node.right;
			} else {
				Node<K, V> minNode = inorderSuccessor(node.right); //find min in right subtree
				node.key = minNode.key; // set the current node's key to the inorder successor key
				node.value = minNode.value; // set to inorder successor value
				
				remove(node.right, minNode.key); // remove the inorder successor
				return node; // return the minNode
			}
		}
		return node;
	}
	
	/** 
	 * method to order a node properly with its successor
	 * 
	 * @param node
	 * @return node in order of successor
	 */
	
	public Node<K, V> inorderSuccessor(Node<K, V> node) {
		if (node.left == null && node.right == null) {
			Node<K,V> temp = node;
			node = null;
			return temp;
		} else if (node.left == null) { return inorderSuccessor(node.right); }
		else { return inorderSuccessor(node.left); }
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
		if (!containsKey(key)) { put(key, value); }
		else { replace(key, value); }
	}

	/** 
	 * get method finds the value at the given key.
	 * Returns a call to the other get method if
	 * key does not exist.
	 * 
	 * @param key
	 */
	
	@Override
	public V get(K key) throws IllegalArgumentException {
		if (!containsKey(key)) { return null; }
		return get(this.root, key);
	}

	/** 
	 * get method returns the value of the node at the given key.
	 * Returns null if node is null.
	 * 
	 * @param node
	 * @param key
	 */
	
	public V get(Node<K, V> node, K key) {
		if (node == null) { return null; }
		else if (node.key.equals(key)) { return node.value; }
		if (get(node.left, key) == null) { return get(node.right, key); }
		return get(node.left, key);
	}

	// method to return the size.
	@Override
	public int size() {
		return this.size;
	}

	// method to check if empty.
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/** 
	 * method to check if key exists.
	 * if key is null, throws exception,
	 * otherwise calls containKey.
	 * 
	 * @param key
	 */
	
	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException(); }
		return containsKey(this.root, key);
	}

	/** 
	 * method to check if key exists.
	 * Returns false if node is null,
	 * returns true if key exists.
	 * 
	 * @param node
	 * @param key
	 */
	
	public boolean containsKey(Node<K, V> node, K key) {
		if (node == null) { return false; }
		else if (node.key.equals(key)) { return true; }
		return containsKey(node.left, key) || containsKey(node.right, key);
	}

	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do inorder traversal of the tree
	
	/* 
	 * method to return a list of the keys.
	 */
	
	@Override
	public List<K> keys() {
		List<K> keys = new ArrayList<K>();
		keys(this.root, keys);
		return keys;
	}

	/* 
	 * method to add to a list of the current keys.
	 */
	
	public void keys(Node<K, V> node, List<K> currKeys) {
		if (node == null) { return; }
		keys(node.left, currKeys);
		currKeys.add(node.key);
		keys(node.right, currKeys);
	}
	
	private static class Node<K extends Comparable<? super K>, V> 
								implements DefaultMap.Entry<K, V> {
		K key;
		V value;
		Node<K, V> left, right;

		public Node(K key, V value, Node<K, V> left, Node<K, V> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
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
	}
}