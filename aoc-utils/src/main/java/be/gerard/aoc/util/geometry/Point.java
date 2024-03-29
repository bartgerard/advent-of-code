package be.gerard.aoc.util.geometry;

public interface Point {
    static Point2d of(
            final int x,
            final int y
    ) {
        return new Point2d(x, y);
    }
}
