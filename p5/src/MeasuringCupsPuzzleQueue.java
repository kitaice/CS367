///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MeasuringCupsSovler.java
//File:             MeasuringCupsPuzzleQueue.java
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
import java.util.LinkedList;
import java.util.Queue;

/**
 * A queue of MeasuringCupsPuzzleState nodes
 */
public class MeasuringCupsPuzzleQueue implements MeasuringCupsPuzzleADT {

	Queue<MeasuringCupsPuzzleState> queue;

	/**
	 * Construct a new queue
	 */
	public MeasuringCupsPuzzleQueue() {
		this.queue = new LinkedList<MeasuringCupsPuzzleState>();
	}

	/**
	 * Add a node to the queue
	 * 
	 * @param state
	 *            the node to add
	 */
	@Override
	public void add(MeasuringCupsPuzzleState state) {
		// TODO
		queue.add(state);
		//add state into the queue of the state
	}

	/**
	 * Remove the last (FIFO) node from the queue
	 * 
	 * @return the least recent node that has been inserted into the queue;
	 *         which is now removed from the queue as a result of this function
	 *         call
	 */
	@Override
	public MeasuringCupsPuzzleState remove() {
		// TODO
		return queue.remove();
		
		//remove the first inserted state from the state queue
	}

	/**
	 * @return true if the queue is empty and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		// TODO
		return queue.isEmpty();
		//retirm true if the queue is empty
	}

	/**
	 * Update the queue by removing all of its members.
	 */
	@Override
	public void clear() {
		this.queue.clear();
	}

	/**
	 * @return a String representation of the queue by visiting each member in
	 *         FIFO order, calling its toString, and joining the resulting
	 *         String to the return string by separating member Strings with a
	 *         space character
	 */
	public String toString() {
		Iterator<MeasuringCupsPuzzleState> queueIterator = this.queue.iterator();
		String result = "";
		while (queueIterator.hasNext()) {
			result += queueIterator.next().toString();
			if (queueIterator.hasNext()) {
				result += " ";
			}
		}
		return result;
	}
}
