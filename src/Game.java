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
    private int emptySpaces;

    Game(String player1, String player2){
        this.currentPlayer = 1;
        this.playerPiece = "X";
        this.opponentPiece = "O";
        players = new Player[]{new Player(player1), new Player(player2)};
    }

    void playGame(){
        Scanner gameScanner = new Scanner(System.in);
        boolean finished = false;
        while (!finished){
            scanBoard();
            if (this.emptySpaces > 0){
                int p1Score = players[0].getScore();
                int p2Score = players[1].getScore();
                if (p1Score > 0 && p2Score > 0){
                    System.out.println("");
                    displayBoard();
                    String[] validMoves = calculateValidMoves();
                    if (validMoves.length > 0){
                        System.out.println((players[currentPlayer - 1].getName()) + ", it is your turn.");
                        boolean validMove = false;
                        while (!validMove){
                            System.out.println("Valid moves: " + String.join(" ,", validMoves));
                            System.out.println("Please enter the column you want: ");
                            String playerColumnChoice = gameScanner.nextLine().toUpperCase();
                            System.out.println("Please enter the row you want: ");
                            String playerRowChoice = gameScanner.nextLine();
                            validMove = validateMove(new String[]{playerColumnChoice, playerRowChoice});
                            if (validMove){
                                calculateChanges(rowToIndex(playerRowChoice), colToIndex(playerColumnChoice));
                                if (this.currentPlayer == 1){
                                    this.currentPlayer = 2;
                                    this.playerPiece = "O";
                                    this.opponentPiece = "X";
                                } else {
                                    this.currentPlayer = 1;
                                    this.playerPiece = "X";
                                    this.opponentPiece = "O";
                                }

                            } else {
                                System.out.println("Your move is not valid, please try again.");
                            }
                        }

                    } else {
                        System.out.println((players[currentPlayer - 1].getName()) + ", you have no valid moves.");
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
        Player winner;
        if (p1Score > p2Score){
            winner = players[0];
        } else {
            winner = players[1];
        }
        System.out.println();
        System.out.println(winner.getName() + ", you are the winner!");
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



    private boolean validateMove(String[] move){
        boolean valid = false;
        int colIndex = colToIndex(move[0]);
        int rowIndex = rowToIndex(move[1]);
        if(colIndex >= 0 && colIndex <= 7 && rowIndex >=0 && rowIndex <= 7){
            valid = true;
        }
        return valid;
    }

    private int rowToIndex(String row){
        return Integer.parseInt(row) - 1;
    }

    private int colToIndex(String col){
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

    private void changeN(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex - 1][colIndex].equals(opponentPiece)){
                    valid = true;
                    rowIndex --;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex - 1][colIndex].equals(playerPiece)){
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

    private void changeNE(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex - 1][colIndex + 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex --;
                    colIndex ++;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex - 1][colIndex + 1].equals(playerPiece)){
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

    private void changeE(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex][colIndex + 1].equals(opponentPiece)){
                    valid = true;
                    colIndex ++;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex][colIndex + 1].equals(playerPiece)){
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

    private void changeSE(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex + 1][colIndex + 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex ++;
                    colIndex ++;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex + 1][colIndex + 1].equals(playerPiece)){
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

    private void changeS(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex + 1][colIndex].equals(opponentPiece)){
                    valid = true;
                    rowIndex ++;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex + 1][colIndex].equals(playerPiece)){
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

    private void changeSW(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex + 1][colIndex - 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex ++;
                    colIndex --;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex + 1][colIndex - 1].equals(playerPiece)){
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

    private void changeW(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex][colIndex - 1].equals(opponentPiece)){
                    valid = true;
                    colIndex --;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex][colIndex - 1].equals(playerPiece)){
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

    private void changeNW(int rowIndex, int colIndex){
        boolean valid = false;
        List<int[]> potentialChanges = new ArrayList<>();
        potentialChanges.add(new int[]{rowIndex, colIndex});
        boolean checked = false;
        while (!checked){
            try{
                if (this.board[rowIndex - 1][colIndex - 1].equals(opponentPiece)){
                    valid = true;
                    rowIndex --;
                    colIndex --;
                    potentialChanges.add(new int[]{rowIndex, colIndex});
                } else if (this.board[rowIndex - 1][colIndex - 1].equals(playerPiece)){
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

    private void calculateChanges(int rowIndex, int colIndex){
        changeN(rowIndex, colIndex);
        changeNE(rowIndex, colIndex);
        changeE(rowIndex, colIndex);
        changeSE(rowIndex, colIndex);
        changeS(rowIndex, colIndex);
        changeSW(rowIndex, colIndex);
        changeW(rowIndex, colIndex);
        changeNW(rowIndex, colIndex);
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

    private void saveGame(){}

    void loadGame(){}
}
