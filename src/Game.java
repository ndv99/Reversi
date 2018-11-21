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
            } else {
                System.out.println("Player " + currentPlayer + ", you have no valid moves.");
            }

        }
    }

    void displayBoard(){
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

    private String[] calculateValidMoves(){
        List<String> validMovesList = new ArrayList<>();
        for (String[] row : this.board){
            for (String cell: row){

            }
        }
        String[] validMoves = validMovesList.toArray(new String[0]);
        return validMoves;
    }

    boolean checkN(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    boolean checkNE(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    boolean checkE(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    boolean checkSE(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    boolean checkS(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }
    boolean checkSW(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    boolean checkW(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    boolean checkNW(String row, String col){
        boolean valid = false;
        try{
            boolean checked = false;
            while (!checked){

            }
        } catch (ArrayIndexOutOfBoundsException e){

        }
        return valid;
    }

    void calculateChanges(){}

    void saveGame(){}

    void loadGame(){}
}
