public class Ocean {
    private Ship[][] ships;
    private int shotsFired;
    private int hitCount;
    private int shipsSunk;
    private int remainingShips;

    public Ocean() {
        this.ships = new Ship[20][20];
        this.shotsFired = 0;
        this.hitCount = 0;
        this.shipsSunk = 0;
        this.remainingShips = 0;
    }

    public void placeAllShipsRandomly() {
        // TODO write method to place ships randomly
    }

    public boolean isOccupied(int row, int column) {
        // TODO write method for isOccupied
        return false;
    }

    public boolean shootAt(int row, int column) {
        // TODO write method for shootAt
        System.out.println("TEST");
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

    public boolean isGameOver() {
        // TODO if all ships are sunk return true
        return false;
    }

    public void print() {
        // TODO ocean print of grid with numbers for rows and cols
    }
}
