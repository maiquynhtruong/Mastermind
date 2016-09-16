This is the code for the Mastermind Game.
Rules can be found at: https://www.wikiwand.com/en/Mastermind_(board_game)

The program utilizes MVC model, where Mastermind.java is the controller, MastermindModel.java is the model, and View is the view. 
The controller, constructs the frame with components for View to display and instructs View to paint the display. It also takes care of actions related to clicks, analyzes the position the user clicks on the panel, then sends the information to model for computations. It determines, from the results by model, if the user has won or lost or is still in the game and controls the view accrodingly.

The View simply paints and repaints the board. It remembers the colors and can use the colors to fill in the cells with instructions from controller. It also implements methods to announce Win or Loss, or to display answer.

The model is responsible for the computation part of the program. It can generate and random secret code. It has methods to check the guess made by player against the secret code and return the results to controller.

Feel free to contribute to any components you would like to.

Thank you.
