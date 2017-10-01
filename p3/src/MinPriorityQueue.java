///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  FrequencyParser.java
// File:             MinPriorityQueue.java
// Semester:         CS367 Fall 2016
//
// Author:           Jiayue Sheng
// CS Login:         jsheng
// Lecturer's Name:  Deb Deppeler
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     Conrad Chen
// Email:            wchen283@wisc.edu
// CS Login:         conradc
// Lecturer's Name:  Deb Deppeler
//


public class MinPriorityQueue implements MinPriorityQueueADT {

	private HuffmanNode[] huffmanList;
	//the array to store elements for minPriortyQueue
	private int numItems = 0;//record the number of ascii chars
	public MinPriorityQueue(){//constructor
		numItems=0;
		huffmanList= new HuffmanNode[129];
	}
	/**
	 * Removes the minimum element from the Priority Queue, and returns it.
	 *
	 * @return the minimum element in the queue, according to the compareTo()
	 * method of HuffmanNode.
	 * @throws PriorityQueueEmptyException if the priority queue has no elements
	 * in it
	 */
	@Override
	public HuffmanNode removeMin() throws PriorityQueueEmptyException {
		// TODO Auto-generated method stub

		// If the array is empty, throw a PriorityQueueEmptyException
		if (isEmpty()) {
			throw new PriorityQueueEmptyException();
		}

		int pos=1;//record the position of the element being shifted

		// Switch the positon of the largest element and the smallest element
		// and remove the smallest element.
		HuffmanNode removed = huffmanList[1];
		huffmanList[1] = huffmanList[numItems];
		huffmanList[numItems] = null;
		numItems --;

		// Sort the array to right order after removing
		boolean done = false;	
		
		while(! done ){
			// Compare parent with its left child if it has one
			if(pos * 2 + 1 <= numItems &&
					(( huffmanList[pos].compareTo(huffmanList[pos*2]) > 0)||
							(huffmanList[pos].compareTo(huffmanList[pos*2 + 1])
									> 0))){
				if(huffmanList[pos * 2 + 1].compareTo(huffmanList[pos *2]) < 0){
					// Compare parent with its right child if it has one
					// If parent is larger right child, switch their position
					HuffmanNode temp = huffmanList[pos];
					huffmanList[pos] = huffmanList[pos * 2 + 1];
					huffmanList[pos * 2 + 1] = temp;
					pos = pos * 2 + 1;
				}
				else {//If parent is larger than left child, switch position
					HuffmanNode temp2 = huffmanList[pos];
					huffmanList[pos] = huffmanList[pos * 2];
					huffmanList[pos * 2] = temp2;
					pos = pos * 2;
				}

			}
			else if(pos * 2 <= numItems){
				//if its right child is null but has a left child
				if(huffmanList[pos].compareTo(huffmanList[pos * 2]) > 0){
					HuffmanNode temp3 = huffmanList[pos];
					huffmanList[pos] = huffmanList[pos * 2];
					huffmanList[pos * 2] = temp3;
					pos = pos*2;
				}
				//if parent is smaller than its left child
				else done = true;
			}
			//If parent is smaller than both of its child, 
			//it is in the right position
			else {
				done = true;
			}
		}

		return removed;
	}


	/**
	 * Inserts a HuffmanNode into the queue, making sure to keep the shape and
	 * order properties intact.
	 *
	 * @param hn the HuffmanNode to insert
	 * @throws PriorityQueueFullException if the priority queue is full.
	 */
	@Override
	public void insert(HuffmanNode hn) throws PriorityQueueFullException {
		// TODO Auto-generated method stub

		// If the HuffmanNode to be added is empty, throw an IllegalArgumentException
		if(hn == null){
			throw new IllegalArgumentException();
		}

		// If the list is full, throw a PriorityQueueFullException
		if(numItems== huffmanList.length-1){
			throw new PriorityQueueFullException();
		}

		// Add the HuffmanNode to the last position of the list
		huffmanList[numItems + 1] = hn;
		numItems ++;

		// Find the correct position for the newly add HuffmanNode by 
		// comparing its frequency with its parent's frequency
		int i = numItems;//record the position of newly added node

		while(i!=1){
			if(huffmanList[i / 2].compareTo(hn) < 0) break;
			//if it is larger than its parent, done
			else{
				//if it is not larger than its parent, switch position
				HuffmanNode temp1 = huffmanList[i / 2];
				huffmanList[i / 2] = hn;
				huffmanList[i] = temp1;
				i = i / 2;//update the position 
			}
		}
	}


	/**
	 * Checks if the queue is empty.
	 * e.g. 
	 * 
	 * <pre>
	 * {@code
	 * m = new MinPriorityQueue(); 
	 * // m.isEmpty(): true
	 * m.insert(HuffmanNode hn);
	 * // m.isEmpty(): false
	 * m.remove();
	 * // m.isEmpty(): true
	 * }
	 * </pre>
	 *
	 * @return true, if it is empty; false otherwise
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub

		return numItems == 0;
	}

}
