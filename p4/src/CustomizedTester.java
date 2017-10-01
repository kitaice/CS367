import java.util.ArrayList;
import java.util.HashSet;import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class CustomizedTester {
	// List of movie records that we want to run analysis on.
		private List<MovieRecord> movieRecordList;

		// Sets of keys on which we can query.
		private Set<String> titleKeys;
		private Set<String> directorKeys;
		private Set<String> releaseYearKeys;
		private Set<String> ratingKeys;

		/**
		 * <p>
		 * Main method. Every application needs one. Command-line arguments are
		 * required.
		 * </p>
		 * 
		 * <ul>
		 * <li>args[0] is the name of an existing data file that contains a list of
		 * movie records described by comma-separated attributes. This is the data
		 * set that will be used to build our Index trees.</li>
		 * </ul>
		 * 
		 * <p>
		 * For example:
		 * </p>
		 * 
		 * <pre>
		 * java MovieDbTesterMain data.csv
		 * </pre>
		 *
		 * @param args
		 *            filename
		 */
		public static void main(String[] args) {
			if (args.length != 1) {
				System.err.println("Bad invocation! Correct usage: "
						+ "java MovieDbTesterMain <Data file> ");
				System.exit(1);
			}

			String dataFilename = args[0];
			CustomizedTester customizedtester = new CustomizedTester(dataFilename);
			customizedtester.initialize();
			customizedtester.evaluateAll();
		}

		/**
		 * Construct a set of all candidate keys on which we will query our
		 * performance on Index trees and ArrayList.
		 */
		public void initialize() {
			titleKeys = new HashSet<>();
			releaseYearKeys = new HashSet<>();
			directorKeys = new HashSet<>();
			ratingKeys = new HashSet<>();

			for (MovieRecord rec : movieRecordList) {
				titleKeys.add(rec.getTitle().toLowerCase());
				releaseYearKeys.add(rec.getReleaseYear());
				directorKeys.add(rec.getDirector().toLowerCase());
				ratingKeys.add(rec.getRating());
			}
		}

		/**
		 * Evaluate search by key time for all sets of keys for both the data
		 * structures.
		 */
		public void evaluateAll() {
			// Search By Key comparison.
			testing();
			System.out.println();
		}

		/**
		 * Evaluates search by key performance.
		 * 
		 * @param arrayListTester
		 * @param indexTreeTester
		 * @param index
		 * @param keys
		 */
		private void testing() {
			// Create testers for the two data structures.
			TesterADT<String, MovieRecord> arrayListTester =
					new ArrayListTester(movieRecordList);
			TesterADT<String, MovieRecord> indexTreeTester =
					new IndexTreeTester(movieRecordList);
			
			System.out.println("Test ArrayListTester");
			//Test ArrayListTester Key Search
			System.out.println("Key Search");
			System.out.println(arrayListTester.searchByKey("title", "inception"));
			//Test ArrayListTester Range Search
			System.out.println("Range Search");
			System.out.println("Range by year: " + arrayListTester.rangeSearch("releaseYear", "2015", "2016").size());
			System.out.println("Range by rating: " + arrayListTester.rangeSearch("rating", "2", "3").size());
			System.out.println("Range by title: " + arrayListTester.rangeSearch("title", "a", "b").size());
			System.out.println("Range by director: " + arrayListTester.rangeSearch("director", "a", "b").size());
			//Test ArrayListTester Sorted Search
			System.out.println("Sorted Key");
			System.out.println(arrayListTester.allSortedKeys("title").size());
			System.out.println(" ");
			
			System.out.print("Test IndexTreeTester");
			//Test IndexTreeTester Key Search
			System.out.println("Key Search");
			System.out.println(indexTreeTester.searchByKey("title", "inception"));
			//Test IndexTreeTester Range Search
			System.out.println("Range Search");
			System.out.println("Range by year: " + indexTreeTester.rangeSearch("releaseYear", "2015", "2016").size());
			System.out.println("Range by rating: " + indexTreeTester.rangeSearch("rating", "2", "3").size());
			System.out.println("Range by title: " + indexTreeTester.rangeSearch("title", "a", "b").size());
			System.out.println("Range by director: " + indexTreeTester.rangeSearch("director", "a", "b").size());
			//Test IndexTreeTester Sorted Search
			System.out.println("Sorted Key");
			System.out.println(indexTreeTester.allSortedKeys("title").size());
		}
		
		public CustomizedTester(String dataFile) {
			// Read movie data from the file.
			try {
				movieRecordList = new ArrayList<MovieRecord>();
				Scanner fileScnr = new Scanner(new File(dataFile));
				// Read individual data values from the file
				while (fileScnr.hasNextLine()) {
					String line = fileScnr.nextLine();
					String[] tokens = line.split(",");
					MovieRecord record = new MovieRecord(tokens[0].trim(),
							tokens[1].trim(), tokens[2].trim(),
							tokens[3].trim(), tokens[4].trim(),
							tokens[5].trim(), tokens[6].trim());
					movieRecordList.add(record);
				}
				fileScnr.close();
			} catch (FileNotFoundException e) {
				System.out.println("MovieDbTesterMain Construction fails..."
								+ dataFile + " file not found.");
				System.exit(0);
			} catch (Exception e) {
				System.out.println("MovieDbTesterMain Construction fails..."
								+ e.getMessage());
				System.exit(0);
			}
		}
}
