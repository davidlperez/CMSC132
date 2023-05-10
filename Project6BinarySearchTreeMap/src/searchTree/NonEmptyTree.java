package searchTree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 * 
 */
public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {

	private Tree<K, V> left, right;
	private K key;
	private V value;

	/**
	 * Constructor that creates a tree with key-value pairs and subtrees instead of
	 * nodes
	 * 
	 * @param key
	 * @param value
	 * @param left
	 * @param right
	 */
	public NonEmptyTree(K key, V value, Tree<K, V> left, Tree<K, V> right) {
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * Find the value that corresponds to the key in the tree Searches recursively
	 * and uses dynamic dispatch to stop at the end of tree
	 * 
	 * @param K key to search for
	 * @return value assigned to given key
	 */
	public V search(K key) {
		int result = key.compareTo(this.key);
		if (result == 0) { // if current key is the one being searched for
			return this.value;
		} else if (result < 0) { // if current key is less than (left subtree)
			return left.search(key);
		} else if (result > 0) { // if more than (right subtree)
			return right.search(key);
		} else {
			return null;
		}
	}

	/**
	 * Searches through tree to place key so that the binary search remains intact;
	 * continues until the insert method is called for an EmptyTree
	 * 
	 * @param K key
	 * @param V value
	 * @return NonEmptyTree<K, V> usually a reference to itself
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		int result = key.compareTo(this.key);
		if (result == 0) {
			this.value = value;
		} else if (result < 0) {
			this.left = this.left.insert(key, value);
		} else if (result > 0) {
			this.right = this.right.insert(key, value);
		}
		return this;
	}

	/**
	 * Finds and removes key from tree while maintaining the binary search element
	 * 
	 * @param K key
	 * @return Tree<K, V>
	 */
	public Tree<K, V> delete(K key) {
		int result = key.compareTo(this.key);
		if (result == 0) {
			try {
				K maximum = left.max(); // throws TreeIsEmptyException
				this.key = maximum;
				value = left.search(maximum);
				left = left.delete(left.min());
			} catch (TreeIsEmptyException e) { // handles exception
				return right;
			}
		}
		if (result < 0) {
			left = left.delete(key);
		} else if (result > 0) {
			right = right.delete(key);
		}
		return this;
	}

	/**
	 * traverses the right subtree until max value is found
	 * 
	 * @return key of maximum value
	 */
	public K max() {
		try {
			return right.max();
		} catch (TreeIsEmptyException e) {
			// EmptyTree throws TreeIsEmptyExcpetion as stopping case
			return key;
		}
	}

	/**
	 * traverses the left subtree until min value is found
	 * 
	 * @return key of minimum value
	 */
	public K min() {
		try {
			return left.min();
		} catch (TreeIsEmptyException e) {
			// EmptyTree throws TreeIsEmptyExcpetion as stopping case
			return key;
		}
	}

	/**
	 * Adds the size of left subtree, right subtree, and 1 for the top
	 * 
	 * @return int size of entire tree
	 */
	public int size() {
		return left.size() + right.size() + 1;
	}

	/**
	 * Adds all keys from tree into a collection c in sorted order
	 * 
	 * @param Collection<K> c of keys
	 */
	public void addKeysToCollection(Collection<K> c) {
		left.addKeysToCollection(c);
		c.add(key);
		right.addKeysToCollection(c);
	}

	/**
	 * Traverses through tree and adds the trees starting at fromKey through toKey
	 * 
	 * @param K fromKey starting key to add to subtree
	 * @param K toKey final key to add to subtree
	 * @return Tree<K, V> subTree
	 * 
	 */
	public Tree<K, V> subTree(K fromKey, K toKey) {
		Tree<K, V> ans = null;
		if (fromKey.compareTo(key) > 0) {
			ans = right.subTree(fromKey, toKey);
		} else if (toKey.compareTo(key) < 0) {
			ans = left.subTree(fromKey, toKey);
		} else {
			return new NonEmptyTree<K, V>(key, value, left.subTree(fromKey, toKey), right.subTree(fromKey, toKey));
		}
		return ans;
	}
}