///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MovieDbMain.java
//File:             IndexTreeTester.java
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
import java.util.Iterator;
import java.util.List;

/**
 * Tester implementation using IndexTree as the data structure.
 *
 * DO NOT MODIFY THIS CLASS.
 *
 * @author apul
 */

public class IndexTreeTester implements TesterADT<String, MovieRecord> {

	// MovieDb object which creates IndexTrees and provides an interface to
	// query the IndexTree for given index (e.g., director).
	private MovieDb movieDb;
	
	public IndexTreeTester(List<MovieRecord> movieRecords) {
		movieDb = new MovieDb(movieRecords);	
	}
	
	/**
	 * Returns a list of data values having index key equal to input key.
	 * For example, search for MovieRecords with director (index) =
	 * James Cameron (key). Hint: Use movieDb methods. 
	 * 
	 * @param index
	 *            index (attribute) of the movie record by which search is to be
	 *            done.
	 * @param key
	 *            key to be searched.
	 * @return list of movie records that match the key.
	 */
	@Override
	public List<MovieRecord> searchByKey(String index, String key) {
		// TODO
		//return the records that match the key
		return movieDb.getIndexTree(index).search(key);
	}

	/**
	 * Returns a list of data values having index key in the specified range.
	 * For example, search for MovieRecords with 2015 &gt;= releaseYear (index) &lt;= 2016.
	 * Hint: Use movieDb methods. 
	 * 
	 * @param index
	 *            attribute of the movie record by which search is to be done.
	 * @param minVal
	 *            lower bound of the range search.
	 * @param maxVal
	 *            upper bound of the range search.
	 * @return list of movie records that fall in the given range.
	 */
	@Override
	public List<MovieRecord> rangeSearch(String index, String minVal,
			String maxVal) {
		// TODO
		//return list of movie records that fall in the given range.
		return movieDb.getIndexTree(index).rangeSearch(minVal, maxVal);
	}

	/**
	 * Returns a sorted list of keys - index defines which key we want to sort
	 * on. Hint: Use IndexTreeIterator to do in-order traversal.
	 * 
	 * @param index
	 *            the index to which sort on.
	 * @return list of index values sorted in lexicographically increasing order.
	 * E.g., [..., "Christopher Nolan", ..., "James Cameron", ...] for director
	 * as index.
	 */
	@Override
	public List<String> allSortedKeys(String index) {
		// TODO
		List<String> result = new ArrayList<String>();
		// the list that stores the keys

		Iterator<IndexTreeNode<String, List<MovieRecord>>> itr 
		=movieDb.getIndexTree(index).iterator();
		// Add the key of the nodes to the list
	
		while(itr.hasNext()){
			result.add(itr.next().getKey());//add the key into the result list
		}
		Collections.sort(result);
		// Sort and return the result list
		return result;
	}

}
