import java.util.ArrayList;
import java.util.Random;

public class Ocean {
    private Ship[][] ships;
    private int shotsFired;
    private int hitCount;
    private int shipsSunk; // this also functions as an index in the shot queue
    private int remainingShips;
    private ArrayList<Coordinates> shotQueue;

    public Ocean() {
        this.ships = new Ship[20][20];
        fillWithEmptySea();
        print();
        this.shotsFired = 0;
        this.hitCount = 0;
        this.shipsSunk = 0;
        this.remainingShips = 0;
        this.shotQueue = new ArrayList<>();
    }

    public void fillWithEmptySea() {
        for (int row = 0; row < this.size(); row++) {
            for (int col = 0; col < this.size(); col++) {
                EmptySea emptySea = new EmptySea();
                emptySea.placeShipAt(row, col, true, this);
            }
        }
    }

    public void placeAllShipsRandomly() {
        placeShipRandomly(new BattleShip());
        placeShipRandomly(new BattleCruiser());
        placeShipRandomly(new Cruiser());
        placeShipRandomly(new LightCruiser());
        placeShipRandomly(new Destroyer());
        placeShipRandomly(new Submarine());
    }

    private void placeShipRandomly(Ship ship) {

        Coordinates coords = Coordinates.generateRandom();
        Random rand = new Random();
        boolean horizontal = rand.nextBoolean();

        if (!ship.okToPlaceShipAt(coords.x, coords.y, horizontal, this)) {
            placeShipRandomly(ship); // try again (assumes there is always a way to place a ship so there is no limit on attempts)
        }
        ship.placeShipAt(coords.x, coords.y, horizontal, this);
    }

    public boolean isOccupied(int row, int column) {
        return !this.ships[row][column].getShipType().equals("empty"); // if not empty then is occupied.
    }

    public boolean shootAt(int row, int column) {
        // TODO write method for shootAt
        System.out.println("TEST");
        incShotsFired();
        return false;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public void incShotsFired() {
        this.shotsFired++;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void incHitCount() {
        this.hitCount++;
    }

    public int getShipsSunk() {
        return shipsSunk;
    }

    public void setShipsSunk(int shipsSunk) {
        this.shipsSunk = shipsSunk;
    }

    public int getRemainingShips() {
        return this.remainingShips;
    }

    public void setRemainingShips(int remainingShips) {
        this.remainingShips = remainingShips;
    }

    public Ship[][] getShipArray() {
        return this.ships;
    }

    public void setShipArrayCell(int row, int col, Ship ship) {
        this.ships[row][col] = ship;
    }

    public ArrayList<Coordinates> getShotQueue() {
        return this.shotQueue;
    }

    public void addToShotQueue(Coordinates coord) {
        shotQueue.add(coord);
    }

    public boolean isGameOver() {
        // TODO if all ships are sunk return true
        return false;
    }

    /**
     * Ocean print of grid with numbers for rows and cols
     */
    public void print() {
        // Header with title
        System.out.println();
        System.out.println(battleShipTitle());

        // Top row numbers for board
        System.out.print(padRight(""));
        for (int x = 0; x < this.size(); x++) {
            System.out.print(padRight(Integer.toString(x))); // padRight used here since toString
        }
        // Ships array
        for (int row = 0; row < this.size(); row++) {
            System.out.println();
            System.out.print(padRight(padRight(Integer.toString(row))));
            for (int col = 0; col < this.size(); col++) {
                System.out.print(padRight(this.ships[row][col].toString()));
            }
        }

        // Footer with title
        System.out.println();
        System.out.println(battleShipTitle());
    }

    private static String battleShipTitle() {
        return "|- - - - - - - - - - B A T T L E S H I P - - - - - - - - - -|";
    }

    public static String padRight(String s) {
        return String.format("%-" + 3 + "s", s);
    }

    public void processShotQueue() {
        if (shotsFired >= shotQueue.size()) { return; }     // return when entire queue is processed
        Coordinates shotCoords = shotQueue.get(shotsFired); // pull coords from queue
        shootAt(shotCoords.x, shotCoords.y);                    // shoot at coords and increments shot queue index (shots fired)
        processShotQueue();                                     // process next iteration
    }

    public void printShotHistory() {
        for (Coordinates shot : shotQueue) {
            System.out.println(shot);
        }
    }

    public int size() {
        return ships.length;
    }
}
