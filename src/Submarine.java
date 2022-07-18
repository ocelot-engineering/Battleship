public class Submarine extends Ship {
    public Submarine() {
        this.setLength(3);
    }

    @Override
    String getShipType() {
        return "submarine";
    }
}
