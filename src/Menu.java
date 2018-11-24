import java.util.Scanner;
import java.io.*;

public class Menu {

    private Game game;

    public static void main(String[] args){
        Menu menu = new Menu();
        menu.processChoices();
    }

    private void displayMenu(){
        readFile("src/logo.txt", "Reversi");
        System.out.println();
        System.out.println("Menu");
        System.out.println();
        System.out.println("1. New Player vs Player");
        System.out.println("2. New Player vs AI Game");
        System.out.println("3. Load game");
        System.out.println("4. Rules of Reversi");
        System.out.println("0. Exit");
    }

    private void readFile(String source, String errorMsg){
        try{
            FileReader fileReader = new FileReader(source);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String nextFileLine = bufferedReader.readLine();

            // prints every line in file to console
            while (nextFileLine != null) {
                System.out.println(nextFileLine);
                nextFileLine = bufferedReader.readLine();
            }

            bufferedReader.close();
        } catch (IOException e){
            System.out.println(errorMsg);
        }
    }

    private void processChoices(){
        boolean finished = false; // set to true when the user wants to exit to break the loop
        Scanner menuScanner = new Scanner(System.in);
        while (!finished) { // keeps going while the user hasn't chosen to exit
            displayMenu();
            String userChoice = menuScanner.nextLine();
            switch (userChoice) {
                case "1": // User chooses option 1
                    System.out.println("Player 1, enter your name:");
                    String p1Name = menuScanner.nextLine();
                    System.out.println("Player 2, enter your name:");
                    String p2Name = menuScanner.nextLine();
                    game = new Game(p1Name, p2Name, false);
                    game.playGame();
                    break;
                case "2": // User chooses option 2
                    System.out.println("Player 1, enter your name:");
                    p1Name = menuScanner.nextLine();
                    game = new Game(p1Name, "CPU", true);
                    game.playGame();
                    break;
                case "3": // User chooses option 3
                    loadGame();
                    break;
                case "4": // User chooses option 4
                    readFile("src/rules.txt", "Error reading rules.");
                    break;
                case "0": // User chooses to exit

                    finished = true; // Big while loop that this is in will now break.
                    break;
                default:
                    System.out.println("That input was invalid, please try again.");
                    break;
            }
        }
    }

    private void loadGame(){
        Scanner loadScanner = new Scanner(System.in);
        System.out.println("Enter the save name:");
        String saveName = loadScanner.nextLine();

        try{
            FileInputStream inputStream = new FileInputStream(new File(saveName));
            ObjectInputStream objectInput = new ObjectInputStream(inputStream);

            game = (Game) objectInput.readObject();
            game.playGame();

            inputStream.close();
            objectInput.close();
        } catch (IOException e){
            System.out.println("Error (IOException). Please try again.\n If you've just saved your game, make sure to restart the program first.");
        } catch (ClassNotFoundException e){
            System.out.println("Error (ClassNotFoundException). Please try again.");
        }
    }
}

