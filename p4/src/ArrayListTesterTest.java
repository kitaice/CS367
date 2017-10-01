import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

/** Runs junit tests on student's ArrayList class */
public class ArrayListTesterTest {
    private static int totalPoints = 0;
    private static int maxPoints = 0;
    private ArrayListTester evaluator;
    private ArrayList<MovieRecord> records;

  //  @Rule
    //    public Timeout globalTimeout = Timeout.seconds(60);

/** creates an ArrayList with all Movie data loaded. Used by all tests. */
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
                System.out.println("exception while seting up tests in ArrayListTesterTest");
            }
            evaluator = new ArrayListTester(records);
        }

/** prints totals for units tests of ArrayList */
    @AfterClass
        public static void printPoints() {
            String border = "------------------------------";
            System.out.println(
                    String.format("\n\n%s\nPoints for ArayListTester: %d / %d\n%s", border, totalPoints, maxPoints, border));
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

    private static boolean areEqual(MovieRecord r1, MovieRecord r2) {
        String indices[] = { "title", "releaseYear", "director", "rating", "genre", "cast", "lang" };
        for (String index : indices) {
            if (r1.getValByAttribute(index).compareTo(r2.getValByAttribute(index)) != 0)
                return false;
        }
        return true;
    }

    private static List<String> getSortedKeys(List<MovieRecord> data, String index) {
        List<String> keys = new ArrayList<String>();
        for (MovieRecord record : data) {
            keys.add(record.getValByAttribute(index).toLowerCase());
        }
        Collections.sort(keys);
        return keys;
    }

    private List<MovieRecord> searchByKey(String index, String key) {
        List<MovieRecord> result = new ArrayList<MovieRecord>();
        for (MovieRecord rec : records) {
            if (rec.getValByAttribute(index).equalsIgnoreCase(key)) {
                result.add(rec);
            }
        }
        return result;
    }

    private List<MovieRecord> rangeSearch(String index, String minVal, String maxVal) {
        List<MovieRecord> result = new ArrayList<MovieRecord>();
        for (MovieRecord rec : records) {
            if (rec.getValByAttribute(index).compareToIgnoreCase(maxVal) <= 0
                    && rec.getValByAttribute(index).compareToIgnoreCase(minVal) >= 0) {
                result.add(rec);
            }
        }
        return result;
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
            try {
                // search for a key which is in lowercase in test file

                List<MovieRecord> res = evaluator.searchByKey("title", "get low");
                res = getSortedByIndex(res, "title");
                List<MovieRecord> expected = searchByKey("title", "get low");
                expected = getSortedByIndex(expected, "title");

                if (!areSimilarLists(res, expected))
                    failed = true;

                // search of key with 2 movierecords
                res = evaluator.searchByKey("releaseYear", "2016");
                expected = searchByKey("releaseYear", "2016");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;

                res = evaluator.searchByKey("director", "charles chaplin");
                expected = searchByKey("director", "charles chaplin");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;

                res = evaluator.searchByKey("rating", "8.5");
                expected = searchByKey("rating", "8.5");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;


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
                // result should exclude zootopia
                List<MovieRecord> res = evaluator.rangeSearch("title", "a", "z");
                List<MovieRecord> expected = rangeSearch("title", "a", "z");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;

                // result should include all records
                res = evaluator.rangeSearch("releaseYear", "1900", "2020");
                expected = rangeSearch("releaseYear", "1900", "2020");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;

                res = evaluator.rangeSearch("director", "bat", "cat");
                expected = rangeSearch("director", "bat", "cat");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;
                res = evaluator.rangeSearch("rating", "1", "5");
                expected = rangeSearch("rating", "1", "5");
                res = getSortedByIndex(res, "title");
                expected = getSortedByIndex(expected, "title");
                if (!areSimilarLists(res, expected))
                    failed = true;

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
                List<String> res = evaluator.allSortedKeys("title");
                List<String> expected = getSortedKeys(records, "title");
                if (res.size() != expected.size()) {
                    failed = true;
                } else {
                    for (int i = 0; i < expected.size(); i++) {
                        if (!(expected.get(i).equalsIgnoreCase(res.get(i)))) {
                            failed = true;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                failed = true;
            }
            assertEquals(String.format("(-%d point) IndexTree: " + "allSortedKeys() is not implemented correctly", points),
                    failed, false);
            totalPoints += points;
        }

}
