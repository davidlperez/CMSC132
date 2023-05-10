package tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import listClass.BasicLinkedList;

public class StudentTests {

	BasicLinkedList<Integer> blist = new BasicLinkedList<>();

	/**
	 * loads the LinkedList blist with 314159 using add to front and end
	 */
	private void loadList1() {
		blist.addToFront(4);
		blist.addToFront(1);
		blist.addToFront(3);
		blist.addToEnd(1);
		blist.addToEnd(5);
		blist.addToEnd(9);
	}

	/**
	 * loads the LinkedList blist with 101010 using add to front and end
	 */
	private void loadList2() {
		blist.addToEnd(0);
		blist.addToEnd(1);
		blist.addToEnd(0);
		blist.addToFront(1);
		blist.addToFront(0);
		blist.addToFront(1);
	}

	/**
	 * tests getSize()
	 */
	@Test
	public void testGetSize() {
		loadList1();

		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}

		assertEquals("314159", answer);
		assertEquals(6, blist.getSize());
	}

	/**
	 * tests getFirst() and getLast()
	 */
	@Test
	public void testGetFirstAndLast() {
		loadList1();

		assertTrue(3 == blist.getFirst());
		assertTrue(9 == blist.getLast());
	}

	/**
	 * tests retrieveFirstElement() and retrieveLastElement()
	 */
	@Test
	public void testRetrieving() {
		loadList1();

		Integer first = blist.retrieveFirstElement();
		Integer last = blist.retrieveLastElement();

		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}

		assertEquals(4, blist.getSize());
		assertEquals("1415", answer);
		assertTrue(3 == first);
		assertTrue(9 == last);
	}

	/**
	 * tests removeAllInstances()
	 */
	@Test
	public void testRemoveAllInstances1() {
		loadList1();

		blist.removeAllInstances(1);

		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}

		assertEquals("3459", answer);
		assertEquals(4, blist.getSize());
	}

	/**
	 * tests iterator() hasNext() and next() methods
	 */
	@Test
	public void testIterator() {
		loadList1();
		Iterator<Integer> it = blist.iterator();

		String answer = "";
		while (it.hasNext()) {
			answer += it.next();
		}

		assertTrue(answer.equals("314159"));
		assertTrue(answer.length() == blist.getSize());
	}

	/**
	 * tests removeAllInstances when targetData is first node
	 */
	@Test
	public void testRemoveAllInstances2() {
		loadList2();
		blist.removeAllInstances(1);

		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}

		assertEquals("000", answer);
		assertEquals(3, blist.getSize());
	}

	/**
	 * tests removeAllInstances when targetData is last node
	 */
	@Test
	public void testRemoveAllInstances3() {
		loadList2();
		blist.removeAllInstances(0);

		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}

		assertEquals("111", answer);
		assertEquals(3, blist.getSize());
	}

	/**
	 * tests removing all nodes so linked list is empty
	 */
	@Test
	public void testRemoveAll() {
		loadList2();
		blist.removeAllInstances(0);
		blist.removeAllInstances(1);

		String answer = "";
		for (Integer entry : blist) {
			answer += entry;
		}

		assertEquals("", answer);
		assertEquals(0, blist.getSize());
		assertNull(blist.getFirst());
		assertNull(blist.getLast());
	}
}
