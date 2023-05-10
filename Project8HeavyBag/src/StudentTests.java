import static org.junit.Assert.*;

import org.junit.Test;

import java.util.*;

/**
 * Some test cases distributed as part of the project.
 */
public class StudentTests {

	/**
	 * Utility function Given a String, return a list consisting of one character
	 * Strings
	 */
	public static List<String> makeListOfCharacters(String s) {
		List<String> lst = new ArrayList<String>();
		for (int i = 0; i < s.length(); i++)
			lst.add(s.substring(i, i + 1));
		return lst;
	}

	/**
	 * Test adding to a Bag
	 * 
	 */
	@Test
	public void testBagAddSizeUniqueElements() {
		List<String> lst = makeListOfCharacters("aaabbc");
		HeavyBag<String> b = new HeavyBag<String>();
		b.addAll(lst);
		assertEquals(6, b.size());
		assertEquals(3, b.uniqueElements().size());
	}

	/**
	 * Test checking if a Bag contains a key, and the count for each element
	 * 
	 */
	@Test
	public void testBagContainsAndCount() {
		List<String> lst = makeListOfCharacters("aaabbc");
		HeavyBag<String> b = new HeavyBag<String>();
		b.addAll(lst);
		assertEquals(6, b.size());
		assertEquals(3, b.uniqueElements().size());
		assertTrue(b.contains("a"));
		assertTrue(b.contains("b"));
		assertTrue(b.contains("c"));
		assertFalse(b.contains("d"));
		assertEquals(3, b.getCount("a"));
		assertEquals(2, b.getCount("b"));
		assertEquals(1, b.getCount("c"));
		assertEquals(0, b.getCount("d"));
	}

	@Test
	public void testAddMany() {
		List<String> lst = makeListOfCharacters("aaabbc");
		HeavyBag<String> b = new HeavyBag<String>();
		b.addAll(lst);
		b.addMany("d", 20);
		assertEquals(26, b.size());
	}

	@Test
	public void testToString() {
		List<String> lst = makeListOfCharacters("aaabbc");
		HeavyBag<String> b = new HeavyBag<String>();
		b.addAll(lst);
		assertTrue(b.toString().equals("aaabbc"));
	}

	@Test
	public void testEqualsAndHashCode() {
		List<String> lst = makeListOfCharacters("aaabbc");
		List<String> lst1 = makeListOfCharacters("caaabb");
		HeavyBag<String> b = new HeavyBag<String>();
		HeavyBag<String> c = new HeavyBag<String>();
		b.addAll(lst);
		c.addAll(lst1);
		assertTrue(b.equals(c));
		assertTrue(b.hashCode() == c.hashCode());
	}

	@Test
	public void testIterator() {
		List<String> lst = makeListOfCharacters("aaabbc");
		HeavyBag<String> b = new HeavyBag<String>();
		b.addAll(lst);
		ArrayList<String> answer = new ArrayList<>();
		Iterator<String> it = b.iterator();
		while (it.hasNext()) {
			answer.add(it.next());
		}
		assertEquals(answer.toString(), "[a, a, a, b, b, c]");
	}

	@Test
	public void testIteratorRemove() {
		List<String> lst = makeListOfCharacters("aaabbc");
		HeavyBag<String> b = new HeavyBag<String>();
		b.addAll(lst);
		Iterator<String> it = b.iterator();
		while (it.hasNext()) {
			it.next();
		}
		it.remove();
		assertEquals(b.toString(), "aaabb");
	}
}