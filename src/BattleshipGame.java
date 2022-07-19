import java.util.Arrays;
import java.util.Scanner;

/**
 * BattleshipGame class is concerned mostly with user input and running the game.
 * But most of the computation is done in Ocean, BattleshipGame will call ocean objects to correctly run the game.
 */
public class BattleshipGame {
    // Game config
    private static final int MAX_SHOTS = 5;
    private static final int MIN_SHOTS = 1;

    // Symbols for grid
    public static final String SHIP_PRESENT = "S";
    public static final String SHIP_SUNK = "x";
    public static final String SHIP_MISSING = "-";
    public static final String SHIP_UNKNOWN = ".";

    // Commands (regex patterns)
    public static final String QUIT = "(quit|q|stop|end)";
    public static final String SCORE = "(score|points|remaining|s)";
    public static final String HELP = "(help|h)";
    public static final String SHOT_HISTORY = "(history|hist|shots)";
    public static final String PLAY_AGAIN = "(yes|y)";
    public static final String CHEAT = "(peak|god-mode|cheat|iddqd|idkfa)";

    public BattleshipGame() {
    }

    public static void main(String[] args) {
        // Start game, initiate objects
        System.out.println("Welcome to Battleship!");
        System.out.println("This game will be played on a 20x20 grid.");
        System.out.println("Coordinates should separated by a semicolon: 1, 1; 0, 3; 7, 3; 9, 11; 12, 17");

        Ocean ocean = new Ocean();
        ocean.placeAllShipsRandomly();
        System.out.println(ocean.getRemainingShips() + " ships remain...");

        // Play game
        while (!ocean.isGameOver()) {
            ocean.printFor(true);
            inputShots(ocean);
            ocean.processShotQueue();
        }

        // Game over
        System.out.println("Game over.");
        ocean.printFor(false);
        printScore(ocean);

        // Play again
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to play again?");
        if (scanner.nextLine().matches(PLAY_AGAIN)) {
            main(args);
        }
    }

    /**
     * Read shots input from user and parse into coordinates.
     * These coordinates will be added to the queue and processed one at a time.
     */
    private static void inputShots(Ocean ocean) {
        // The input format should look like this: 1, 1; 0, 3; 7, 3; 9, 11; 12, 17

        Coordinates coord; // single coord
        String[] shots; // array of shots
        String[] shot; // single shot (array of x and y) coords

        // Take input from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Input up to 5 coordinates:");
        shots = scanner.nextLine().split("(;| ;|; )");

        // Print previous shots by writing previous
        if (shots[0].toLowerCase().matches(SHOT_HISTORY)) {
            ocean.printShotHistory();
            return;
        }

        // Print scores
        if (shots[0].toLowerCase().matches(SCORE)) {
            printScore(ocean);
            return;
        }

        // Quit game
        if (shots[0].toLowerCase().matches(QUIT)) {
            forceGameEnd(ocean);
            return;
        }

        // Help
        if (shots[0].toLowerCase().matches(HELP)) {
            help();
            return;
        }

        // Cheat game and peak at board
        if (shots[0].toLowerCase().matches(CHEAT)) {
            ocean.printFor(false);
            return;
        }

        if (shots.length < MIN_SHOTS) {
            System.out.println(shots.length + " is not enough shots. Please input up to " + MAX_SHOTS + " shots.");
            return;
        }

        if (shots.length > MAX_SHOTS) {
            System.out.println(shots.length + " is too many shots. Player is only allowed up to " + MAX_SHOTS + " shots.");
            System.out.println("Taking the first " + MAX_SHOTS + " shots.");
            shots = Arrays.copyOfRange(shots, 0, MAX_SHOTS);
        }

        for (String shotString: shots) {
            System.out.println(shotString);
            shot = shotString.split("(,| ,|, )");

            if (shot.length > 2) {
                System.out.println(shot.length + " dimensions is too many dimensions given for coordinates. Taking first two.");
            }

            try {
                coord = new Coordinates(
                        Integer.parseInt(shot[0].replaceAll(" ", "")),
                        Integer.parseInt(shot[1].replaceAll(" ", "")));
                ocean.addToShotQueue(coord);
            } catch (NumberFormatException e) {
                System.out.println("Cannot understand input. Please try again with format row,col;row,col; etc.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Either row or column was outside of play zone. Row and column should be less than" + ocean.size());
            }

        }
    }

    /*
    * Below are methods to print more information such as score and help.
    */
    private static void printScore(Ocean ocean) {
        System.out.println("Shots fired: " + ocean.getShotsFired());
        System.out.println("Ships sunk: " + ocean.getShipsSunk());
        System.out.println("Direct hits: " + ocean.getHitCount());
        System.out.println("Remaining ships: " + ocean.getRemainingShips());
    }

    private static void forceGameEnd(Ocean ocean) {
        ocean.forceQuit();
    }

    private static void help() {
        String regexCleaner = "([()|])";
        String regexReplacement = " ";
        System.out.println("Get help by typing any of:" + HELP.replaceAll(regexCleaner, regexReplacement));
        System.out.println("Get scores by typing any of:" + SCORE.replaceAll(regexCleaner, regexReplacement));
        System.out.println("Get shot history by typing any of:" + SHOT_HISTORY.replaceAll(regexCleaner, regexReplacement));
        System.out.println("Quit game by typing any of:" + QUIT.replaceAll(regexCleaner, regexReplacement));
        System.out.println("Cheat game by typing any of:" + CHEAT.replaceAll(regexCleaner, regexReplacement));
    }
}
