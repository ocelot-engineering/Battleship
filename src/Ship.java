public abstract class Ship {
    private int bowRow;
    private int bowColumn;
    private int length;
    private boolean horizontal;
    private boolean[] hit = new boolean[8];

    abstract String getShipType();

    /**
     * Checks if ship is allowed to be placed in a certain location but ensuring it fits on the board and doesn't clash
     * with other ships.
     *
     * @param row row index
     * @param column column index
     * @param horizontal whether the ship is horizontal or not
     * @param ocean an ocean object that the game is being played on
     * @return returns true is a ship can be placed in the ocean in that location. Meaning it is not off the board or
     * clashing with other ships.
     */
    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {

        if (column + this.length > ocean.size() || row + this.length > ocean.size()) {
            return false;
        }

        if (horizontal) {
            for (int c = column; c < column + this.length; c++) {
                if (ocean.isOccupied(row, c)) {
                    return false;
                }
            }
        } else {
            for (int r = row; r < row + this.length; r++) {
                if (ocean.isOccupied(r, column)) {
                    return false;
                }
            }
        }
        return true; // no ships found during scan, return true as ship is ok to place
    }

    /**
     * Updates the ocean object to contain the ship object in the ocean at a given row, column.
     * @param row row index of ocean
     * @param column column index of ocean
     * @param horizontal true if ship is horizontal, false if vertical
     * @param ocean ocean object that the game is being played on
     */
    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        setBowRow(row);
        setBowColumn(column);
        setHorizontal(horizontal);

        if (horizontal) { // place in row
            for (int c = column; c < column + this.length; c++) {
                ocean.setShipArrayCell(row, c, this);
            }
        } else { // place in column
            for (int r = row; r < row + this.length; r++) {
                ocean.setShipArrayCell(r, column, this);
            }
        }
    }

    /**
     * Updates the hit tracking of the ship if ship is located at coordinates.
     * @param row row index ocean
     * @param column column index of ocean
     * @return true if a section of the ship was shot, otherwise false
     */
    public boolean shootAt(int row, int column) {
        int sectionOfShip = getSectionOfShip(row, column);
        if (sectionOfShip < 0) {
            return false;
        }

        this.updateHit(sectionOfShip);
        return true;
    }

    /**
     * Returns an integer representing the section of the ship where 0 is the bow, and 2 is 2 places from bow etc
     * -1 is returned when row,col has no current ship present (it is possible other ships could be located there, but
     * not this ship).
     * @param row row index of ocean
     * @param col column index of ocean
     * @return 0 for bow, -1 where row,col is not part of the ship.
     */
    private int getSectionOfShip(int row, int col) {
        if (this.isHorizontal() && this.getBowRow() == row) { // is horizontal and in the row selected
            if (col >= this.getBowColumn() && col <= this.getBowColumn() + this.getLength()) { // is the column between the bow and end of ship
                return col - this.getBowColumn();
            }
        } else if (!this.isHorizontal() && this.getBowColumn() == col) {
            if (row >= this.getBowRow() & row <= this.getBowRow() + this.getLength()) {
                return row - this.getBowRow();
            }
        }
        return -1; // not part of the ship
    }

    /**
     * A method that is used to print ships.
     * This should only be used to print ships that have been fired upon.
     */
    public String toString() {
        if (isSunk()) {
            return BattleshipGame.SHIP_SUNK;
        } else {
            return BattleshipGame.SHIP_PRESENT;
        }
    }

    public int getBowRow() {
        return bowRow;
    }

    public void setBowRow(int bowRow) {
        this.bowRow = bowRow;
    }

    public int getBowColumn() {
        return bowColumn;
    }

    public void setBowColumn(int bowColumn) {
        this.bowColumn = bowColumn;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public boolean[] getHit() {
        return hit;
    }

    /**
     * Updates the hit tracker of the ship.
     * @param location number of places from the bow the ship was hit. E.g. 0 if the bow was hit.
     */
    public void updateHit(int location) throws RuntimeException {
        if (location > this.getLength()) {
            throw new RuntimeException("Length of ship is:"   + this.getLength() + "\n" +
                                       "Location of hit is: " + location         + "\n" +
                                       "Therefore location of hit on ship longer than length of ship.");
        }
        this.hit[location] = true;
    }

    /**
     * Gets number of hits on ship.
     * @return integer of number of unique hits taken on ship
     */
    private int getHitCount() {
        int hitCount = 0;

        for (boolean sectionHit : this.getHit()) {
            if (Boolean.TRUE.equals(sectionHit)) { // avoids extra null checks
                hitCount++;
            }
        }
        return hitCount;
    }

    /**
     * Return true of every part of ship has been hit.
     */
    public boolean isSunk() {
        return this.getHitCount() >= this.getLength();
    }
}
