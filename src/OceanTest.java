import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OceanTest {
    private Ocean ocean;
    private BattleShip testShip;

    @BeforeEach
    void setUp() {
        ocean = new Ocean();
        testShip = new BattleShip();
        testShip.placeShipAt(0, 0, true, ocean); // testShip is 8-square size (battleship)
        ocean.setRemainingShips(1);
    }

    @Test
    void isOccupied() {
        for (int col = 0; col < testShip.getLength(); col++) {
            assertTrue(ocean.isOccupied(0, col));
        }
        assertFalse(ocean.isOccupied(0, testShip.getLength()));
        assertFalse(ocean.isOccupied(1, 0));
    }

    @Test
    void shootAt() {
        // ship expected
        Coordinates hitCoords = new Coordinates(0, 0);
        assertEquals(BattleshipGame.SHIP_PRESENT, ocean.getShipArray()[hitCoords.x][hitCoords.y].toString());
        assertTrue(ocean.shootAt(hitCoords.x, hitCoords.y));

        // no ship expected
        Coordinates missCoords = new Coordinates(1, 1);
        assertEquals(BattleshipGame.SHIP_MISSING, ocean.getShipArray()[missCoords.x][missCoords.y].toString());
        assertFalse(ocean.shootAt(missCoords.x,missCoords.y));
    }

    @Test
    void getAndIncShotsFired() {
        assertEquals(0, ocean.getShotsFired());
        ocean.shootAt(0, 0);
        assertEquals(1, ocean.getShotsFired());
        ocean.shootAt(1, 1);
        assertEquals(2, ocean.getShotsFired());
    }

    @Test
    void getAndIncHitCount() {
        assertEquals(0, ocean.getHitCount());
        ocean.shootAt(0, 0);
        assertEquals(1, ocean.getHitCount());
        ocean.shootAt(1, 1);
        assertEquals(1, ocean.getHitCount());
    }

    @Test
    void getAndSetShipsSunk() {
        assertEquals(0, ocean.getShipsSunk());
        ocean.setShipsSunk(1);
        assertEquals(1, ocean.getShipsSunk());
        ocean.setShipsSunk(11);
        assertEquals(11, ocean.getShipsSunk());
    }

    @Test
    void getShipArray() {
        assertInstanceOf(Ship[][].class, ocean.getShipArray());
        assertEquals(20, ocean.getShipArray().length);
    }

    @Test
    void isGameOver() {
        assertFalse(ocean.isGameOver());
        ocean.setRemainingShips(0);
        assertTrue(ocean.isGameOver());
    }
}