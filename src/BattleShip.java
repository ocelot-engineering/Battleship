public class BattleShip extends Ship {
    @Override
    String getShipType() {
        return "battleship";
    }

    @Override
    public int getLength() {
        return 8;
    }
}
