package listClass;

import java.util.Iterator;
/**
 * This class represents a linked list of generic type <T> with the
 * ability to add nodes to the front and end, as well as remove from front and end.
 * A tail reference is provided.
 * @author davidperez
 *
 * @param <T>
 */
public class BasicLinkedList<T> implements Iterable<T> {
/**
 * Static nested class for each node added to linked list.
 * @author davidperez
 *
 * @param <T>
 */
	public static class Node<T> {

		private T data;
		private Node<T> next;

		/**
		 * Constructor for each Node
		 * @param data
		 */
		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int size;
/**
 * This constructor initializes the linked list of size 0, with the head and tail
 * both pointing to null
 */
	public BasicLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
/**
 * 
 * @return the current size of the array
 */
	public int getSize() {
		return size;
	}
/**
 * Adds data to a node and then places it at the end of the linked list
 * with the tail pointing to it
 * @param data data to add to node at the end of linked list
 * @return reference to the current object
 */
	public BasicLinkedList<T> addToEnd(T data) {
		Node<T> node = new Node<>(data); // creates new node with data
		if (head == null) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
		return this;
	}
/**
 * Adds data to a node and then places it at the front of the linked list
 * with the head pointing to it
 * @param data data to add to node at the front of linked list
 * @return reference to the current object
 */
	public BasicLinkedList<T> addToFront(T data) {
		Node<T> node = new Node<>(data);
		if (size == 0) {
			head = node;
			tail = node;
		} else {
			node.next = head;
			head = node;
		}
		size++;
		return this;
	}
/**
 * takes the data from the first node
 * @return data from the node with the head reference
 */
	public T getFirst() {
		if (size != 0) {
			return head.data;
		}
		return null;
	}
/**
 * takes the data from the last node
 * @return data from the node with the tail reference
 */
	public T getLast() {
		if (size != 0) {
			return tail.data;
		}
		return null;
	}
/**
 * removes the first element from the linked list and returns it
 * @return data from the node with the head reference
 */
	public T retrieveFirstElement() {
		if (size != 0) {
			T curr = head.data; // maintains reference to current first element
			head = head.next;
			size--;
			return curr;
		}
		return null;
	}
/**
 * removes the last element from the linked list and returns it
 * @return data from the node with the tail reference
 */
	public T retrieveLastElement() {
		if (size != 0) {
			T curr = tail.data; // maintains reference to current last element
			Node<T> pointer = head; // traversing reference
			while(pointer.next != tail) {
				pointer = pointer.next;
			}
			tail = pointer;
			pointer.next = null;
			size--;
			return curr;
		}
		return null;
	}
/**
 * removes every instance node of the parameter within the linked list
 * @param targetData item to remove
 * @return reference to current object after target is removed
 */
	public BasicLinkedList<T> removeAllInstances(T targetData) {
		if(size != 0) {
			Node<T> pointer = head;
			while(pointer != tail) {
				if(pointer.data == targetData) { // if first node is targetData
					pointer = pointer.next;
					head.next = null;
					head = pointer;
					size--;
					if(size == 1 && pointer.data == targetData) { // if only node is targetData
						tail = null;
					}
					continue;
				}
				if(pointer.next.data == targetData) {
					pointer.next = pointer.next.next;
					size--;
				}
				if (pointer.next == null) { // moves tail if last node is targetData
					tail = pointer;
					continue;
				}
				pointer = pointer.next;
				
			}
			return this;
		}
		return null;
	}
/**
 * returns iterator with hasNext() and next()
 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node<T> curr = head; // counter

			@Override
			public boolean hasNext() {
				return curr != null; // returns true until curr is the last node
			}

			@Override
			public T next() {
				T data = curr.data;
				curr = curr.next; // moves counter one more node
				return data;
			}
		};
	}
}
