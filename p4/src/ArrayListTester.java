///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MovieDbMain.java
//File:             ArrayListTester.java
//Semester:         CS367 Spring 2016
//Author:           jsheng7@wisc.edu
//CS Login:         jsheng
//Lecturer's Name:  Deb Deppeler
//
//////////////////////////////////////////////////////////////////////////////
//
//Pair Partner:     Conrad Chen
//Email:            wchen283@wisc.edu
//CS Login:         conradc
//Lecturer's Name:  Deb Deppeler
//Lab Section:      (your partner's lab section number)
//
//////////////////////////////////////////////////////////////////////////////
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Tester implementation using ArrayList as the data structure.
 *
 * @author apul
 */
public class ArrayListTester implements TesterADT<String, MovieRecord> {
	// ArrayList of movie records.
	private List<MovieRecord> movieRecordList;

	/**
	 * Constructor that initializes the movieRecordList.
	 * 
	 * @param movieRecordList
	 *            list of movie records.
	 */
	public ArrayListTester(List<MovieRecord> movieRecordList) {
		this.movieRecordList = movieRecordList;//constructor
	}

	/**
	 * Returns size of the data list.
	 * 
	 * @return number of movie records.
	 */
	public int size() {
		return movieRecordList.size();//return size of the arraylist
	}

	/**
	 * Search for records having index value equal to key.
	 * 
	 * @param index
	 *            the index (attribute) we want to search for.
	 * @param key
	 *            the key value we are looking for.
	 * @return the list of movie records.
	 */
	@Override
	public List<MovieRecord> searchByKey(String index, String key) {
		// TODO
		// Go over all movie records and compare the attribute (index
		// e.g., director) with the key (e.g, Christopher Nolan)
		// NOTE: Ignore case while comparing the attribute (index) value
		// with the key.
		List<MovieRecord> resultList = new ArrayList<MovieRecord>();

		for(int i = 0;i < movieRecordList.size();i ++){
			if(movieRecordList.get(i).getValByAttribute(index).
					equalsIgnoreCase(key)){
				resultList.add(movieRecordList.get(i));
				//add result into the list if key matchs 
			}
		}

		return resultList;
	}

	/**
	 * Search for records having index value within the range minVal and maxVal.
	 * 
	 * @param index
	 *            the index tree to search.
	 * @param minVal
	 *            minimum value of the range (inclusive).
	 * @param maxVal
	 *            maximum value of the range (inclusive).
	 * @return list of MovieRecords with index key in the specified range (both inclusive).
	 */
	@Override
	public List<MovieRecord> rangeSearch(String index, String minVal, 
			String maxVal) {
		// TODO
		// Create a list to store the value within the range
		List<MovieRecord> resultList = new ArrayList<MovieRecord>();

		// Add value into the list if it is in the range

		for(int i = 0;i < movieRecordList.size();i ++){
			if(movieRecordList.get(i).getValByAttribute(index).
					compareToIgnoreCase(minVal) >= 0
					&& movieRecordList.get(i).getValByAttribute(index).
					compareToIgnoreCase(maxVal) <= 0){
				resultList.add(movieRecordList.get(i));
				//get the records in the list within the range
			}
		}

		return resultList;
	}

	/**
	 * Returns a sorted list of keys - index defines which key we want to sort on.
	 * Hint: You can define a Comparator to compare two MovieRecords based on
	 * specified index (e.g., director). You can then use this comparator class
	 * to directly sort using List.sort method.
	 * 
	 * @param index the index to which sort on.
	 * @return Sorted list of key values. 
	 * E.g., [..., "Christopher Nolan", ..., "James Cameron", ...] for director as index.
	 */
	@Override
	public List<String> allSortedKeys(String index) {
		// TODO
		// Create a list to store the key values

		ArrayList<String> temp = new ArrayList<String>(movieRecordList.size()); 
		// Add key values to the list

		for(int i = 0;i < movieRecordList.size();i ++){
			temp.add(movieRecordList.get(i).getValByAttribute(index));
			//get every node in the arraylist
		}
		Collections.sort(temp);// Sort the list
		/*ArrayList<String> result = new ArrayList<String>();
		// Create a new list as returned list
		for(int i = 0;i < temp.size();i ++){
			// Remove duplicates by comparing an element previous one
			// Copy the elements that are not duplicates into the result list
			if(i == 0){
				result.add(temp.get(0));
			}
			else{
				if(!(temp.get(i).equals(temp.get(i - 1)))){
					result.add(temp.get(i));
				}
			}
		}*/
        
		return temp;
	}
}