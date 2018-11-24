import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

class Game implements Serializable{

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

    Game(String player1, String player2, boolean PlayerVsComputer){
        this.currentPlayer = 1;
        this.playerPiece = "X";
        this.opponentPiece = "O";
        this.players = new Player[]{new Player(player1), new Player(player2)};
        this.PlayerVsComputer = PlayerVsComputer;
        this.finished = false;
    }

    void playGame(){
        Scanner gameScanner = new Scanner(System.in);
        while (!finished){
            scanBoard();
            if (this.emptySpaces > 0){
                int p1Score = players[0].getScore();
                int p2Score = players[1].getScore();
                if (p1Score > 0 && p2Score > 0){
                    String[] validMoves = calculateValidMoves();
                    if (validMoves.length > 0) {
                        if (currentPlayer == 2){
                            if (!PlayerVsComputer){
                                humanPlayerTurn(validMoves, gameScanner);
                            } else {
                                CPUturn(validMoves);
                            }
                        } else {
                            humanPlayerTurn(validMoves, gameScanner);
                        }

                    } else {
                        System.out.println((players[currentPlayer - 1].getName()) + ", you have no valid moves.");
                        changePlayer();
                        validMoves = calculateValidMoves();
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

    private void humanPlayerTurn(String[] validMoves, Scanner gameScanner){
        System.out.println();
        displayBoard();
        System.out.println();
        System.out.println(players[0].getName() + ": " + players[0].getScore());
        System.out.println(players[1].getName() + ": " + players[1].getScore());
        System.out.println();
        System.out.println((players[currentPlayer - 1].getName()) + ", it is your turn.");

        boolean validRequest = false;
        while (!validRequest){
            System.out.println("\nEnter 1 to make your move, 2 to save your game, or 3 to exit.");
            String playerRequest = gameScanner.nextLine();
            switch (playerRequest){
                case("1"):
                    validRequest = true;
                    boolean validMove = false;
                    while (!validMove){
                        System.out.println("Valid moves: " + String.join(" ,", validMoves));
                        System.out.println("Please enter the column you want: ");
                        String playerColumnChoice = gameScanner.nextLine().toUpperCase();
                        System.out.println("Please enter the row you want: ");
                        String playerRowChoice = gameScanner.nextLine();
                        validMove = validateMove(new String[]{playerColumnChoice, playerRowChoice}, validMoves);
                        if (validMove){
                            changeCells(rowToIndex(playerRowChoice), colToIndex(playerColumnChoice));
                            changePlayer();
                        } else {
                            System.out.println("Your move is not valid, please try again.");
                        }
                    }
                    break;
                case("2"):
                    validRequest = true;
                    saveGame();
                    break;
                case("3"):
                    validRequest = true;
                    finished = true;
                    break;
                default:
                    System.out.println("\nYour request is invalid. Please try again");
            }

        }
    }

    private void CPUturn(String[] validMoves){
        int moveChoiceIndex = ThreadLocalRandom.current().nextInt(0, validMoves.length);
        String[] moveChoice = validMoves[moveChoiceIndex].split(" ");
        changeCells(rowToIndex(moveChoice[1]), colToIndex(moveChoice[0]));
        changePlayer();
    }

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

    private void displayBoard(){
        System.out.println(" | A B C D E F G H");
        int currentRow = 1;
        for (String[] row : this.board){
            String stringRow = String.join(" ", row);
            System.out.println(currentRow + "| " + stringRow);
            currentRow ++;
        }
    }

    private boolean validateMove(String[] move, String[] validMoves){
        boolean valid = false;
        int colIndex = colToIndex(move[0]);
        int rowIndex = rowToIndex(move[1]);
        if(colIndex >= 0 && colIndex <= 7 && rowIndex >=0 && rowIndex <= 7){
            String moveString = String.join(" ", move);
            for(String validMove : validMoves){
                if (moveString.equals(validMove)){
                    valid = true;
                    break;
                }
            }

        }
        return valid;
    }

    private int rowToIndex(String row){
        try {
            return Integer.parseInt(row) - 1;
        } catch (NumberFormatException e){
            return 9;
        }

    }

    private int colToIndex(String col){
        int colIndex = 9;
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

    private String indexToRow(int rowIndex){
        return Integer.toString(rowIndex + 1);
    }
    
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

    private String[] calculateValidMoves(){
        List<String> validMovesList = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < 8; rowIndex++){
            for (int colIndex = 0; colIndex < 8; colIndex++){
                if (board[rowIndex][colIndex].equals("-")){
                    boolean validMove = cellChecker(rowIndex, colIndex);
                    if (validMove){
                        String[] move = {indexToCol(colIndex), indexToRow(rowIndex)};
                        validMovesList.add(String.join(" ", move));
                    }
                }
            }
        }
        return validMovesList.toArray(new String[0]);
    }

    private boolean cellChecker(int rowIndex, int colIndex){
        boolean valid = false;
        boolean[] validDirs = {
                false, false, false, false, false, false, false, false
        };
        int count = 0;
        int originalRowIndex = rowIndex;
        int originalColIndex = colIndex;
        for (int[] direction : CHECKDIR){
            rowIndex = originalRowIndex;
            colIndex = originalColIndex;
            boolean checked = false;
            boolean validDir = false;
            while (!checked){
                try{
                    if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(opponentPiece)){
                        validDir = true;
                        rowIndex += direction[0];
                        colIndex += direction[1];
                    } else if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(playerPiece)){
                        checked = true;
                    } else {
                        validDir = false;
                        checked = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    validDir = false;
                    checked = true;
                }
            }
            validDirs[count] = validDir;
            count++;
        }

        for (boolean validDir : validDirs){
            if (validDir){
                valid = true;
                break;
            }
        }

        return valid;
    }

    private void changeCells(int rowIndex, int colIndex){
        int originalRowIndex = rowIndex;
        int originalColIndex = colIndex;
        for (int[] direction : CHECKDIR){
            rowIndex = originalRowIndex;
            colIndex = originalColIndex;
            boolean valid = false;
            List<int[]> potentialChanges = new ArrayList<>();
            potentialChanges.add(new int[]{rowIndex, colIndex});
            boolean checked = false;
            while (!checked){
                try{
                    if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(opponentPiece)){
                        valid = true;
                        rowIndex += direction[0];
                        colIndex += direction[1];
                        potentialChanges.add(new int[]{rowIndex, colIndex});
                    } else if (this.board[rowIndex + direction[0]][colIndex + direction[1]].equals(playerPiece)){
                        checked = true;
                        potentialChanges.add(new int[]{rowIndex, colIndex});
                    } else {
                        valid = false;
                        checked = true;
                    }
                } catch (ArrayIndexOutOfBoundsException e){
                    valid = false;
                    checked = true;
                }
            }
            if (valid){
                for (int[] change : potentialChanges){
                    this.board[change[0]][change[1]] = this.playerPiece;
                }
            }
        }
    }

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

    private void saveGame(){
        Scanner saveGameScanner = new Scanner(System.in);
        System.out.println("\nEnter save name:");
        String saveName = saveGameScanner.nextLine();

        try{
            FileOutputStream outputStream = new FileOutputStream(new File(saveName));
            ObjectOutputStream objectOutput = new ObjectOutputStream(outputStream);

            objectOutput.writeObject(this);

            objectOutput.close();
            outputStream.close();
            System.out.println("Game saved as '" + saveName + "'.\n");
        } catch (IOException e){
            System.out.println("Error.");
        }
    }

}
