import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class for handling gameplay. Stores player objects and handles game board.
 */
class Game implements Serializable{

// Implementation of 'Serializable' means that game object can be saved and written to a file.
// serialVersionUID is need to make object serializable.
    private static final long serialVersionUID = 1L;

    private String[][] board = {
        {"-", "-", "-", "-", "-", "-", "-", "-"},
        {"-", "-", "-", "-", "-", "-", "-", "-"},
        {"-", "-", "-", "-", "-", "-", "-", "-"},
        {"-", "-", "-", "X", "O", "-", "-", "-"},
        {"-", "-", "-", "O", "X", "-", "-", "-"},
        {"-", "-", "-", "-", "-", "-", "-", "-"},
        {"-", "-", "-", "-", "-", "-", "-", "-"},
        {"-", "-", "-", "-", "-", "-", "-", "-"}
    };

    // needed for checking moves, where {-1, 0) checks north of a cell, {-1, 1} is north east, etc.
    private final int[][] CHECKDIR = {
        {-1, 0}, {-1, 1}, {0, 1}, {1, 1},
        {1, 0}, {1, -1}, {0, -1}, {-1, -1}
    };

    private Player[] players;
    private int currentPlayer;
    private String playerPiece;
    private String opponentPiece;
    private int emptySpaces;
    private boolean PlayerVsComputer;
    private boolean finished;

    /**
     * Constructor method for game.
     * @param player1 Player 1 name (string)
     * @param player2 Player 2 name (string)
     * @param PlayerVsComputer Boolean to determine if game is Player vs Player or Player vs Computer.
     */
    Game(String player1, String player2, boolean PlayerVsComputer){
        this.currentPlayer = 1;
        this.playerPiece = "X";
        this.opponentPiece = "O";
        this.players = new Player[]{new Player(player1), new Player(player2)};
        this.PlayerVsComputer = PlayerVsComputer;
        this.finished = false;
    }

    /**
     * Method to handle gameplay.
     */
    void playGame(){
        Scanner gameScanner = new Scanner(System.in);
        while (!finished){ // loops while not finished.
            scanBoard();
            if (this.emptySpaces > 0){ // will only commence if there are empty spaces
                int p1Score = players[0].getScore();
                int p2Score = players[1].getScore();
                if (p1Score > 0 && p2Score > 0){ // if both player scores are more than 0 - if one is 0 then other player has won.
                    String[] validMoves = calculateValidMoves(); // valid move are fetched

                    if (validMoves.length > 0) { // if there are valid moves (player can't take turn if no valid moves)
                        if (currentPlayer == 2){ // computer would be second player, needed to check if computer or not.
                            if (!PlayerVsComputer){
                                humanPlayerTurn(validMoves, gameScanner);
                            } else {
                                CPUturn(validMoves);
                            }
                        } else { // player 1 is always human.
                            humanPlayerTurn(validMoves, gameScanner);
                        }

                    } else {
                        System.out.println((players[currentPlayer - 1].getName()) + ", you have no valid moves.");
                        changePlayer();
                        validMoves = calculateValidMoves(); // have to check if player 2 also has no valid moves - if neither player can go, loop must break.
                        if (validMoves.length == 0){
                            finished = true;
                        }
                    }
                } else {
                    finished = true;
                }

            } else {
                finished = true;
            }

        }
        // score calculation
        int p1Score = players[0].getScore();
        int p2Score = players[1].getScore();
        if (p1Score > p2Score){
            System.out.println(players[0].getName() + ", you are the winner!");
        } else if (p1Score < p2Score){
            System.out.println(players[1].getName() + ", you are the winner!");
        } else{
            System.out.println("The game is a draw.");
        }
    }

    /**
     * Method for a human player to take their turn.
     * @param validMoves Array of all possible valid moves at that point in the game.
     * @param gameScanner Scanner to get player input (wasteful to create a new one each turn)
     */
    private void humanPlayerTurn(String[] validMoves, Scanner gameScanner){
        System.out.println();
        displayBoard(); // display board in current state
        System.out.println();
        // show player scores
        System.out.println(players[0].getName() + ": " + players[0].getScore());
        System.out.println(players[1].getName() + ": " + players[1].getScore());
        System.out.println();
        System.out.println((players[currentPlayer - 1].getName()) + ", it is your turn."); // says whose turn it is

        boolean validRequest = false;
        // player must enter a valid request. see below println for all option.
        while (!validRequest){
            System.out.println("\nEnter 1 to make your move, 2 to save your game, or 3 to exit.");
            String playerRequest = gameScanner.nextLine();
            switch (playerRequest){
                case("1"): // player chooses to take turn
                    validRequest = true;
                    boolean validMove = false;
                    // player must make a valid move.
                    while (!validMove){
                        System.out.println("Valid moves: " + String.join(" ,", validMoves)); // valid moves are shown
                        System.out.println("Please enter the column you want: ");
                        String playerColumnChoice = gameScanner.nextLine().toUpperCase();
                        System.out.println("Please enter the row you want: ");
                        String playerRowChoice = gameScanner.nextLine();
                        validMove = validateMove(new String[]{playerColumnChoice, playerRowChoice}, validMoves);

                        if (validMove){
                            changeCells(rowToIndex(playerRowChoice), colToIndex(playerColumnChoice)); // board must be updated
                            changePlayer(); // player must change after turn is finished.
                        } else {
                            System.out.println("Your move is not valid, please try again.");
                        }
                    }
                    break;

                case("2"): // player chooses to save the game
                    validRequest = true;
                    saveGame(gameScanner);
                    break;

                case("3"): // player chooses to end game
                    validRequest = true;
                    finished = true;
                    break;

                default: // if player tries an input that isn't one of the above, they'll be told that their input is invalid
                    System.out.println("\nYour request is invalid. Please try again");
            }

        }
    }

