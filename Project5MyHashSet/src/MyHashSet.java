import java.util.ArrayList;
import java.util.Iterator;

/**
 * The MyHashSet API is similar to the Java Set interface. This collection is
 * backed by a hash table.
 */
public class MyHashSet<E> implements Iterable<E> {

	/**
	 * Unless otherwise specified, the table will start as an array (ArrayList) of
	 * this size.
	 */
	private final static int DEFAULT_INITIAL_CAPACITY = 4;

	/**
	 * When the ratio of size/capacity exceeds this value, the table will be
	 * expanded.
	 */
	private final static double MAX_LOAD_FACTOR = 0.75;

	public ArrayList<Node<E>> hashTable;

	private int size; // number of elements in the table

	/**
	 * Class for each node attached to list in hash table
	 * 
	 * @author CMSC132
	 *
	 * @param <T>
	 */
	public static class Node<T> {
		private T data;
		public Node<T> next;

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/**
	 * Initializes an empty table with the specified capacity by creating an
	 * ArrayList and filling it with null values until the inital capacity is
	 * reached.
	 *
	 * @param initialCapacity initial capacity (length) of the underlying table
	 */
	public MyHashSet(int initialCapacity) {
		hashTable = new ArrayList<Node<E>>();
		while (hashTable.size() < initialCapacity) {
			hashTable.add(null);
		}

	}

	/**
	 * Initializes an empty table of length equal to DEFAULT_INITIAL_CAPACITY. This
	 * default constructor calls the above constructor with DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Returns the total number of elements stored in the table.
	 * 
	 * @return number of elements in the table
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * 
	 * @return length of the table (capacity)
	 */
	public int getCapacity() {
		return hashTable.size();
	}

	/**
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(Object element) {
		Node<E> curr = hashTable.get(bucket(element)); // Uses hashing to determine which bucket element is in
		while (curr != null) {
			if (curr.data.equals(element)) {
				return true;
			}
			curr = curr.next; // assigns curr to next item in list
		}
		return false;
	}

	/**
	 * Adds the specified element to the collection, if it is not already present.
	 * If the element is already in the collection, then this method does nothing.
	 * Once MAX_LOAD_FACTOR is reached, size of table doubles and everything is
	 * reHashed.
	 * 
	 * @param element the element to be added to the collection
	 */
	public void add(E element) {
		if (contains(element)) {
			return; // does nothing if element is already present
		} else {
			Node<E> toAdd = new Node<E>(element);
			if (hashTable.get(bucket(element)) == null) { // if bucket is currently empty
				hashTable.set(bucket(element), toAdd);
			} else {
				Node<E> curr = hashTable.get(bucket(element));

				while (curr.next != null) { // finds last item in bucket
					curr = curr.next;
				}
				if (curr.next == null) {
					curr.next = toAdd; // adds element to the end of list
				}
			}
			size++;
		}
		/**
		 * if the total amount of items divided by the capacity is greater than or equal
		 * to the MAX_LOAD_FACTOR, the entire table is doubled in size, and all items
		 * within the table are rehashed in reHash() function to be replaced in the new
		 * table
		 */
		if (((double) size() / (double) getCapacity()) >= MAX_LOAD_FACTOR) {
			reHash();
		}
	}

	/**
	 * Removes the specified element from the collection. If the element is not
	 * present then this method should do nothing (and return false in this case).
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element removed
	 */
	public boolean remove(Object element) {
		if (contains(element)) { // hashTable must contain the element in order for it to be removed
			Node<E> curr = hashTable.get(bucket(element));
			Node<E> prev = null; // prev reference to successfully remove node from set
			while (curr != null) {
				if (curr.data.equals(element)) {
					if (curr == hashTable.get(bucket(element))) {
						hashTable.set(bucket(element), curr.next);
					} else {
						prev.next = curr.next;
					}
					return true;
				} else {
					prev = curr;
					curr = curr.next;
				}
			}
		}
		return false; // if the object is not found
	}

	/**
	 * Returns an Iterator that can be used to iterate over all of the elements in
	 * the collection.
	 * 
	 * The order of the elements is unspecified.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			int pos = 0; // position counter to go through each bucket
			int count = 0; // counter of how many times a value is returned
			Node<E> curr; // current node
			Node<E> ans; // node returned in next() method

			/**
			 * @return false if pos counter is greater than or equal to number of buckets
			 *         and current reference is null (reached the end of hashSet); true
			 *         otherwise
			 */
			@Override
			public boolean hasNext() {
				return count < size();
			}

			/**
			 * @return data from each node iterated through
			 */
			@Override
			public E next() {
				if (hasNext()) { // only runs if hasNext() is true
					while (hashTable.get(pos) == null || curr == null) { // skips over null
						try {
							pos++;
							curr = hashTable.get(pos);
						} catch (IndexOutOfBoundsException e) { // catches exception for when pos == getCapacity()
							break;
						}
					}

					if (curr != null) {
						ans = curr;
						curr = curr.next;
						count++;
					}
				}
				return ans.data;
			}

		};
	}

	/**
	 * Uses hashCode() and the capacity to determine which bucket each element is to
	 * be placed
	 * 
	 * @param element
	 * @return int of which bucket each element is to be placed
	 */
	private int bucket(Object element) {
		return Math.abs(element.hashCode() % getCapacity());
	}

	/**
	 * when the total number of items divided by the capacity exceeds
	 * MAX_LOAD_FACTOR, this method is called to double the size of the table and
	 * re-add everything back in with new bucket numbers
	 */
	private void reHash() {
		MyHashSet<E> newHashSet = new MyHashSet<E>(2 * getCapacity()); // calls constructor with double the initial
																		// capacity

		for (int i = 0; i < getCapacity(); i++) {
			if (hashTable.get(i) != null) {
				Node<E> curr = hashTable.get(i);
				while (curr != null) {
					newHashSet.add(curr.data); // hashing is done with add function
					curr = curr.next;
				}
			}
		}
		hashTable = newHashSet.hashTable; // sets current hashTable to new hashTable
	}
}
