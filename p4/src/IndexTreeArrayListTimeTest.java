import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class IndexTreeArrayListTimeTest {
	private static int totalPoints = 0;
	private static int maxPoints = 0;
	private IndexTreeTester evaluator1;
	private ArrayListTester evaluator2;
	private ArrayList<MovieRecord> records;
	private Set<String> titleKeys = new HashSet<>();
	private Set<String> directorKeys = new HashSet<>(); 
	private Set<String> releaseYearKeys = new HashSet<>();
	private Set<String> ratingKeys = new HashSet<>();

	//@Rule
//		public Timeout globalTimeout = Timeout.seconds(60);

	@Before
		public void setup() {
			records = new ArrayList<MovieRecord>();
			try {
				Scanner fileScnr = new Scanner(new File("data.csv"));
				while (fileScnr.hasNextLine()) {
					String line = fileScnr.nextLine();
					String[] tokens = line.split(",");
					MovieRecord record = new MovieRecord(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(),
							tokens[3].trim(), tokens[4].trim(), tokens[5].trim(), tokens[6].trim());
					records.add(record);
					titleKeys.add(tokens[0].toLowerCase());
					directorKeys.add(tokens[2].toLowerCase());
					ratingKeys.add(tokens[3]);
					releaseYearKeys.add(tokens[1].toLowerCase());
				}
				fileScnr.close();
			} catch (Exception e) {
				System.out.println("exception while seting up tests in IndexTreeTesterTest.");
			}
			evaluator1 = new IndexTreeTester(records);
			evaluator2 = new ArrayListTester(records);
		}

	@AfterClass
		public static void printPoints() {
			String border = "------------------------------";
			System.out.println(
					String.format("\n\n%s\nPoints for IndexTreeArrayListTimeTest: %d / %d\n%s", border, totalPoints, maxPoints, border));
		}

	@Test
		public void compareSearchByKeyTime() {
			int points = 10;
			maxPoints += points;
			boolean failed = false;

			long indexTreeTime = 0, arrayListTime = 0;

			for (String key : titleKeys) {
				long start = System.currentTimeMillis();
				evaluator1.searchByKey("title", key);
				long end = System.currentTimeMillis();
				indexTreeTime += end - start;
				// search in arrayList
				start = System.currentTimeMillis();
				evaluator2.searchByKey("title", key);
				end = System.currentTimeMillis();
				arrayListTime += end - start;
			}
			failed = !(arrayListTime > indexTreeTime);
			assertEquals(String.format("(-%d point) IndexTreeArrayListTimeTest: " + "searchByKey() is not implemented correctly", points), failed,
					false);
			totalPoints += points;
		}

	@Test
		public void testRangeSearchTime() {
			int points = 10;
			maxPoints += points;
			boolean failed = false;

			long start = System.currentTimeMillis();
			evaluator1.rangeSearch("title", "t", "t");
			long end = System.currentTimeMillis();
			long t1 = end - start;

			start = System.currentTimeMillis();
			evaluator2.rangeSearch("title", "t", "t");
			end = System.currentTimeMillis();
			long t2 = end - start;

			failed = failed || !(t2 > t1);
			assertEquals(String.format("(-%d point) IndexTreeArrayListTimeTest: " + "rangeSearch() is not implemented correctly",points),failed, false);
			totalPoints += points;
		}

	@Test
		public void testAllSortedKeys() {
			int points = 10;
			maxPoints += points;
			boolean failed = false;
			long start = System.currentTimeMillis();
			evaluator1.allSortedKeys("title");
			long end = System.currentTimeMillis();
			long t1 = end - start;

			start = System.currentTimeMillis();
			evaluator2.allSortedKeys("title");
			end = System.currentTimeMillis();
			long t2 = end - start;

			failed = failed || !(t2 > t1);

			start = System.currentTimeMillis();
			evaluator1.allSortedKeys("releaseYear");
			end = System.currentTimeMillis();
			t1 = end - start;

			start = System.currentTimeMillis();
			evaluator2.allSortedKeys("releaseYear");
			end = System.currentTimeMillis();
			t2 = end - start;
			failed = failed || !(t2 > t1);

			start = System.currentTimeMillis();
			evaluator1.allSortedKeys("director");
			end = System.currentTimeMillis();
			t1 = end - start;

			start = System.currentTimeMillis();
			evaluator2.allSortedKeys("director");
			end = System.currentTimeMillis();
			t2 = end - start;
			failed = failed || !(t2 > t1);

			start = System.currentTimeMillis();
			evaluator1.allSortedKeys("rating");
			end = System.currentTimeMillis();
			t1 = end - start;

			start = System.currentTimeMillis();
			evaluator2.allSortedKeys("rating");
			end = System.currentTimeMillis();
			t2 = end - start;
			failed = failed || !(t2 > t1);

			assertEquals(String.format(
						"(-%d point) IndexTreeArrayListTimeTest: " + "allSortedKeys() is not implemented correctly", points),
					failed, false);
			totalPoints += points;
		}

}