    /**
     * Method for computer to take turn in AI game.
     * @param validMoves String array of all possible valid moves at that point in the game.
     */
    private void CPUturn(String[] validMoves){
        int moveChoiceIndex = ThreadLocalRandom.current().nextInt(0, validMoves.length); // chooses a random valid move
        String[] moveChoice = validMoves[moveChoiceIndex].split(" "); // splits choice into column and row
        changeCells(rowToIndex(moveChoice[1]), colToIndex(moveChoice[0])); // board must be updated
        changePlayer(); // player must be changed.
    }

    /**
     * Changes the current player.
     */
    private void changePlayer(){
        if (this.currentPlayer == 1){
            this.currentPlayer = 2;
            this.playerPiece = "O";
            this.opponentPiece = "X";
        } else {
            this.currentPlayer = 1;
            this.playerPiece = "X";
            this.opponentPiece = "O";
        }
    }

    /**
     * Displays the board in a nice format.
     */
    private void displayBoard(){

        System.out.println(" | A B C D E F G H");
        int currentRow = 1;
        for (String[] row : this.board){
            String stringRow = String.join(" ", row);
            System.out.println(currentRow + "| " + stringRow);
            currentRow ++;
        }
    }

    /**
     * Method to check if requested move is valid.
     * @param move Co-ordinates of player's chosen cell (String array)
     * @param validMoves All possible valid moves (String array)
     * @return valid: boolean if move is valid or not.
     */
    private boolean validateMove(String[] move, String[] validMoves){
        boolean valid = false;
        int colIndex = colToIndex(move[0]); //  converts player entry to an index
        int rowIndex = rowToIndex(move[1]);
        if(colIndex >= 0 && colIndex <= 7 && rowIndex >=0 && rowIndex <= 7){ // if cell within board limits
            String moveString = String.join(" ", move); // converts player entry into a single array
            for(String validMove : validMoves){ // checks if entry is in list of valid moves
                if (moveString.equals(validMove)){
                    valid = true;
                    break; // break the loop - wasteful to keep going after valid move is found
                }
            }

        }
        return valid;
    }

    /**
     * Converts player's string input for the row into an index.
     * @param row Chosen row (String)
     * @return Chosen row as index (int)
     */
    private int rowToIndex(String row){
        try {
            return Integer.parseInt(row) - 1;
        } catch (NumberFormatException e){ // happens if player puts in something that isn't valid
            return 9; // 9 is not a valid index, other method will catch this
        }

    }

    /**
     * Converts player's string input for the column into an index.
     * @param col Chosen column (String)
     * @return Chosen column as index (int)
     */
    private int colToIndex(String col){
        int colIndex = 9; // nine is an impossible index, other method will catch this.
        // Columns are letters
        switch (col){
            case ("A"):
                colIndex = 0;
                break;
            case ("B"):
                colIndex = 1;
                break;
            case ("C"):
                colIndex = 2;
                break;
            case ("D"):
                colIndex = 3;
                break;
            case ("E"):
                colIndex = 4;
                break;
            case ("F"):
                colIndex = 5;
                break;
            case ("G"):
                colIndex = 6;
                break;
            case ("H"):
                colIndex = 7;
                break;
        }
        return colIndex;
    }

    /**
     * Converts a row index to a String for the row.
     * @param rowIndex Index for a row (int)
     * @return Row as a string
     */
    private String indexToRow(int rowIndex){
        return Integer.toString(rowIndex + 1);
    }

    /**
     * Converts a column index to a String for the row.
     * @param colIndex Index for a column (int)
     * @return Column as a string
     */
    private String indexToCol(int colIndex){
        String col = "A";
        switch (colIndex){
            case (1):
                col = "B";
                break;
            case (2):
                col = "C";
                break;
            case (3):
                col = "D";
                break;
            case (4):
                col = "E";
                break;
            case (5):
                col = "F";
                break;
            case (6):
                col = "G";
                break;
            case (7):
                col = "H";
                break;
        }
        return col;
    }

