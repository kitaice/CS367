///////////////////////////////////////////////////////////////////////////////
//Main Class File:  MeasuringCupsSovler.java
//File:             Cup.java
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
/**
 * A representation of a measuring cup.
 */
public class Cup {

	private int capacity;
	private int currentAmount;

	/**
	 * Construct a measuring cup
	 * 
	 * @param capacity
	 *            the maximum volume of the measuring cup
	 * @param currentAmount
	 *            the current volume of fluid in the measuring cup
	 * @throws IllegalArgumentException
	 *             when any of these conditions are true: capacity < 0,
	 *             currentAmount < 0, currentAmount > capacity
	 * 
	 */
	public Cup(int capacity, int currentAmount){
		// TODO
		if(capacity<0||currentAmount<0||currentAmount>capacity) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		this.currentAmount = currentAmount;
		//constructor, setting capacity and current amount as the input value
	}

	/**
	 * @return capacity
	 */
	public int getCapacity() {
		// TODO
		return capacity;//return current capacity
	}

	/**
	 * @return currentAmount
	 */
	public int getCurrentAmount() {
		// TODO
		return currentAmount;//return current amount
	}

	/**
	 * Compare this cup against another cup
	 * 
	 * @param cup
	 *            an other cup to compare against this cup
	 * @return true if the other cup has the same capacity and currentAmount as
	 *         this cup and false otherwise
	 */
	public boolean equals(Cup cup) {
		// TODO
		return (cup.getCapacity() == this.capacity) &&
			   (cup.getCurrentAmount() == this.currentAmount);
		//return true if both capacity and current amount are equal
	}

	/**
	 * @return a string containing the currentAmount
	 */
	public String toString() {
		return String.valueOf(this.currentAmount);
	}
}
