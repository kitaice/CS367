///////////////////////////////////////////////////////////////////////////////
// Main Class File:  CacheImageApp.java
// Files:            PacketLinkedList.java
// Semester:         CS367 Fall 2016
//
// Author:           Jiayue Sheng
// Email:            jsheng7@wisc.edu
// CS Login:         jsheng
// Lecturer's Name:  Deb Deppeler
// Lecture Number:   001
//
///////////////////////////////////////////////////////////////////////////////
//
// Pair Partner:     Conrad Chen
// Email:            wchen283@wisc.edu
// CS Login:         conradc
// Lecturer's Name:  Deb Deppeler
// Lecture Number:   002
//
/////////////////////////////////////////////////////////////////////////////


import java.util.Iterator;

/**
 * A Single-linked linkedlist with a "dumb" header node (no data in the node),
 * but without a tail node. It implements ListADT&lt;E&gt; and returns
 * PacketLinkedListIterator when requiring a iterator.
 * 
 * @author honghui
 */
public class PacketLinkedList<E> implements ListADT<E> {
	// TODO: add your fields here
	private Listnode<E> head;
	private int numItems;

	/**
	 * Constructs a empty PacketLinkedList
	 */
	public PacketLinkedList() {
		// TODO
		//conctructor
		head = new Listnode<E>(null);
		numItems = 0;
	}

	/**
	 * Adds item to the end of the List.
	 * 
	 * @param item
	 *            the item to add
	 * @throws IllegalArgumentException
	 *             if item is null
	 */
	@Override
	public void add(E item) {
		// TODO
		if(null == item){
			throw new IllegalArgumentException();
			//throw exceptioin if the item trying to add is null
		}
				
				Listnode<E>curr=head;
				//traverse through the list
				while(curr.getNext()!= null){
					curr = curr.getNext();
				}
				curr.setNext(new Listnode<E>(item));
				//add at the end of the list
				
			
			numItems ++;//increment counter
	}

	@Override
	/**
	 * Adds item at position pos in the List, moving the items originally in
	 * positions pos through size() - 1 one place to the right to make room.
	 * 
	 * @param pos
	 *            the position at which to add the item
	 * @param item
	 *            the item to add
	 * @throws IllegalArgumentException
	 *             if item is null
	 * @throws IndexOutOfBoundsException
	 *             if pos is less than 0 or greater than size(), please check
	 *             this exception before IllegalArgumentException.
	 */
	public void add(int pos, E item) {
		// TODO
		if(null == item){
			throw new IllegalArgumentException();
		}//throw exceptioin if the item trying to add is null
		if(pos < 0 || pos > numItems){
			throw new IndexOutOfBoundsException();
		}//check if pos is out of bound
		
			Listnode<E> newNode=new Listnode<E> (item,head);
			
	
			Listnode<E> curr = head;
			for(int k = 0;k < pos;k ++){
				curr = curr.getNext();
			}

			newNode.setNext(curr.getNext());//set the next node of newNode
			curr.setNext(newNode);//set the next node of current node
		
		numItems ++;//update number of items
	}

	@Override
	/**
	 * Returns true iff item is in the List (i.e., there is an item x in the
	 * List such that x.equals(item))
	 * 
	 * @param item
	 *            the item to check
	 * @return true if item is in the List, false otherwise
	 */
	public boolean contains(E item) {
		// TODO
		Listnode<E> curr = head;
		Listnode<E> temp = new Listnode<E>(item);
		
		while(curr != null){
			if(temp == curr){
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Returns the item at position pos in the List.
	 * 
	 * @param pos
	 *            the position of the item to return
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException
	 *             if pos is less than 0 or greater than or equal to size()
	 */
	@Override
	public E get(int pos) {
		// TODO
		if(pos < 0 || pos > numItems){
			throw new IndexOutOfBoundsException();
		}//check if pos is out of bound
		Listnode<E> curr = head;
		E temp;
		
		for(int i = 0;i < pos+1;i ++){
			curr = curr.getNext();//traverse to the position
		}
		temp = curr.getData();//get data
		
		return temp;
	}

	@Override
	public boolean isEmpty() {
		// TODO
		if(head == null){
			return true;
		}
		
		return false;
	}

	/**
	 * Removes and returns the item at position pos in the List, moving the
	 * items originally in positions pos+1 through size() - 1 one place to the
	 * left to fill in the gap.
	 * 
	 * @param pos
	 *            the position at which to remove the item
	 * @return the item at position pos
	 * @throws IndexOutOfBoundsException
	 *             if pos is less than 0 or greater than or equal to size()
	 */
	@Override
	public E remove(int pos) {
		// TODO
		 if(pos < 0 || pos >= numItems) return null;
			
		Listnode<E> curr = head;

			for(int i = 0;i < pos ;i ++){
				curr = curr.getNext();
			}//get to the position
			E remove=curr.getNext().getData();//get the item
			curr.setNext(curr.getNext().getNext());//remove the item
			numItems --;//update number of items
			return remove;

		
	}

	/**
	 * Returns the number of items in the List.
	 * 
	 * @return the number of items in the List
	 */
	@Override
	public int size() {
		// TODO
		return numItems;
	}

	@Override
	public PacketLinkedListIterator<E> iterator() {
		// TODO
		PacketLinkedListIterator <E> curr = new PacketLinkedListIterator <E>(head);
		
		return curr;
	}

} // End of PacketLinkedList class
