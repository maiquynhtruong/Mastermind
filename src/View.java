import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class View {
	private static final int ROWS = 12;
	private int dw, dh, currentColor, currentRow;

	private Color colors[] = { Color.red,  Color.green,   Color.blue,
                             Color.cyan, Color.magenta, Color.yellow };
	private Color feedbackColors[] = {Color.WHITE, Color.BLACK, Color.GRAY};

	private Color guesses[][];
	private Color responses[][];

	private MastermindModel mm;
	private Mastermind controller;
	private boolean displayAnswer;
	
	Font font = new Font("Chalkboard", Font.PLAIN, 24);
    
	/**
	 * Constructor for Mastermind View
	 * we need the Model to talk to.
	 * we need the reference to guesses and responses to
	 * paint the correct colors
	 */
	public View(MastermindModel m, Color guesses[][], Color responses[][],Mastermind controller)
	{
		this.mm = m;
		this.guesses = guesses;
		this.responses = responses;
		this.controller=controller;
	}
	
   /**
    * This method is called by Mastermind class every time user wants to place a color on the grid 
    * or clicks the submit button to paint response to user's guess. Paints user guesses and responses
    * @param g
    * @param dw (display width)
    * @param dh (display height)
    * @param curRow 
    */
	
	 public void fillBoard( Graphics g, int dw, int dh, int curRow ) {
		   /* Used for indexing in responses */
		    int index=0;
	        this.currentRow = curRow;
	        /* Test to see the curRow
	         *  System.out.println("fillBoard.curRow: " + curRow);
	         */	 
	     /* Go through all the previous rows and current one */
		for (int i = 0; i <=currentRow; i++) {
			/* Test to see the how many times we execute this loop
	         * System.out.println(i); 
	         */	  	
			/* Paint the users guesses*/
			for (int j = 0; j < 4; j++) {
				g.setColor(guesses[i][j]);
				g.fillRect(dw * (j + 1), dh * (i + 1), dw, dh);
			}
			/* Paint the users responces */
			for (int row = 0; row < 2; row++)
				for (int col = 0; col < 2; col++) {
					g.setColor(responses[i][index]);
					g.fillRect(5 * dw + (dw / 2) * col, dh * (i + 1) + (dh / 2) * row, dw / 2, dh / 2);
					index++;
				}
			/*reset index for a new row */
			index=0;
		}
	}

	public void drawBoard( Graphics g, int dw, int dh ) {
		g.setColor( Color.black );
		// draw the rows
		for (int i=0; i < ROWS+1; i++) {
			g.drawLine( dw, dh*(i+1), 6*dw, dh*(i+1) );
		}
		// draw the columns
		for (int i=0; i <  6; i++) {
			g.drawLine( dw*(i+1), dh, dw*(i+1), (ROWS+1)*dh );
		}
		// draw the black-white horizontal dividers
		for (int i=0; i < ROWS; i++) {
			g.drawLine( dw*5, (i+1)*dh+dh/2, dw*6, (i+1)*dh+dh/2 );  
		}
		// draw the black-white vertical divider
		g.drawLine( dw*5+dw/2, dh, dw*5+dw/2, dh*(ROWS+1) );  

		// draw the color pallette
		for (int i=0; i < 6; i++) {
			g.setColor( colors[i] );
			g.fillRect( i*dw+dw/2, dh*(ROWS+1)+dh/2, dw, dh );  
		}
	}
	
	/**
	 * This method will pop up a dialog box to let the user know they have won
	 * and give option to either play another game or quit
	 * The code for the box was taken form this site: 
	 * http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanetopromptuserconfirmationademo.htm
	 * @param g
	 * @param frame
	 */
	public void dipslayWin(Graphics g, JFrame frame){ 
		 JDialog.setDefaultLookAndFeelDecorated(true);
		    int response = JOptionPane.showConfirmDialog(frame, "You have won!!! Do you want to play another game?", "Mastermind",
		        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		    if (response == JOptionPane.NO_OPTION) {
		      System.exit(0);
		    } else if (response == JOptionPane.YES_OPTION) {
		      controller.reset();
		    } else if (response == JOptionPane.CLOSED_OPTION) {
		      frame.dispose();
		    }
	}
	
	/**
	 * This method will pop up a dialog box to let the user know they have lost
	 * and give option to either play another game or quit
	 * The code for the box was taken form this site: 
	 * http://www.java2s.com/Tutorial/Java/0240__Swing/UsingJOptionPanetopromptuserconfirmationademo.htm
	 * @param g
	 * @param frame
	 */
	public void displayLoss(Graphics g, JFrame frame){
		JDialog.setDefaultLookAndFeelDecorated(true);
	    int response = JOptionPane.showConfirmDialog(frame, "You have lost:'( Do you want to play another game?", "Mastermind",
	        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    if (response == JOptionPane.NO_OPTION) {
	      System.exit(0);
	    } else if (response == JOptionPane.YES_OPTION) {
	      controller.reset();
	    } else if (response == JOptionPane.CLOSED_OPTION) {
	      frame.dispose();
	    }
	}
	
	/**
	 * This method is called when the check box is clicked by Mastermind class. Will display answer
	 * if the check box is checked
	 * @param g (graphics)
	 * @param dispAnswer (show or not?)
	 * @param ans (answer as an array of numbers)
	 */
	public void showAnswer(Graphics g, boolean dispAnswer, int [] ans){
		displayAnswer=dispAnswer;
		Color [] answer=new Color [4];
		// Convert numbers into Colors
		for(int i=0; i<4; i++){
			answer[i]=colors[ans[i]];
		}
		
		/* Display answer if check box is checked */
		if(displayAnswer){
			for(int i=0; i<4; i++){
				g.setColor(answer[i]);
				g.fillRect((3+i*22),2, 20, 20);
			}
		}
	}
}
