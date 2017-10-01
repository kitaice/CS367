///////////////////////////////////////////////////////////////////////////////
// Main Class File:  CacheImageApp.java
// File Name:        PacketLinkedListIterator.java    
// Semester:         CS367 Fall 2016
//
// Author:           Jiayue Sheng
// Email:            jsheng7@wisc.edu
// CS Login:         jsheng
// Lecturer's Name:  Deb Deppeler
//
///////////////////////////////////////////////////////////////////////////////
//
// Pair Partner:     Conrad Chen
// Email:            wchen283@wisc.edu
// CS Login:         conradc
// Lecturer's Name:  Deb Deppeler
//
/////////////////////////////////////////////////////////////////////////////


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The iterator implementation for PacketLinkedList.
 * 
 * @author honghui
 */
public class PacketLinkedListIterator<T> implements Iterator<T> {
	private Listnode<T> curr;

	/**
	 * Constructs a PacketLinkedListIterator by passing a head node of
	 * PacketLinkedList.
	 * 
	 * @param head
	 *            the head node of PacketLinkedList.
	 */
	public PacketLinkedListIterator(Listnode<T> head) {
		curr = head.getNext();//constructor
	}

	/**
	 * Returns the next element in the iteration.
	 * 
	 * @return the next element in the iteration
	 * @throws NoSuchElementException
	 *             if the iteration has no more elements
	 */
	@Override
	public T next() {
		// TODO
		
		if(curr == null){
			throw new NoSuchElementException();
			//throw exception when there is no next element
		}
		
		T item = curr.getData();//get data at present position
		curr = curr.getNext();//go to next element
		
		return item;
	}

	/**
	 * Returns true if the iteration has more elements.
	 * 
	 * @return true if the iteration has more elements
	 */
	@Override
	public boolean hasNext() {
		// TODO
		return curr != null;//if there is no more element in the list
	}

	/**
	 * The remove operation is not supported by this iterator
	 * 
	 * @throws UnsupportedOperationException
	 *             if the remove operation is not supported by this iterator
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
