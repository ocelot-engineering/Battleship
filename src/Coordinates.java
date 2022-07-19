import java.util.concurrent.ThreadLocalRandom;

public class Coordinates {
    public int x;
    public int y;
    private final int xMax = 19;
    private final int yMax = 19;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinates generateRandom() {
        return random(19, 19);
    }

    public static Coordinates generateRandom(int maxBound) {
        return random(maxBound, maxBound);
    }

    public static Coordinates random(int xMax, int yMax) {
        int x = ThreadLocalRandom.current().nextInt(0, xMax + 1);
        int y = ThreadLocalRandom.current().nextInt(0, yMax + 1);
        return new Coordinates(x, y);
    }

    @Override
    public String toString() {
        return "x:" + this.x + ", y:" + this.y;
    }

    public boolean equals(Coordinates coordinates) {
        return (coordinates.x == this.x) && (coordinates.y == this.y);
    }
}
