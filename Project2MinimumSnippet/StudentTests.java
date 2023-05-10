import java.util.Arrays;

import org.junit.Test;

import junit.framework.TestCase;

public class StudentTests extends TestCase {

	static MinimumSnippet get(String[] doc, String[] terms) {
		return new MinimumSnippet(Arrays.asList(doc), Arrays.asList(terms));
	}

	/*
	 * Test finding "1" and "2" in the document "0", "0", "1", "0", "2", "0", "0",
	 * "0"
	 */
	@Test
	public void test12in00102000() {
		MinimumSnippet m = get(new String[] { "0", "0", "1", "0", "2", "0", "0", "0" }, new String[] { "1", "2" });
		assertTrue(m.foundAllTerms());
		assertEquals(2, m.getStartingPos());
		assertEquals(4, m.getEndingPos());
		assertEquals(3, m.getLength());
		assertEquals(2, m.getPos(0));
		assertEquals(4, m.getPos(1));
	}

	/*
	 * Test finding "1" and "2" in the document "0", "0", "1", "0", "2", "1", "0",
	 * "0", "0"
	 */
	@Test
	public void test12in001021000() {
		MinimumSnippet m = get(new String[] { "0", "0", "1", "0", "2", "1", "0", "0", "0" }, new String[] { "1", "2" });
		assertTrue(m.foundAllTerms());
		assertEquals(4, m.getStartingPos());
		assertEquals(5, m.getEndingPos());
		assertEquals(2, m.getLength());
		assertEquals(5, m.getPos(0));
		assertEquals(4, m.getPos(1));
	}

	/*
	 * Test finding "1", "2", and "3" in the document "0", "1", "0", "2", "0", "3",
	 * "0", "3", "2", "0", "1", "0", "0"
	 */
	@Test
	public void test123in0102030320100() {
		MinimumSnippet m = get(new String[] { "0", "1", "0", "2", "0", "3", "0", "3", "2", "0", "1", "0", "0" },
				new String[] { "1", "2", "3" });
		assertTrue(m.foundAllTerms());
		assertEquals(7, m.getStartingPos());
		assertEquals(10, m.getEndingPos());
		assertEquals(4, m.getLength());
		assertEquals(10, m.getPos(0));
		assertEquals(8, m.getPos(1));
		assertEquals(7, m.getPos(2));
	}

	/*
	 * Test finding "1", "2", "3", and "4" in the document "0", "1", "0", "2", "0",
	 * "3", "0", "3", "2", "0", "1", "0", "4", "0", "0"
	 */
	@Test
	public void test1234in01020303201040() {
		MinimumSnippet m = get(new String[] { "0", "1", "0", "2", "0", "3", "0", "3", "2", "0", "1", "0", "4", "0" },
				new String[] { "1", "2", "3", "4" });
		assertTrue(m.foundAllTerms());
		assertEquals(7, m.getStartingPos());
		assertEquals(12, m.getEndingPos());
		assertEquals(6, m.getLength());
		assertEquals(10, m.getPos(0));
		assertEquals(8, m.getPos(1));
		assertEquals(7, m.getPos(2));
		assertEquals(12, m.getPos(3));
	}
}