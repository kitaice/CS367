import static org.junit.Assert.*;
import java.util.NoSuchElementException;

import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class IndexTreeIteratorTest {
	private static int totalPoints = 0;
	private static int maxPoints = 0;

//	@Rule
	//public Timeout globalTimeout = Timeout.seconds(60);

	@AfterClass
	public static void printPoints() {
		String border = "------------------------------";
		System.out.println(String.format("\n\n%s\nPoints for IndexTreeIterator: %d / %d\n%s", border, totalPoints,
				maxPoints, border));
	}

	@Test
	public void testHasNext() {
		int points = 20;
		maxPoints += points;

		try {
			IndexTreeNode<String, Integer> root = new IndexTreeNode<String, Integer>("key", 10);
			IndexTreeIterator<String, Integer> iter = new IndexTreeIterator<String, Integer>(root);
			assertEquals(String.format(
					"(-%d point) IndexTreeIteratorTest: " + "didn't return correct results when called hasNext()",
					points), iter.hasNext(), true);
			iter.next();
			assertEquals(String.format(
					"(-%d point) IndexTreeIteratorTest: " + "didn't return correct results when called hasNext()",
					points), iter.hasNext(), false);
		} catch (Exception e) {
			fail(String.format("(-%d) IndexTreeIteratorTest: " + "did't return correct results when call hasNext()",
					points));
		}
		totalPoints += points;
	}

	@Test
	public void testNext() {
		int points = 20;
		maxPoints += points;

		try {
			IndexTreeNode<Integer, Integer> n1 = new IndexTreeNode<Integer, Integer>(10, 10);
			IndexTreeNode<Integer, Integer> n2 = new IndexTreeNode<Integer, Integer>(6, 6);
			IndexTreeNode<Integer, Integer> n3 = new IndexTreeNode<Integer, Integer>(9, 10);
			IndexTreeNode<Integer, Integer> n4 = new IndexTreeNode<Integer, Integer>(15, 10);
			IndexTreeNode<Integer, Integer> n5 = new IndexTreeNode<Integer, Integer>(2, 10);
			IndexTreeNode<Integer, Integer> n6 = new IndexTreeNode<Integer, Integer>(3, 10);
			IndexTreeNode<Integer, Integer> n7 = new IndexTreeNode<Integer, Integer>(12, 10);
			n1.setLeftChild(n2);
			n2.setRightChild(n3);
			n1.setRightChild(n4);
			n2.setLeftChild(n5);
			n5.setRightChild(n6);
			n4.setLeftChild(n7);
			IndexTreeIterator<Integer, Integer> iter = new IndexTreeIterator<Integer, Integer>(n1);
			if (iter.next().getKey() != 2 || iter.next().getKey() != 3 || iter.next().getKey() != 6
					|| iter.next().getKey() != 9 || iter.next().getKey() != 10 || iter.next().getKey() != 12
					|| iter.next().getKey() != 15) {
				fail(String.format("(-%d) IndexTreeIteratorTest: " + "did't return correct results when call next()",
						points));
			}
		} catch (Exception e) {
			fail(String.format("(-%d) IndexTreeIteratorTest: " + "did't return correct results when call next()",
					points));
		}
		totalPoints += points;
	}

	@Test
	public void testNextException() {
		int points = 10;
		maxPoints += points;

		boolean failed = true;
		try {
			IndexTreeNode<Integer, Integer> n1 = new IndexTreeNode<Integer, Integer>(10, 10);
			IndexTreeIterator<Integer, Integer> iter = new IndexTreeIterator<Integer, Integer>(n1);
			iter.next();
			iter.next();
		} catch (NoSuchElementException ex) {
			failed = false;
		} catch (Exception ex) {
		}

		assertEquals(String.format("(-%d) IndexTreeIteratorTest: " + "didn't throw NoSuchElementException "
				+ "if the iteration has no more elements when call next()", points), failed, false);
		totalPoints += points;
	}

}
