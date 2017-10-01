///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MovieDbMain.java
//File:             IndexTree.java
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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Generic IndexTree implementation where each node is identified by a key and
 * can have a list of values i.e duplicate keys are allowed.
 * 
 * For example: You can insert &lt;K1, V1&gt; and &lt;K1, V2&gt; in the IndexTree.
 * After you insert, the node corresponding to key K1 will have a list of data
 * values [V1, V2].
 *
 * @author CS367
 */
public class IndexTree<K extends Comparable<K>, V> implements
IndexTreeADT<K, V>, Iterable<IndexTreeNode<K, List<V>>> {

	// Root node.
	private IndexTreeNode<K, List<V>> root;

	/**
	 * Constructs a IndexTree and initializes the root node.
	 */
	public IndexTree() {
		root = null;
	}

	/**
	 * Returns iterator with respect to the root node.
	 * 
	 * @return the iterator for the IndexTree.
	 */
	public Iterator<IndexTreeNode<K, List<V>>> iterator() {
		return new IndexTreeIterator<K, List<V>>(root);
	}

	/**
	 * Search for the node with key equals to input key. Hint: Call
	 * a search helper method to recursively traverse the tree.
	 *
	 * @param key
	 *            the key to search.
	 * @return data value in the tree for the corresponding key.
	 * @throws IllegalArgumentException
	 *             if key is null.
	 * @throws KeyNotFoundException
	 * 				if key is not found in the tree.
	 */	
	public List<V> search(K key) throws KeyNotFoundException,
	IllegalArgumentException {
		// TODO
		// 1. Check for null key and throw IllegalArgumentException() exception.
		// 2. Call Search helper with root and key to be searched.    	
		// 3. Throw KeyNotFoundException() if key is not found.
		if(key == null){
			throw new IllegalArgumentException();
		}// Check for null key and throw IllegalArgumentException() exception.

		return  search(root,key);
		// Call Search helper with root and key to be searched.

	}

	/**
	 * Search helper for the node with key equals to input key.
	 *
	 * @param key
	 *            the key to search.
	 * @param node 
	 * 			  the node to start the research           
	 * @return data value in the tree for the corresponding key.
	 * @throws KeyNotFoundException
	 * 				if key is not found in the tree.
	 */	

	private List<V> search(IndexTreeNode<K, List<V>> node, K key)
			throws KeyNotFoundException{
		//TODO 

		if (node == null) {
			throw new KeyNotFoundException();
		}// Throw KeyNotFoundException() if key is not found.

		if(node.getKey().equals(key)){
			return node.getData();
		}
		// If there exists a node with the same key value with the input
		// return the node's data
		if (key.compareTo(node.getKey()) < 0) {
			// key < this node's key; look in left subtree
			return search(node.getLeftChild(), key);
		}

		else {
			// key > this node's key; look in right subtree
			return search(node.getRightChild(), key);
		}

	}
	/**
	 * Inserts a (key, value) pair into the IndexTree. This will call a recursive
	 * method with root node and (key, value) to be inserted in the IndexTree.
	 * 
	 * @param key
	 *            key of the new data to be inserted.
	 * @param value
	 *            data to be inserted.
	 * @throws IllegalArgumentException
	 *             if key or value is null.
	 */
	public void insert(K key, V value) {
		// TODO
		if(key == null || value == null){
			throw new IllegalArgumentException();
		}//Throw an IllegalArgumentException if theinput key or value is null

		root = insert(root, key, value);
		// Insert the node with the input key and value
	}

	/** 
	 * Recursive helper method to find the position and insert a key and value
	 * into the IndexTree. 
	 * 
	 * NOTE: STUDENTS MUST IMPLEMENT insert recursively, 
	 * but you may define your own recursive helper method instead of
	 * defining and using this method. 
	 * 
	 * @param node
	 *            node is the recursive parameter with initial value being root
	 *            of the IndexTree.
	 * @param key
	 *            key of the new data to be inserted.
	 * @param value
	 *            data to be inserted.
	 */	
	private IndexTreeNode<K, List<V>> insert(IndexTreeNode<K, List<V>> node, 
			K key, V value) {
		// TODO
		// 1. Check if node is null. If so, create a new IndexTreeNode<K,List<V>>.
		//    a.  create an ArrayList that stores type V items
		//    b.  add value to that array list
		//    c.  create the IndexTreeNode with the specified key and the list 
		//    d.  return the newly created node

		if(node == null){
			ArrayList<V> resultList = new ArrayList<V>();
			resultList.add(value);
			// Add value to that array list
			IndexTreeNode<K,List<V>> resultNode = 
					new IndexTreeNode<K,List<V>>(key,resultList);
			//create the IndexTreeNode with the specified key and the list 
			return resultNode;
			// Return the newly created node

		}


		// TODO
		// 2. If node is not null, compare key with current node's key. 
		// There are 3 cases:
		// 	a. If key = current node's key, 
		//            append value to the data list of node.
		//            and return node
		//		
		//  b. If key < current node's key, 
		//            return the result of inserting into "left subtree"
		//
		//  c. If key > current node's key, 
		//            return the result of inserting into "right subtree"

		// Remove this after you write the program.

		// If node is not null, compare key with current node's key.

		if(node.getKey().equals(key)){
			node.getData().add(value);
			return node;

		}
		// If key = current node's key
		else if(key.compareTo(node.getKey()) < 0){
			//node = insert(node.getLeftChild(),key,value);
			node.setLeftChild(insert(node.getLeftChild(),key,value));
			return node;
		}
		else {
			//node = insert(node.getRightChild(),key,value);
			node.setRightChild(insert(node.getRightChild(),key,value));
			return node;
		}


	}
	/**
	 * Returns a list of data values which have keys in the specified range
	 * (inclusive of minValue and maxValue). Hint: This must be done recursively
	 * using a range search helper method and call it with root.
	 * Note: Range values are always compared lexicographically For example,
	 * "15" &lt; "7" lexicographically.
	 *
	 * @param minValue
	 *            the minimum value of the desired range (inclusive).
	 * @param maxValue
	 *            the maximum value of the desired range (inclusive).
	 * @return list of data values having key in the specified range.
	 * @throws IllegalArgumentException
	 *             if either minValue or maxValue is null.
	 */	
	public List<V> rangeSearch(K minValue, K maxValue) {
		// TODO check for IllegalArgumentException
		if(minValue == null || maxValue == null){
			throw new IllegalArgumentException();
		}

		// Ensure min is less than max
		if ( minValue.compareTo(maxValue) > 0 ) {
			K t = minValue;
			minValue = maxValue;
			maxValue = t;
		}

		// TODO call the helper method
		
		List<V> resultList2 = new ArrayList<V>();
				rangeSearch(root,minValue,maxValue,resultList2);
		
		return resultList2;
	}
	/**
	 * Recursive helper method to search for the key value with in
	 * range of maxValue and minValue
	 * 
	 * @param node
	 *            node is the recursive parameter with initial value being root
	 *            of the IndexTree.
	 * @param minValue
	 *            the minimum value of the desired range (inclusive).
	 * @param maxValue
	 *            the maximum value of the desired range (inclusive).
	 * @return a list with values within the range
	 */

	private List<V> rangeSearch(IndexTreeNode<K, List<V>> node,
			K minValue,K maxValue,List<V> resultList){
		//List<V> resultList2 = new ArrayList<V>();
		if (node==null){
			return new ArrayList<V>();
		}
		// If the node is null, return null

		if(node.getKey().compareTo(minValue) >= 0 
				&& node.getKey().compareTo(maxValue) <= 0){
			// If the node's key lies within the range

			for(int i = 0;i < node.getData().size();i++){
				resultList.add(node.getData().get(i));
				// Add the value in the node to the returned list

			}
			// Check the node's left and right children's value 
			rangeSearch(node.getLeftChild(), minValue, maxValue,resultList);
			rangeSearch(node.getRightChild(), minValue, maxValue,resultList);
			return resultList;
		}

		else if(node.getKey().compareTo(minValue) < 0 ){
			return rangeSearch(node.getRightChild(), 
					minValue, maxValue,resultList);
		} 
		// If the node's key is larger than the minValue
				// Search the node's right subtree

		else return rangeSearch(node.getLeftChild(),
				minValue,maxValue,resultList);
		// If the node's key is smaller than the maxValue
				// Search the node's left subtree



	}

	/**
	 * Returns the number of nodes in the tree. This must be done recursively
	 * using the helper method to get the number of nodes.
	 * 
	 *  @return number of nodes in the tree.
	 */
	public int size() {
		// TODO
		return size(root);
	}
	/**
	 * Recursive helper method to calculate the size of the tree
	 * 
	 * @param node
	 *            node is the recursive parameter with initial value being root
	 *            of the IndexTree.
	 * @return the size of the tree
	 */

	private int size(IndexTreeNode<K, List<V>> node){

		if(node == null){
			return 0;
		}// If the node is null, return 0


		return size(node.getLeftChild()) + size(node.getRightChild()) + 1;
		// Return the sum of the 1 and 
				// the size of the node's left subtree and right subtree

	}

	/**
	 * Returns height of the tree. Hint: Use a recursive helper method
	 * and call it with root node to calculate the height.
	 *
	 * @return the height of the tree.
	 */
	public int getHeight() {
		// TODO
		// Return the height of the root node.
		return getHeight(root);
	}

	/**
	 * Recursive helper method to calculate the height of the tree
	 * 
	 * @param node
	 *            node is the recursive parameter with initial value being root
	 *            of the IndexTree.
	 * @return the height of the tree
	 */

	private int getHeight(IndexTreeNode<K, List<V>> node){

		int heightLeft = 0;
		int heightRight = 0;
		// Calculate the height of the node's left subtree and right subtree

		if(node.getLeftChild()!=null)
			heightLeft = getHeight(node.getLeftChild());
		if(node.getRightChild()!=null)
			heightRight = getHeight(node.getRightChild());
		if(heightLeft > heightRight){
			return heightLeft+1;
			// If the height of left subtree is larger than right subtree
			// Add one to heightLeft

		}
		else{
			return heightRight+1;
			// If the height of right subtree is larger than left subtree
			// Add one to heightRight

		}
	}

	/**
	 * Returns total number of data values in the tree.
	 * Hint: Call a recursive helper method to recursively count the nodes.
	 * 
	 * @return the total data count (values of all data values in the tree).
	 */
	public int getTotalDataCount() {
		// TODO
		return getTotalDataCount(root);
	}
	/**
	 * Recursive helper method to calculate the total number of data of the tree
	 * 
	 * @param node
	 *            node is the recursive parameter with initial value being root
	 *            of the IndexTree.
	 * @return the total number of data of the tree
	 */

	private int getTotalDataCount(IndexTreeNode<K, List<V>> node){
		if(node == null){
			return 0;
		}// If the node is null, return 0


		// If the node is not null, return the number of data in the node 
		// and the number of data in the node's left child and right child 

		return node.getData().size() + getTotalDataCount(node.getLeftChild()) +
				getTotalDataCount(node.getRightChild());
	}

	/**
	 * Returns average number of data values per node (E.g., Node with key "key"
	 * and list of values List&lt;V&gt; = {v1, v2, v3} has number of data values as 3)
	 * rounded to 3 decimal places.
	 * Hint: Use getTotalDataCount() and size().
	 * 
	 * @return the average data count.
	 */
	public double getAvgDataCount() {
		// TODO
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		return Double.parseDouble(df.format
				((double) (getTotalDataCount()) / size()));
	}

	/**
	 * Displays the top maxNumLevels of the tree. DO NOT CHANGE IT!
	 * You can use this method to debug your code.
	 *
	 * @param maxNumLevels
	 *            from the top of the IndexTree that will be displayed.
	 */
	public void displayTree(int maxNumLevels) {
		System.out.println("---------------------------" +
				"IndexTree Display--------------------------------");
		displayTree(root, 0, maxNumLevels);
	}

	/**
	 * Recursive helper function to display the top levels of the IndexTree.
	 * 
	 * @param node
	 *            initial value being root of IndexTree.
	 * @param curLevel
	 *            initial value 0.
	 * @param maxNumLevels
	 *            initial value being the number of levels of the tree to be
	 *            displayed.
	 */
	private void displayTree(IndexTreeNode<K, List<V>> node, int curLevel,
			int maxNumLevels) {
		if (maxNumLevels <= curLevel)
			return;
		if (node == null)
			return;
		for (int i = 0; i < curLevel; i++) {
			System.out.print("|--");
		}
		System.out.println(node.getKey());
		displayTree(node.getLeftChild(), curLevel + 1, maxNumLevels);
		displayTree(node.getRightChild(), curLevel + 1, maxNumLevels);
	}

}