    /**
     * Finds out which moves are valid for the current player at the current stage in the game.
     * @return String array of valid moves.
     */
    private String[] calculateValidMoves(){
        List<String> validMovesList = new ArrayList<>(); // easier to start off with an empty list, could be any number of valid moves
        for (int rowIndex = 0; rowIndex < 8; rowIndex++){ // go through all rows
            for (int colIndex = 0; colIndex < 8; colIndex++){ // go through all columns
                if (board[rowIndex][colIndex].equals("-")){ // if the space is empty
                    boolean validMove = cellChecker(rowIndex, colIndex); // checks the cell
                    if (validMove){ // move is added to list if valid
                        String[] move = {indexToCol(colIndex), indexToRow(rowIndex)};
                        validMovesList.add(String.join(" ", move));
                    }
                }
            }
        }
        return validMovesList.toArray(new String[0]); // list is converted to an array
    }

    /**
     * Method to check if a cell is a valid move.
     * @param rowIndex Index of the row being checked (int)
     * @param colIndex Index of the column being checked
     * @return Boolean representing validity of move (true for valid, false for invalid)
     */
    private boolean cellChecker(int rowIndex, int colIndex){
        boolean valid = false;
        int originalRowIndex = rowIndex; // Original row and column index need to be stored since these are modified
        int originalColIndex = colIndex;
        for (int[] direction : CHECKDIR){
            if (valid){
                break;
            }
            rowIndex = originalRowIndex; // reset for each direction, otherwise incorrect moves are found
            colIndex = originalColIndex;
            boolean checked = false; // keeps running until check on direction is complete
            while (!checked){
                try{
                    if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(opponentPiece)){
                        valid = true; // move may be valid if next cell in direction being checked is of opponent's colour
                        // need to move one space along in current checking direction
                        rowIndex += direction[0];
                        colIndex += direction[1];
                    } else if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(playerPiece)){
                        // if a piece of the player's own colour is found, the loop can break here - valid will be true if white piece is found before, invalid by default
                        checked = true;
                    } else {
                        // move is set to invalid if empty space is found, check in that direction is complete.
                        valid = false;
                        checked = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    // move is invalid if it has to check beyond edge of board, obviously.
                    valid = false;
                    checked = true;
                }
            }
        }

        return valid;
    }

    /**
     * Changes cells accordingly with move.
     * @param rowIndex Row index of chosen cell.
     * @param colIndex Column index of chosen cell.
     */
    private void changeCells(int rowIndex, int colIndex){
        int originalRowIndex = rowIndex; // Original row and column index need to be stored since these are modified
        int originalColIndex = colIndex;
        for (int[] direction : CHECKDIR){
            rowIndex = originalRowIndex; // reset for each direction, otherwise incorrect moves are found
            colIndex = originalColIndex;
            boolean valid = false;
            List<int[]> potentialChanges = new ArrayList<>(); // List is cleared for each direction
            potentialChanges.add(new int[]{rowIndex, colIndex});
            boolean checked = false; // keeps running until check on direction is complete
            while (!checked){
                try{
                    if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(opponentPiece)){
                        valid = true;// direction may be valid if next cell in direction being checked is of opponent's colour
                        // need to move one space along in current checking direction
                        rowIndex += direction[0];
                        colIndex += direction[1];
                        potentialChanges.add(new int[]{rowIndex, colIndex});
                    } else if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(playerPiece)){
                        // if a piece of the player's own colour is found, the loop can break here - valid will be true if white piece is found before, invalid by default
                        checked = true;
                        potentialChanges.add(new int[]{rowIndex, colIndex});
                    } else {
                        // direction is set to invalid if empty space is found, check in that direction is complete.
                        valid = false;
                        checked = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    // move is invalid if it has to check beyond edge of board, obviously.
                    valid = false;
                    checked = true;
                }
            }
            if (valid){
                // Changes are only made if direction is found to be valid
                for (int[] change : potentialChanges){
                    this.board[change[0]][change[1]] = this.playerPiece;
                }
            }
        }
    }

    /**
     * Method to scan the board to calculate the score for each player and the number of empty spaces
     */
    private void scanBoard(){
        this.emptySpaces = 0;
        this.players[0].setScore(0);
        this.players[1].setScore(0);
        for(int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                switch (board[row][col]) {
                    case "O":
                        this.players[1].setScore(this.players[1].getScore() + 1);
                        break;
                    case "X":
                        this.players[0].setScore(this.players[0].getScore() + 1);
                        break;
                    default:
                        this.emptySpaces++;
                        break;
                }
            }
        }
    }

    /**
     * Method to save the game in its current state. The game object is serialized and written to a file.
     * @param saveGameScanner Scanner to get input. Scanner is already created in previous methods, wasteful to create a new one.
     */
    private void saveGame(Scanner saveGameScanner){
        System.out.println("\nEnter save name:");
        String saveName = saveGameScanner.nextLine();

        try{
            FileOutputStream outputStream = new FileOutputStream(new File(saveName));
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);

            objectOutput.writeObject(this); // writes object to specified file

            objectOutput.close();
            outputStream.close();
            System.out.println("Game saved as '" + saveName + "'.\n"); // confirmation that game has been saved
        } catch (IOException e){
            System.out.println("Error (IOException), game was not saved."); //  Error message if game wasn't saved.
        }
    }

}
