public class LightCruiser extends Ship {
    public LightCruiser() {
        this.setLength(5);
    }

    @Override
    String getShipType() {
        return "lightcruiser";
    }
}
