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
    public static final String SHIP_PRESENT = "s";
    public static final String SHIP_HIT = "S";
    public static final String SHIP_SUNK = "x";
    public static final String SHIP_MISSING = "-";
    public static final String SHIP_UNKNOWN = ".";

    public BattleshipGame() {
    }

    public static void main(String[] args) {
        // TODO main method in game
        System.out.println("Welcome to Battleship!");
        System.out.println("This game will be played on a 20x20 grid.");
        System.out.println("Coordinates should separated by a semicolon: 1, 1; 0, 3; 7, 3; 9, 11; 12, 17");

        Ocean ocean = new Ocean();

        while (ocean.getRemainingShips() > 0) {
            inputShots(ocean);
            ocean.processShotQueue();
        }


        // accept shots from user
        // print grid
        // print final scores
        // ask if user wants to play again
        // all computation done in ocean and ship classes

    }

    /**
     * Read shots input from user and parse into coordinates.
     * These coordinates will be added to the queue and processed one at a time.
     */
    private static void inputShots(Ocean ocean) {
        // build coords from string
        // The input format should look like this: 1, 1; 0, 3; 7, 3; 9, 11; 12, 17

        Coordinates coord; // single coord
        String[] shots; // array of shots
        String[] shot; // single shot (array of x and y) coords

        Scanner scanner = new Scanner(System.in);
        System.out.println("Input 5 coordinates:");
        shots = scanner.nextLine().split(";");

        // Print previous shots by writing previous
        if (shots[0].matches("history")) {
            ocean.printShotHistory();
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
            shot = shotString.split(", ");
            coord = new Coordinates(Integer.parseInt(shot[0]), Integer.parseInt(shot[1]));
            ocean.addToShotQueue(coord);
        }
    }
}
