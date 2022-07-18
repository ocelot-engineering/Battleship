public class Cruiser extends Ship {
    public Cruiser() {
        this.setLength(6);
    }

    @Override
    String getShipType() {
        return "cruiser";
    }
}
