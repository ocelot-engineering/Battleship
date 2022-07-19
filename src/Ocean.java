import java.util.ArrayList;
import java.util.Random;

public class Ocean {
    private Ship[][] ships;                   // the ocean the ships are placed in
    private Ship[] shipDirectory;             // stores the state of each ship
    private int shotsFired;                   // this also functions as an index in the shot queue
    private int hitCount;                     // number of unique hits taken by ships
    private int shipsSunk;                    // number of ships sunk where all sections are hit
    private int remainingShips;               // number of remaining ships on board, including those that are partially hit
    private ArrayList<Coordinates> shotQueue; // list of all shots taken by player
    private boolean forceQuit;

    public Ocean() {
        this.forceQuit = false;
        this.ships = new Ship[20][20];
        fillWithEmptySea();
        this.shipDirectory = new Ship[13];
        this.shotsFired = 0;
        this.hitCount = 0;
        this.shipsSunk = 0;
        this.remainingShips = 0;
        this.shotQueue = new ArrayList<>();
    }

    /**
     * Run straight after creating the ships array. It fills the ships array with empty ocean objects, some of which
     * will be overwritten with ship objects.
     */
    public void fillWithEmptySea() {
        for (int row = 0; row < this.size(); row++) {
            for (int col = 0; col < this.size(); col++) {
                EmptySea emptySea = new EmptySea();
                emptySea.placeShipAt(row, col, true, this);
            }
        }
    }

    /**
     * Creates 13 ships and places them in the ship directory
     * Ship directory is useful for understand status of ship (sunk, hit, etc)
     * Then places these ships randomly all over the 20x20 board.
     */
    public void placeAllShipsRandomly() {
        shipDirectory[0] = new BattleShip();
        shipDirectory[1] = new BattleCruiser();
        shipDirectory[2] = new Cruiser();
        shipDirectory[3] = new Cruiser();
        shipDirectory[4] = new LightCruiser();
        shipDirectory[5] = new LightCruiser();
        shipDirectory[6] = new Destroyer();
        shipDirectory[7] = new Destroyer();
        shipDirectory[8] = new Destroyer();
        shipDirectory[9] = new Submarine();
        shipDirectory[10] = new Submarine();
        shipDirectory[11] = new Submarine();
        shipDirectory[12] = new Submarine();

        for (Ship ship : shipDirectory) {
            placeShipRandomly(ship);
        }
    }

    /**
     * Pass in a ship object and have it randomly placed in the ocean in a legal area.
     * @param ship a shipping object such as submarine, destroyer, etc.
     */
    private void placeShipRandomly(Ship ship) {

        Coordinates coords = Coordinates.generateRandom();
        Random rand = new Random();
        boolean horizontal = rand.nextBoolean();

        if (!ship.okToPlaceShipAt(coords.x, coords.y, horizontal, this)) {
            placeShipRandomly(ship); // try again (assumes there is always a way to place a ship so there is no limit on attempts)
        } else {
            ship.placeShipAt(coords.x, coords.y, horizontal, this);
            this.setRemainingShips(this.getRemainingShips() + 1);
        }
    }

    /**
     * Checks if ships array cell is occupied by a ship and not empty ocean.
     *
     * @param row row index of ships array
     * @param column column index of ships array
     * @return true if array spot is occupied by anything but empty ocean, otherwise false.
     */
    public boolean isOccupied(int row, int column) {
        return !this.getShipArray()[row][column].getShipType().equals("empty"); // if not empty then is occupied.
    }

    public boolean shootAt(int row, int column) {
        this.incShotsFired();
        if (this.isOccupied(row, column) && !this.getShipArray()[row][column].toString().equals(BattleshipGame.SHIP_SUNK)) {
            if (!this.inProcessedShotQueue(new Coordinates(row, column), false)) { // has not shot here yet
                this.incHitCount();
                this.getShipArray()[row][column].shootAt(row, column);
            }
            return true;
        }
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

    public void forceQuit() {
        this.forceQuit = true;
    }

    public boolean isGameOver() {
        return remainingShips <= 0 || forceQuit;
    }

    /**
     * Ocean print of grid with numbers for rows and cols.
     */
    public void print() {
        printFor(false);
    }

    /**
     * Prints the ocean in the console with headers, footers and row and column indexes.
     * Allows to mask shipping locations from player if they have not been shot at yet.
     * @param forPlayer true if view is for player, false if for admin
     */
    public void printFor(boolean forPlayer) {
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
                String output = this.ships[row][col].toString();
                boolean hasShotHere = this.inProcessedShotQueue(new Coordinates(row, col), true);

                if (forPlayer && !hasShotHere) {
                    output = BattleshipGame.SHIP_UNKNOWN; // hide everything that hasn't been shot
                }

                System.out.print(padRight(output));
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

    /**
     * Shows if a shot has been taken at a location or not.
     *
     * @param coordinates x,y coords
     * @return true if shot has been taken here before, otherwise false
     */
    private boolean inProcessedShotQueue(Coordinates coordinates, boolean includeLatest) {
        if (this.shotQueue.size() <= 0) {
            return false;
        }

        int endOfQueueIndex = this.shotsFired;
        if (!includeLatest) {
            endOfQueueIndex--;
        }

        for (Coordinates shot : this.shotQueue.subList(0, endOfQueueIndex)) { // only look at processed ones
            if (shot.equals(coordinates)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes unprocessed shot coordinates from the player and actions them.
     */
    public void processShotQueue() {
        if (shotsFired >= shotQueue.size()) { return; }     // return when entire queue is processed
        Coordinates shotCoords = shotQueue.get(shotsFired); // pull coords from queue
        this.shootAt(shotCoords.x, shotCoords.y);           // shoot at coords and increments shot queue index (shots fired)
        this.checkSunkShips();
        processShotQueue();                                 // process next iteration
    }

    private void checkSunkShips() {
        int sunkCount = 0;
        for (Ship ship : this.shipDirectory) {
            if (ship.isSunk()) {
                sunkCount++;
            }
        }
        this.setShipsSunk(sunkCount);
        setRemainingShips(this.shipDirectory.length - sunkCount);
    }

    /**
     * Prints a history of all shots taken by player shown as coordinates.
     */
    public void printShotHistory() {
        for (Coordinates shot : shotQueue) {
            System.out.println(shot);
        }
    }

    public int size() {
        return ships.length;
    }
}
