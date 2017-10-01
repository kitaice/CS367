///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  MeasuringCupsSolver.java
// File:             MeasuringCupsPuzzle.java
// Semester:         CS 367	Spring 2016
//
// Author:           Jiayue Sheng
// CS Login:         jsheng
// Lecturer's Name:  Deb Deppeler
// Lab Section:      Lec 001
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ////////////////////
//
// Pair Partner:     (name of your pair programming partner)
// Email:            (email address of your programming partner)
// CS Login:         (partner's login name)
// Lecturer's Name:  (name of your partner's lecturer)
// Lab Section:      (your partner's lab section number)
//


import java.util.Iterator;

/**
 * A class describing the measuring cups puzzle with a startState
 * {@link MeasuringCupsPuzzleState} and a goalState
 * {@link MeasuringCupsPuzzleState}
 */
public class MeasuringCupsPuzzle {

	private MeasuringCupsPuzzleState startState;
	private MeasuringCupsPuzzleState goalState;
	private MeasuringCupsPuzzleADT measuringCupsPuzzleADT;
	private MeasuringCupsPuzzleStateList pathFromStartToGoal;
	private MeasuringCupsPuzzleStateList processedStates;
	private MeasuringCupsPuzzleState foundGoalState;

	/**
	 * Construct a puzzle object by describing the startState and goalState
	 *
	 * @param startState
	 *            a state describing the capacities and initial volumes of
	 *            measuring cups {@link MeasuringCupsPuzzleState}
	 * @param goalState
	 *            a state describing the desired end volumes of measuring cups
	 *            {@link MeasuringCupsPuzzleState}
	 */
	public MeasuringCupsPuzzle(MeasuringCupsPuzzleState startState, MeasuringCupsPuzzleState goalState) {
		this.startState = startState;
		this.goalState = goalState;

		this.pathFromStartToGoal = new MeasuringCupsPuzzleStateList();
		this.processedStates = new MeasuringCupsPuzzleStateList();
		this.foundGoalState = null;
		this.measuringCupsPuzzleADT = null;
	}

	/**
	 * Solve the measuring cups puzzle if it can be solved. Set processedStates
	 * by adding a {@link MeasuringCupsPuzzleState} graph node to the list as
	 * the algorithm visits that node. Set foundGoalState to a
	 * {@link MeasuringCupsPuzzleState} if the graph traversal algorithm labeled
	 * by *algorithm* visits a node with the same values as the desired
	 * goalState
	 *
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 * @return true if the puzzle can be solved (and has been solved, see
	 *         {@link retrievePath} to obtain the solution stored in this
	 *         object) and false otherwise
	 */
	public boolean findPathIfExists(String algorithm) {
		// TODO
		// Reset the puzzle 
		resetCupPuzzle();

		// Choose a method to find solution
		chooseADT(algorithm);

		measuringCupsPuzzleADT.add(startState);
		while(!measuringCupsPuzzleADT.isEmpty()){
			MeasuringCupsPuzzleState state = measuringCupsPuzzleADT.remove();

			if(!isProcessed(state)){
				// If the goal state can be reached, then the puzzle is solvable
				if(state.equals(goalState)){
					goalState = state;
					foundGoalState = state;
					return true;
				}
				// add all possible situations to the list
				for(MeasuringCupsPuzzleState successor:getSuccessors(state)){
					measuringCupsPuzzleADT.add(successor);
				}
				// Mark state as visited
				processedStates.add(state);
			}
		}
		return false;
	}

	/**
	 * Set member measuringCupsPuzzleADT {@link MeasuringCupsPuzzleADT} with a
	 * data type that will be used to solve the puzzle.
	 *
	 * @param algorithm
	 *            a String describing how the puzzle will be solved; has a value
	 *            equal to the project configuration {@link Config} BFS or DFS;
	 *            e.g. "BFS"
	 */
	private void chooseADT(String algorithm) {
		// TODO
		// Use a queue to find a solution if choose breadth first search
		if(algorithm.equals(Config.BFS)){
			measuringCupsPuzzleADT = new MeasuringCupsPuzzleQueue();
		}
		// Use a stack to find a solution if choose depth first search
		else{
			measuringCupsPuzzleADT = new MeasuringCupsPuzzleStack();
		}
	}

