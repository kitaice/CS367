import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class IndexTreeTesterTest {
	private static int totalPoints = 0;
	private static int maxPoints = 0;
	private IndexTreeTester evaluator;
	private ArrayList<MovieRecord> records;
	private MovieDb movieDb;

	//@Rule
//	public Timeout globalTimeout = Timeout.seconds(60);

	@Before
	public void setup() {
		records = new ArrayList<MovieRecord>();
		try {
			Scanner fileScnr = new Scanner(new File("test_data.csv"));
			while (fileScnr.hasNextLine()) {
				String line = fileScnr.nextLine();
				String[] tokens = line.split(",");
				MovieRecord record = new MovieRecord(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(),
						tokens[3].trim(), tokens[4].trim(), tokens[5].trim(), tokens[6].trim());
				records.add(record);
			}
			fileScnr.close();
		} catch (Exception e) {
			System.out.println("exception while seting up tests in IndexTreeTesterTest.");
		}
		evaluator = new IndexTreeTester(records);
		movieDb = new MovieDb(records);
	}

	@AfterClass
	public static void printPoints() {
		String border = "------------------------------";
		System.out.println(String.format("\n\n%s\nPoints for IndexTreeTesterTest: %d / %d\n%s", border, totalPoints,
				maxPoints, border));
	}

	private boolean areSimilarLists(List<MovieRecord> a, List<MovieRecord> b) {
		if (a.size() != b.size())
			return false;
		for (int i = 0; i < a.size(); i++) {
			if (!(areEqual(a.get(i), b.get(i))))
				return false;
		}
		return true;
	}

	private boolean areEqual(MovieRecord r1, MovieRecord r2) {
		String indices[] = { "title", "releaseYear", "director", "rating", "genre", "cast", "lang" };
		for (String index : indices) {
			if (r1.getValByAttribute(index).compareTo(r2.getValByAttribute(index)) != 0)
				return false;
		}
		return true;
	}

	private List<String> getSortedKeys(String index) {
		List<String> keys = new ArrayList<>();
		Iterator<IndexTreeNode<String, List<MovieRecord>>> itr = movieDb.getIndexTree(index).iterator();
		while (itr.hasNext()) {
			IndexTreeNode<String, List<MovieRecord>> node = itr.next();
			keys.add(node.getKey());
		}
		return keys;
	}
	private List<MovieRecord> getSortedByIndex(List<MovieRecord> records, String index){
		Comparator<MovieRecord> c = new Comparator<MovieRecord>() {
			@Override
			public int compare(MovieRecord r1, MovieRecord r2) {
				return r1.getValByAttribute(index).compareTo(r2.getValByAttribute(index));
			}
		};
		records.sort(c);
		return records;
	}


	@Test
	public void testSearchByKey() {
		int points = 5;
		maxPoints += points;
		boolean failed = false;
		String val1 = "get low";
		String val2 = "2016";
		String val3 = "charles chaplin";
		String val4 = "8.5";
		try {
			List<MovieRecord> res = evaluator.searchByKey("title", val1);
			List<MovieRecord> expected = movieDb.getIndexTree("title").search(val1);
			failed = !areSimilarLists(res, expected);

			res = evaluator.searchByKey("releaseYear", val2);
			expected = movieDb.getIndexTree("releaseYear").search(val2);
			failed = failed || !areSimilarLists(res, expected);

			res = evaluator.searchByKey("director", val3);
			expected = movieDb.getIndexTree("director").search(val3);
			failed = failed || !areSimilarLists(res, expected);
			
			res = evaluator.searchByKey("rating", val4);
			expected = movieDb.getIndexTree("rating").search(val4);
			failed = failed || !areSimilarLists(res, expected);
		} catch (Exception e) {
			failed = true;
		}
		assertEquals(String.format("(-%d point) IndexTree: " + "search() is not implemented correctly", points), failed,
				false);
		totalPoints += points;
	}

	@Test
	public void testRangeSearch() {
		int points = 5;
		maxPoints += points;
		boolean failed = false;
		try {
			List<MovieRecord> res = evaluator.rangeSearch("title", "a", "z");
			List<MovieRecord> expected = movieDb.getIndexTree("title").rangeSearch("a", "z");
			res = getSortedByIndex(res, "title");
			expected = getSortedByIndex(expected, "title");
			failed = failed || !areSimilarLists(res, expected);

			res = evaluator.rangeSearch("releaseYear", "1900", "2020");
			expected = movieDb.getIndexTree("releaseYear").rangeSearch("1900", "2020");
			res = getSortedByIndex(res, "title");
			expected = getSortedByIndex(expected, "title");
			failed = failed || !areSimilarLists(res, expected);

			res = evaluator.rangeSearch("director", "bat", "cat");
			expected = movieDb.getIndexTree("director").rangeSearch("bat", "cat");
			res = getSortedByIndex(res, "title");
			expected = getSortedByIndex(expected, "title");
			failed = failed || !areSimilarLists(res, expected);

			res = evaluator.rangeSearch("rating", "1", "5");
			expected = movieDb.getIndexTree("rating").rangeSearch("1", "5");
			res = getSortedByIndex(res, "title");
			expected = getSortedByIndex(expected, "title");
			failed = failed || !areSimilarLists(res, expected);
		} catch (Exception e) {
			failed = true;
		}
		assertEquals(String.format("(-%d point) IndexTree: " + "rangeSearch() is not implemented correctly", points),
				failed, false);
		totalPoints += points;
	}

	@Test
	public void testAllSortedKeys() {
		int points = 5;
		maxPoints += points;
		boolean failed = false;
		try {
			List<String> res = evaluator.allSortedKeys("rating");
			List<String> expected = getSortedKeys("rating");
			if (res.size() != expected.size()) {
				failed = true;
			} else {
				for (int i = 0; i < expected.size(); i++) {
					if (expected.get(i) != res.get(i)) {
						failed = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			failed = true;
		}
		assertEquals(String.format("(-%d point) IndexTree: " + "rangeSearch() is not implemented correctly", points),
				failed, false);
		totalPoints += points;
	}

}
