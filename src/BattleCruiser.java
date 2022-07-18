public class BattleCruiser extends Ship {
    public BattleCruiser() {
        this.setLength(7);
    }

    @Override
    String getShipType() {
        return "battlecruiser";
    }
}