	/**
	 * Reset the puzzle by erasing all member variables which store some aspect
	 * of the solution (pathFromStartToGoal, processedStates, and
	 * foundGoalState) and setting them to their initial values
	 */
	private void resetCupPuzzle() {
		pathFromStartToGoal.clear();
		processedStates.clear();
		foundGoalState = null;
	}

	/**
	 * Mark the graph node represented by currentState as visited for the
	 * purpose of the graph traversal algorithm being used to solve the puzzle
	 * (set by {@link chooseADT})
	 *
	 * @param currentState
	 *            {@link MeasuringCupsPuzzleState}
	 * @return true if the currentState has been visited and false otherwise
	 */
	private boolean isProcessed(MeasuringCupsPuzzleState currentState) {
		// TODO
		Iterator<MeasuringCupsPuzzleState> itr = processedStates.iterator();

		// Find if currentState has been visited by iterating through processedState
		while(itr.hasNext()){
			MeasuringCupsPuzzleState state = itr.next();
			if(state.equals(currentState)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Assuming {@link findPathIfExists} returns true, return the solution that
	 * was found. Set pathFromStartToGoal by starting at the foundGoalState and
	 * accessing/setting the current node to the parentState
	 * {@link MeasuringCupsPuzzleState#getParentState} until reaching the
	 * startState
	 *
	 * @return a list of states {@link MeasuringCupsPuzzleStateList}
	 *         representing the changes in volume of cupA and cupB from the
	 *         initial state to the goal state.
	 */
	public MeasuringCupsPuzzleStateList retrievePath() {
		// TODO
		MeasuringCupsPuzzleState currentState = foundGoalState.getParentState();

		// Find a solution from the puzzle by starting from goal state
		pathFromStartToGoal.add(foundGoalState);

		// Find each state's parent state until start state is reached
		while(!currentState.equals(startState)){
			pathFromStartToGoal.add(currentState);
			currentState = currentState.getParentState();
		}

		pathFromStartToGoal.add(startState);
		// Reverse the path to start from start state
		pathFromStartToGoal.reverse();

		return pathFromStartToGoal;
	}

	/**
	 * Enumerate all possible states that can be reached from the currentState
	 *
	 * @param currentState
	 *            the current volumes of cupA and cupB
	 * @return a list of states {@link MeasuringCupsPuzzleStateList} that can be
	 *         reached by emptying cupA or cupB, pouring from cupA to cupB and
	 *         vice versa, and filling cupA or cupB to its max capacity
	 */
	public MeasuringCupsPuzzleStateList getSuccessors(MeasuringCupsPuzzleState currentState) {
		MeasuringCupsPuzzleStateList successors = new MeasuringCupsPuzzleStateList();

		if (currentState == null) {
			return successors;
		}
		// TODO
		// Fill CupA
		successors.add(fillCupA(currentState));

		// Fill CupB
		successors.add(fillCupB(currentState));
		// Empty CupA
		successors.add(emptyCupA(currentState));
		// Empty CupB
		successors.add(emptyCupB(currentState));
		// Pour from CupA to CupB
		successors.add(pourCupAToCupB(currentState));
		// Pour from CupB to CupA
		successors.add(pourCupBToCupA(currentState));

		// remove successors if same as currentState

		Iterator<MeasuringCupsPuzzleState> itr = successors.iterator();
		MeasuringCupsPuzzleStateList temp2 = new MeasuringCupsPuzzleStateList();

		// Iterate through successors and add states that are different from current state to 
		// a new list
		while(itr.hasNext()){
			MeasuringCupsPuzzleState temp = itr.next();
			if(!temp.equals(currentState)){
				temp2.add(temp);
			}
		}

		successors = temp2;
		return successors;
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupA to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupA(MeasuringCupsPuzzleState currentState) {
		// TODO
		Cup cupA1 = new Cup(currentState.getCupA().getCapacity(),
				currentState.getCupA().getCapacity());

		return new MeasuringCupsPuzzleState(cupA1,currentState.getCupB(),currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by filling cupB to its max
	 *         capacity
	 */
	public MeasuringCupsPuzzleState fillCupB(MeasuringCupsPuzzleState currentState) {
		// TODO
		Cup cupB1 = new Cup(currentState.getCupB().getCapacity(),
				currentState.getCupB().getCapacity());

		return new MeasuringCupsPuzzleState(currentState.getCupA(),cupB1,currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupA
	 */
	public MeasuringCupsPuzzleState emptyCupA(MeasuringCupsPuzzleState currentState) {
		// TODO
		Cup cupA1 = new Cup(currentState.getCupA().getCapacity(),0);

		return new MeasuringCupsPuzzleState(cupA1,currentState.getCupB(),currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState by emptying cupB
	 */
	public MeasuringCupsPuzzleState emptyCupB(MeasuringCupsPuzzleState currentState) {
		// TODO
		Cup cupB1 = new Cup(currentState.getCupB().getCapacity(),0);

		return new MeasuringCupsPuzzleState(currentState.getCupA(),cupB1,currentState);
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupA into cupB until either cupA is empty or cupB is full
	 */
	public MeasuringCupsPuzzleState pourCupAToCupB(MeasuringCupsPuzzleState currentState) {
		// TODO
		// If cupB cannot contain all of cupA's current amount
		if(currentState.getCupB().getCapacity() < 
				currentState.getCupA().getCurrentAmount() + 
				currentState.getCupB().getCurrentAmount())
		{
			Cup cupA1 = new Cup(currentState.getCupA().getCapacity(),
					currentState.getCupA().getCurrentAmount() - 
					(currentState.getCupB().getCapacity() - 
							currentState.getCupB().getCurrentAmount()));

			Cup cupB1 = new Cup(currentState.getCupB().getCapacity(),
					currentState.getCupB().getCapacity());

			return new MeasuringCupsPuzzleState(cupA1,cupB1,currentState);
		}

		// If cupB is still not full after pouring all current amount in cupA
		else if(currentState.getCupB().getCapacity() > 
		currentState.getCupA().getCurrentAmount() + 
		currentState.getCupB().getCurrentAmount()){

			Cup cupA2 = new Cup(currentState.getCupA().getCapacity(),0);

			Cup cupB2 = new Cup(currentState.getCupB().getCapacity(),
					currentState.getCupA().getCurrentAmount() + 
					currentState.getCupB().getCurrentAmount());
			return new MeasuringCupsPuzzleState(cupA2,cupB2,currentState); 
		} 

		// If cupB can just hold all of cupA's current amount
		Cup cupA3 = new Cup(currentState.getCupA().getCapacity(),0);

		Cup cupB3 = new Cup(currentState.getCupB().getCapacity(),
				currentState.getCupB().getCapacity());

		return new MeasuringCupsPuzzleState(cupA3,cupB3,currentState); 
	}

	/**
	 * @param currentState
	 * @return a new state obtained from currentState pouring the currentAmount
	 *         of cupB into cupA until either cupB is empty or cupA is full
	 */
	public MeasuringCupsPuzzleState pourCupBToCupA(MeasuringCupsPuzzleState currentState) {
		// TODO
		// If cupA cannot contain all of cupB's current amount
		if(currentState.getCupA().getCapacity() < 
				currentState.getCupB().getCurrentAmount() + 
				currentState.getCupA().getCurrentAmount())
		{
			Cup cupA1 = new Cup(currentState.getCupA().getCapacity(),
					currentState.getCupA().getCapacity());

			Cup cupB1 = new Cup(currentState.getCupB().getCapacity(),
					currentState.getCupB().getCurrentAmount() - 
					(currentState.getCupA().getCapacity() - 
							currentState.getCupA().getCurrentAmount()));
			return new MeasuringCupsPuzzleState(cupA1,cupB1,currentState);
		}

		// If cupA is still not full after pouring all current amount in cupB
		else if(currentState.getCupA().getCapacity() > 
		currentState.getCupB().getCurrentAmount() + 
		currentState.getCupA().getCurrentAmount()){

			Cup cupA2 = new Cup(currentState.getCupA().getCapacity(),
					currentState.getCupA().getCurrentAmount() + 
					currentState.getCupB().getCurrentAmount());

			Cup cupB2 = new Cup(currentState.getCupB().getCapacity(),0);
			return new MeasuringCupsPuzzleState(cupA2,cupB2,currentState); 
		} 

		// If cupA can just hold all of cupB's current amount
		Cup cupA3 = new Cup(currentState.getCupA().getCapacity(),
				currentState.getCupA().getCapacity());

		Cup cupB3 = new Cup(currentState.getCupB().getCapacity(),0);
		return new MeasuringCupsPuzzleState(cupA3,cupB3,currentState); 



	}
}
