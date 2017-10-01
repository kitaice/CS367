
public class mainTest {

	 public static void main(String[] args) {

		    // change these to test different cases
		    int capA = 5, capB = 2;
		    int currA = 4, currB = 2;
		    Cup cupA = new Cup(capA, currA);
		    Cup cupB = new Cup(capB, currB);

		    // need this to call state methods
		    MeasuringCupsPuzzle puzzle = new MeasuringCupsPuzzle(null,null); 
				
		    // most tests require a puzzle state, so here it is!
		    MeasuringCupsPuzzleState initialState = 
		        new MeasuringCupsPuzzleState(cupA, cupB, null);

		    // put calls to tests here
		    test_pourAtoB(puzzle,initialState);

		  }

		  private static void test_pourAtoB(
		       MeasuringCupsPuzzle puzzle,
		        MeasuringCupsPuzzleState s1)  {

		    System.out.print("before pour: ");
		    System.out.println("s1 = " + s1);

		    MeasuringCupsPuzzleState s2 = puzzle.fillCupA(s1);
		    System.out.print(" after pour: ");
		    System.out.println("s1 = " + s1 + " s2 = " + s2);		
		  }

}
