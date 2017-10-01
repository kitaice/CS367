///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MeasuringCupsSolver.java
//File:             MeasuringCupsPuzzleStack.java
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
import java.util.Stack;

/**
 * A stack of MeasuringCupsPuzzleState nodes
 */
public class MeasuringCupsPuzzleStack implements MeasuringCupsPuzzleADT {

	private Stack<MeasuringCupsPuzzleState> stack;

	/**
	 * Construct a new stack
	 */
	public MeasuringCupsPuzzleStack() {
		this.stack = new Stack<MeasuringCupsPuzzleState>();
	}

	/**
	 * @param state
	 *            the node to add to the stack
	 */
	@Override
	public void add(MeasuringCupsPuzzleState state) {
		// TODO
		stack.push(state);//push the state into the state stack
	}

	/**
	 * @return the node that was inserted into the stack most recently, which
	 *         has been removed from the stack as a result of this function call
	 */
	@Override
	public MeasuringCupsPuzzleState remove() {
		// TODO
		return stack.pop();
		//remove the last pushed state from the state stack
	}

	/**
	 * @return true if this stack is empty and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		// TODO
		return stack.isEmpty();
		//return true if the state stack is empty
	}

	/**
	 * Update the stack by removing all of the nodes in it
	 */
	@Override
	public void clear() {
		this.stack.clear();
	}

	/**
	 * @return a string representing the stack by calling each node/member's
	 *         toString (in LIFO order) and joining the resulting strings with a
	 *         space character as a delimiter
	 */
	public String toString() {
		Iterator<MeasuringCupsPuzzleState> stackIterator = this.stack.iterator();
		String result = "";
		while (stackIterator.hasNext()) {
			result += stackIterator.next().toString();
			if (stackIterator.hasNext()) {
				result += " ";
			}
		}
		return result;
	}

}
