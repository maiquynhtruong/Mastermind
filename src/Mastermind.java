import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Mastermind extends Canvas implements MouseListener, ActionListener {
	private static final int ROWS = 12;
	/* display width and height */
	private int dw, dh, currentColor, currentRow;

	private Color colors[] = { Color.red,  Color.green,   Color.blue,
                             Color.cyan, Color.magenta, Color.yellow };

	/* the players guesses */
	private Color guesses[][] = new Color[ROWS][4];
	/* the programs responses */
	private Color responses[][] = new Color[ROWS][4];
		
	/* The column that user picked*/
	private int column;
	
	/* used to keep track of #of times submit button is clicked*/
	int count=0;

	private int[] input = new int[4], bw = new int[2];

	private MastermindModel model;
	private boolean displayAnswer;
	/* did the player guess the right thing?*/
	private boolean playerWon=false;
	/* player lost*/
	private boolean playerLost=false;
	private View view;
	private JFrame frame;

	/**
	 * Constructor for Mastermind Controller
	 */
	public Mastermind() {
		reset();

		 frame = new JFrame( "Mastermind" );
		/* exit if we close the window */
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setBackground( Color.white );

		/* just one button to submit each guess */
		JButton submit = new JButton( "Submit guess" );
		submit.addActionListener( this );
		
		/* we need to know mouse coords to place colored pegs */
		addMouseListener( this );
		JMenuBar menuBar = new JMenuBar();
		
		/* A check box for the user to click if they want to see the answer */
		final JCheckBoxMenuItem showAns = new JCheckBoxMenuItem("Show answer");		
		showAns.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				displayAnswer = showAns.isSelected();	
				repaint();
			}
		});
		
		/*add the check box to Frame*/
		menuBar.add(showAns);
		frame.setJMenuBar(menuBar);
		
		frame.getContentPane().add( this );
		/* put the button on the bottom of the frame */
		frame.getContentPane().add( submit, BorderLayout.SOUTH );
		//frame.getContentPane().add( showAns);
        frame.pack();
		frame.setSize( new Dimension( 220, 450 ) );
		frame.setResizable( false );
		frame.setVisible( true );
		
		model = new MastermindModel();
		view = new View( model, guesses, responses,this );
	}
	
	/**
	 *  Called when the Submit Guess button is pressed. Will gall guess in model and get responses
	 * @param e (Action event)
	 */
	
	public void actionPerformed( ActionEvent e ) {
		if (e.getActionCommand().equals("Submit guess" )) {
			count++;
			bw[0] = 0; bw[1] = 0;
            model.guess( input, bw );
            if(bw[0]==4){
            	playerWon=true;
            }
            
            /* initialize the responses to gray */
            for (int index = 0; index < 4; index++)
            		responses[currentRow][index] = Color.lightGray;
            
            /* get the black pegs */
            for(int i = 0; i < bw[0]; i++) {
            	responses[currentRow][i] = Color.BLACK;
            }
            
            /* get the white pegs */
            for(int i = 0; i < bw[1]; i++) {
            	responses[currentRow][bw[0]+i] = Color.WHITE;
            }
            
            /* repaint() calls paint(), but paint() is more likely to be call at a later time */
            repaint();
          /* Game over reset it */
            if(count>12)
            	playerLost=true;
         /* if currentRow is 11, do nothing */
            else if (currentRow == 11) return;
            else currentRow++;           	
		}				
	}

	public void reset() {
		playerLost=false;
		playerWon=false;
		count=0;
		column = 1;
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < 4; j++) {
				guesses[i][j] = Color.white;
				responses[i][j] = Color.white;
			}
		}

		/* Have a way to distinguish the white color so if -1 then white*/
		for(int i = 0; i < 4; i++){
			input[i] = -1;
		}
		currentRow = 0;
		currentColor = 0;
		displayAnswer = false;
		repaint();
	}

	/**
	 * @param e
	 * Called when the mouse button is pressed to select colors
	 * and to indicate which square to add a peg to.
	 */
	public void mousePressed( MouseEvent e ) {
		/* Get the x & y cords of the mouse position */
		int x = e.getX(), y = e.getY();
		/* Figures out if we are clicking on the color palate */
		if((x > dw/2 && y > 13 * dh + dh/2) && (x < 7 * dw - dw/2 && y < 14 * dh + dh/2)) {
			/* Index for the Color array*/
			currentColor = (x - dw/2) / dw; 
			/*
			 * Test the current Color
			 * System.out.println("pallete's currentColor:" + currentColor);
			 */			
		}
		/* Figure out where the user is clicking on the grid */
		else if ((x > dw && y > dh) && (x < 5 * dw && y < 13 * dh)){
			/* Figure out the column that the user is clicking */
			 column = (x - 31) / 31;
			 
			 /*
			  * Test to see if the coordinates are correct
			  * System.out.println("currentRow: " + currentRow + ", currentColumn: " + column);
			  */
			 
			 /* Add the guess to the guesses Array */
			 guesses[currentRow][column] = colors[currentColor];
			 input[column] = currentColor;
			 repaint();	
		}
	}

	public void mouseEntered(  MouseEvent e ) { }

	public void mouseExited(   MouseEvent e ) { }

	public void mouseClicked(  MouseEvent e ) { }

	public void mouseReleased( MouseEvent e ) { }

	/* the paint method we need 
	 * remember we're a ContentPane here
	 * Don't call paint, call repaint instead
	 */
	public void paint( Graphics g ) {
        /*
         * Test to see what current row the paint method is getting
         * System.out.println("paint.currentRow: "+ currentRow);
         */		
		dw = (int) getSize().width / 7;
		dh = (int) getSize().height / (ROWS+3);
		view.fillBoard(g, dw, dh, currentRow );
		view.drawBoard(g, dw, dh );
		view.showAnswer(g, displayAnswer,model.getAnswer());	
		
		if (playerWon) {
			view.dipslayWin(g, frame);
		}
		else if (playerLost) {
			view.displayLoss(g, frame);
		}		
	}
	
	public static void main( String[] args ) {
		new Mastermind();
	}
}
