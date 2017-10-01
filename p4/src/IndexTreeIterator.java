///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MovieDbMain.java
//File:             IndexTreeIterator.java
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
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The Iterator for IndexTree that is built using Java's Stack class. This
 * iterator steps through the IndexTree using an INORDER traversal.
 *
 * @author apul
 */
public class IndexTreeIterator<K extends Comparable<K>, V> implements
Iterator<IndexTreeNode<K, V>> {

	// Stack to track where the iterator is in the tree.
	Stack<IndexTreeNode<K, V>> stack;

	/**
	 * Constructs the iterator so that it is initially at the smallest value in
	 * the set. Hint: Go to farthest left node and push each node onto the stack
	 * while stepping down the IndexTree to get there.
	 *
	 * @param node
	 *            the root node of the IndexTree
	 */
	public IndexTreeIterator(IndexTreeNode<K, V> node) {
		// TODO
		stack=new Stack<IndexTreeNode<K, V>>();
		while(node!=null){
			stack.push(node);
			node=node.getLeftChild();
		}
		//this stack used to store all left child at leftmost subtree
	}

	/**
	 * Returns true iff the iterator has more items.
	 *
	 * @return true iff the iterator has more items
	 */
	public boolean hasNext() {
		// TODO
		return !stack.isEmpty();//return true iff iterator has any items
	}


	/**
	 * Returns the next node in the iteration.
	 *
	 * @return the next item in the iteration
	 * @throws NoSuchElementException
	 *             if the iterator has no more items.
	 */
	public IndexTreeNode<K, V> next() {
		// TODO

		if(stack.isEmpty()) throw new NoSuchElementException();
		//throw exception if stack is empty
		IndexTreeNode<K,V> node=stack.pop();//pop up a left child
		IndexTreeNode<K,V> result=node;
		IndexTreeNode<K,V> child=node.getRightChild();
		//take all right children of this left child

		while(child!=null){
			stack.push(child);
			child=child.getLeftChild();
			//take the left child of this right child
		}
		return result;
	}

	/**
	 * Not Supported
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
