public abstract class Ship {
    private int bowRow;
    private int bowColumn;
    private int length;
    private boolean horizontal;
    private boolean[] hit;

    abstract String getShipType();

    public boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        // TODO write method to check if ship is ok to place
        return false;
    }

    public void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
        // TODO write method for place ship at
    }

    public boolean shootAt(int row, int column) {
        // TODO write method to shoot at ship
        return false;
    }

    public boolean isSunk() {
        // TODO write method to return true of every part of ship has been hit
        return false;
    }

    @Override
    public String toString() {
        // TODO write method to print ships
        return "x for sunk, S for not sunk, etc";
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

    public void setHit(boolean[] hit) {
        this.hit = hit;
    }
}
