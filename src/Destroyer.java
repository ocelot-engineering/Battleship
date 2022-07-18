public class Destroyer extends Ship {
    public Destroyer() {
        this.setLength(4);
    }

    @Override
    String getShipType() {
        return "destroyer";
    }
}
