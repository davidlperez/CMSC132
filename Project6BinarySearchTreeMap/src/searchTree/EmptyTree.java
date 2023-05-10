package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 *  
 */
 public class EmptyTree<K extends Comparable<K>,V> implements Tree<K,V> {
	/**
	 * This static field references the one and only instance of this class.
	 * We won't declare generic types for this one, so the same singleton
	 * can be used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();

	public static  <K extends Comparable<K>, V> EmptyTree<K,V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 *  
	 */
	private EmptyTree() {
		// Nothing to do
	}
	/**
	 * Searches for value based on key
	 * 
	 * @param K key
	 * @return null because tree is empty
	 */
	public V search(K key) {
		return null;
	}
	
	/**
	 * Inserts key and value
	 * 
	 * @param K key
	 * @param V value
	 * @return new NonEmptyTree with key and value as the first
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		return new NonEmptyTree<K,V>(key, value, this, this);
	}

	/**
	 * no-op
	 * 
	 * @return current tree since nothing to delete 
	 */
	public Tree<K, V> delete(K key) {
		return this;
	}
	
	/**
	 * @throws TreeIsEmptyException since there is no max (empty tree)
	 */
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/**
	 * @throws TreeIsEmptyExceptino since there is no min (empty tree)
	 */
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/**
	 * @return 0 since tree is empty
	 */
	public int size() {
		return 0;
	}

	/**
	 * @return no-op
	 */
	public void addKeysToCollection(Collection<K> c) {
		return;
	}

	/**
	 * @return this since no-op
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		return this;
	}
}