import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class IndexTreeTest {
	private static int totalPoints = 0;
	private static int maxPoints = 0;
	private IndexTree<Integer, Integer> indexTree;

//	@Rule
//		public Timeout globalTimeout = Timeout.seconds(60);

	@Before
		public void setup() {
			indexTree = new IndexTree<Integer, Integer>();
		}

	@AfterClass
		public static void printPoints() {
			String border = "------------------------------";
			System.out.println(
					String.format("\n\n%s\nPoints for IndexTreeTest: %d / %d\n%s", border, totalPoints, maxPoints, border));
		}

	@Test
		public void testSearch() {
			int points = 10;
			maxPoints += points;

			boolean failed = false;
			try {
				indexTree.insert(10, 10);
				indexTree.insert(6, 6);
				indexTree.insert(9, 9);
				indexTree.insert(15, 15);
				indexTree.insert(1, 1);
				indexTree.insert(1, 1);
				indexTree.insert(2, 2);
				indexTree.insert(3, 3);
				indexTree.insert(1, 1);
				indexTree.insert(12, 12);
				// search with key that matches a duplicate
				List<Integer> result = indexTree.search(1);
				if (result.size() != 3 || result.get(0) != 1 || result.get(1) != 1 || result.get(2) != 1)
					failed = true;

				result = indexTree.search(10);
				if (result.size() != 1 || result.get(0) != 10)
					failed = true;
				result = indexTree.search(9);
				if (result.size() != 1 || result.get(0) != 9)
					failed = true;
				result = indexTree.search(6);
				if (result.size() != 1 || result.get(0) != 6)
					failed = true;
				result = indexTree.search(15);
				if (result.size() != 1 || result.get(0) != 15)
					failed = true;
				result = indexTree.search(2);
				if (result.size() != 1 || result.get(0) != 2)
					failed = true;
				result = indexTree.search(3);
				if (result.size() != 1 || result.get(0) != 3)
					failed = true;
				result = indexTree.search(12);

			} catch (Exception e) {
				failed = true;
			}
			assertEquals(String.format("(-%d point) IndexTree: " + "search() is not implemented correctly", points), failed,
					false);
			totalPoints += points;
		}

	@Test
		public void testSearchArgumentException() {
			int points = 5;
			maxPoints += points;
			boolean failed = true;
			try {
				// search with key being null
				indexTree.search(null);
			} catch (IllegalArgumentException ex) {
				failed = false;
			} catch (Exception ex) {
			}
			assertEquals(String.format(
						"(-%d point) IndexTree: " + "search() does not throw IllegalArgumentException when key is null",
						points), failed, false);
			totalPoints += points;
		}

	@Test
		public void testSearchException() {
			int points = 5;
			maxPoints += points;
			boolean failed = true;
			try {
				indexTree.insert(1, 1);
				// search with key being null
				indexTree.search(50);
			} catch (KeyNotFoundException ex) {
				failed = false;
			} catch (Exception ex) {
			}
			assertEquals(String.format(
						"(-%d point) IndexTree: " + "search() does not throw KeyNotFoundException when key is not found",
						points), failed, false);
			totalPoints += points;
		}

	@Test
		public void testRangeSearchException() {
			int points = 5;
			maxPoints += points;
			boolean failure = false;
			try {
				boolean failed = true;
				try {
					// range search with min value being null
					indexTree.rangeSearch(null, 10);
				} catch (IllegalArgumentException ex) {
					failed = false;
				}
				failure = failure || failed;

				failed = true;
				try {
					// range search with max value being null
					indexTree.rangeSearch(10, null);
				} catch (IllegalArgumentException ex) {
					failed = false;
				}
				failure = failure || failed;

				failed = true;
				try {
					// range search with both minVal and maxVal being null
					indexTree.rangeSearch(null, null);
				} catch (IllegalArgumentException ex) {
					failed = false;
				}
				failure = failure || failed;
			} catch (Exception ex) {
				failure = true;
			}
			assertEquals(String.format("(-%d point) IndexTree: " + "rangeSearch() does not throw IllegalArgumentException when arguments are null", points),
					failure, false);

			totalPoints += points;
		}

	@Test
		public void testRangeSearch() {
			int points = 15;
			maxPoints += points;
			boolean failed = false;
			try {
				indexTree.insert(10, 10);
				indexTree.insert(6, 6);
				indexTree.insert(9, 9);
				indexTree.insert(15, 15);
				indexTree.insert(2, 2);
				indexTree.insert(3, 3);
				indexTree.insert(12, 12);
				indexTree.insert(15, 15);
				// range search with no nodes in given range
				List<Integer> result = indexTree.rangeSearch(1, 1);
				if (result.size() != 0)
					failed = true;
				// range search with multiple keys matching in a range
				result = indexTree.rangeSearch(2, 13);
				if (!(result.size() == 6 && result.contains(2) && result.contains(3) && result.contains(6)
							|| result.contains(9) || result.contains(10) || result.contains(12)))
					failed = true;
				// range search with upper and lower bound being same and matches a
				// node with duplicate
				result = indexTree.rangeSearch(15, 15);
				if (!(result.size() == 2 && result.get(0) == 15 && result.get(1) == 15))
					failed = true;
			} catch (Exception e) {
				failed = true;
			}
			assertEquals(String.format("(-%d point) IndexTree: " + "rangeSearch() is not implemented correctly", points),
					failed, false);
			totalPoints += points;
		}

	@Test
		public void testInsert() {
			int points = 10;
			maxPoints += points;
			int keys[] = { 1, 2, 3, 4, 5, 5, 4, 3, 2, 1 };
			int values[] = { 1, 2, 3, 4, 5, 5, 4, 3, 2, 1 };
			boolean failed = false;
			try {
				for (int i = 0; i < keys.length; i++) {
					indexTree.insert(keys[i], values[i]);
				}
				Iterator<IndexTreeNode<Integer, List<Integer>>> iter = indexTree.iterator();
				if (indexTree.size() != 5 || indexTree.getHeight() != 5 || indexTree.getTotalDataCount() != 10
						|| iter.next().getData().size() != 2 || iter.next().getData().size() != 2
						|| iter.next().getData().size() != 2 || iter.next().getData().size() != 2
						|| iter.next().getData().size() != 2) {
					failed = true;
				}
			} catch (Exception ex) {
				failed = true;
			}
			assertEquals(String.format("(-%d point) IndexTree: " + "insert() is not implemented correctly", points), failed,
					false);
			totalPoints += points;
		}

	@Test
		public void testInsertNull() {
			int points = 5;
			maxPoints += points;
			boolean failure = false;
			try {
				boolean failed = true;
				try {
					// insert with key and value being null
					indexTree.insert(null, null);
				} catch (IllegalArgumentException ex) {
					failed = false;
				}
				failure = failure || failed;
				failed = true;
				try {
					indexTree.insert(10, null);
				} catch (IllegalArgumentException ex) {
					failed = false;
				}
				failure = failure || failed;
				failed = true;
				try {
					// insert with key being null
					indexTree.insert(null, 10);
				} catch (IllegalArgumentException ex) {
					failed = false;
				}
				failure = failure || failed;
			} catch (Exception ex) {
				failure = true;
			}
			assertEquals(String.format(
						"(-%d) IndexTreeTest: didn't throw "
						+ "IllegalArgumentException if either or both key, value is/are null " + "when called insert()",
						points), failure, false);
			totalPoints += points;
		}

	@Test
		public void testSize() {
			int points = 5;
			maxPoints += points;
			boolean failed = false;
			try {
				// empty tree
				int result = indexTree.size();
				if (result != 0)
					failed = true;
				for (int i = 0; i < 100; i++) {
					indexTree.insert(1, 1);
				}
				// tree with only one node and is duplicated
				result = indexTree.size();
				if (result != 1)
					failed = true;
			} catch (Exception e) {
				failed = true;
			}
			assertEquals(String.format("(-%d point) IndexTree: " + "getNumNodes() is not implemented correctly", points),
					failed, false);
			totalPoints += points;
		}

	@Test
		public void testgetHeight() {
			int points = 5;
			maxPoints += points;
			boolean failed = false;
			try {
				// empty tree
				int result = indexTree.getHeight();
				if (result != 0)
					failed = true;
				for (int i = 0; i < 100; i++) {
					indexTree.insert(1, 1);
				}
				// tree with only one node and is duplicated
				result = indexTree.getHeight();
				if (result != 1)
					failed = true;
				// right skewed tree
				for (int i = 1; i <= 100; i++) {
					indexTree.insert(i, 1);
				}
				result = indexTree.getHeight();
				if (result != 100)
					failed = true;
				// make it left skewed now
				for (int i = 1; i <= 500; i++) {
					indexTree.insert(-i, 1);
				}
				result = indexTree.getHeight();
				if (result != 501)
					failed = true;

			} catch (Exception e) {
				failed = true;
			}
			assertEquals(String.format("(-%d point) IndexTree: " + "getHeight() is not implemented correctly", points),
					failed, false);
			totalPoints += points;
		}

	@Test
		public void testgetTotalDataCount() {
			int points = 5;
			maxPoints += points;
			boolean failed = false;
			try {
				// empty tree
				int result = indexTree.getTotalDataCount();
				if (result != 0)
					failed = true;
				for (int i = 0; i < 100; i++) {
					indexTree.insert(1, 1);
				}
				// tree with only one node and is duplicated
				result = indexTree.getTotalDataCount();
				if (result != 100)
					failed = true;
			} catch (Exception e) {
				failed = true;
			}
			assertEquals(
					String.format("(-%d point) IndexTree: " + "getTotalDataCount() is not implemented correctly", points),
					failed, false);

			totalPoints += points;
		}

	@Test
		public void testgetAvgDataCount() {
			int points = 5;
			maxPoints += points;
			boolean failed = false;
			try {
				// empty tree
				double result = indexTree.getAvgDataCount();
				if (result != 0)
					failed = true;
				for (int i = 0; i < 5; i++) {
					indexTree.insert(1, 1);
				}
				indexTree.insert(2, 2);
				indexTree.insert(2, 2);
				indexTree.insert(3, 3);
				// tree with duplicate values
				result = indexTree.getAvgDataCount();
				// compare double for equality using threshold
				if (!(Math.abs(result - 2.667) <= 0.000001))
					failed = true;
			} catch (Exception e) {
				failed = true;
			}
			assertEquals(
					String.format("(-%d point) IndexTree: " + "getAvgDataCount() is not implemented correctly", points),
					failed, false);

			totalPoints += points;
		}
}
