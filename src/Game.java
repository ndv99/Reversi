import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Game {

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
    private String gameName;
    private Player[] players;
    private int currentPlayer;
    private String playerPiece;
    private String opponentPiece;

    Game(String player1, String player2){
        this.currentPlayer = 1;
        this.playerPiece = "X";
        this.opponentPiece = "O";
        String[] players = {player1, player2};
    }

    void playGame(){
        Scanner gameScanner = new Scanner(System.in);
        boolean finished = false;
        while (!finished){
            System.out.println("");
            displayBoard();
            String[] validMoves = calculateValidMoves();
            if (validMoves.length > 0){
                System.out.println("Player " + currentPlayer + ", it is your turn.");
                System.out.println("Valid moves: " + String.join(" ,", validMoves));
                gameScanner.nextLine();
            } else {
                System.out.println("Player " + currentPlayer + ", you have no valid moves.");
            }

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

    void placePiece(){}

    boolean validateMove(){
        boolean valid = false;
        return valid;
    }

    int rowToIndex(String row){
        return Integer.parseInt(row) - 1;
    }

    int colToIndex(String col){
        int colIndex = 0;
        switch (col){
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
                boolean validMove = checkHandler(rowIndex, colIndex);
                if (validMove){
                    String[] move = {indexToCol(colIndex), indexToRow(rowIndex)};
                    validMovesList.add(String.join(" ", move));
                }
            }
        }
        return validMovesList.toArray(new String[0]);
    }

    private boolean checkN(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex - 1][colIndex].equals(opponentPiece)){
                    valid = true;
                    rowIndex --;
                } else if (this.board[rowIndex - 1][colIndex].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkNE(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex - 1][colIndex + 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex --;
                    colIndex ++;
                } else if (this.board[rowIndex - 1][colIndex + 1].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkE(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex][colIndex + 1].equals(opponentPiece)){
                    valid = true;
                    colIndex ++;
                } else if (this.board[rowIndex][colIndex + 1].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkSE(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex + 1][colIndex + 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex ++;
                    colIndex ++;
                } else if (this.board[rowIndex + 1][colIndex + 1].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkS(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex + 1][colIndex].equals(opponentPiece)){
                    valid = true;
                    rowIndex ++;
                } else if (this.board[rowIndex + 1][colIndex].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkSW(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex + 1][colIndex - 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex ++;
                    colIndex --;
                } else if (this.board[rowIndex + 1][colIndex - 1].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkW(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex][colIndex - 1].equals(opponentPiece)){
                    valid = true;
                    colIndex --;
                } else if (this.board[rowIndex][colIndex - 1].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkNW(int rowIndex, int colIndex){
        boolean valid = false;


        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex - 1][colIndex - 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex --;
                    colIndex --;
                } else if (this.board[rowIndex - 1][colIndex - 1].equals(playerPiece)){
                    checked = true;
                } else {
                    valid = false;
                    checked = true;
                }
            } catch (ArrayIndexOutOfBoundsException e){
                valid = false;
                checked = true;
            }
        }
        return valid;
    }

    private boolean checkHandler(int rowIndex, int colIndex){
        boolean valid = false;

        boolean validN = checkN(rowIndex, colIndex);
        boolean validNE = checkNE(rowIndex, colIndex);
        boolean validE = checkE(rowIndex, colIndex);
        boolean validSE = checkSE(rowIndex, colIndex);
        boolean validS = checkS(rowIndex, colIndex);
        boolean validSW = checkSW(rowIndex, colIndex);
        boolean validW = checkW(rowIndex, colIndex);
        boolean validNW = checkNW(rowIndex, colIndex);

        if (validN || validNE || validE || validSE || validS || validSW || validW || validNW){
            valid = true;
        }
        return valid;
    }



    void calculateChanges(){}

    void saveGame(){}

    void loadGame(){}
}
