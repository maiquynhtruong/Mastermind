import java.awt.Color;
import java.util.Random;

public class MastermindModel {
	public static final int NUMBER_OF_POSITIONS = 4;
	public static final int MAXIMUM_VALUE = 5;

	private Random rn = new Random();
	private int[] answer = new int[NUMBER_OF_POSITIONS];
	private int answerSums[] = new int[MAXIMUM_VALUE+1];
	 

	public MastermindModel() {
		/* create the random code for the user to guess */
		for (int i=0; i < answer.length; i++){
			answer[i] = rn.nextInt( answerSums.length );
        	//System.out.println(answer[i]);
		}
		/* Initialize the answer housekeeping array */
		for (int i=0; i < answer.length; i++)
			answerSums[ answer[i] ]++;
	}

	/**
	 *@param input, bw
	 * this method evaluates the user's input and determines
	 * how many black & white pegs to place
	 * It is called by controller when user clicks submit button 
	 * bw[0] means how many black pegs to draw
	 * bw[1] means how many white pegs to draw
	 **/
	public void guess(int[] input, int[] bw) {
		int[] tempAns = new int[4];

		for (int i = 0; i < 4; i++) {
			tempAns[i] = answer[i];
		}
        /*
         * Testing the answer and input 
         * for (int i = 0; i < 4; i++) {
			System.out.println("answer: " + answer[i] + " input: " + input[i]);
		}
         */		

		/* First check for black pegs */
		for (int i = 0; i < 4; i++) {
			if (input[i] == tempAns[i]) {
				bw[0]++;
				input[i] = -1;
				tempAns[i] = -1;
			}
		}

		/* Check for white pegs */
		for (int i = 0; i < 4; i++) {
			int j;
			for (j = 0; j < tempAns.length; j++) {
				if (input[i] != -1) {
					if (input[i] == tempAns[j]) {
						bw[1]++;
						/*test for white pegs
						 * System.out.println("else input: " + input[i]);
						 */						
						tempAns[j] = -1;
						input[i] = -1;
					}
				}
			}
		}
		
		/* test the number of black and white pegs
		 * System.out.println("num of black pegs: " +bw[0]); System.out.println("num of white pegs: "+bw[1]);
		 */	
	}

	public int[] getAnswer() {
		return answer;
	}
}
