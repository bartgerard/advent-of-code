package be.gerard.aoc.util;

public record Point2d(
        int x,
        int y
) implements Point {
    public Point2d add(final Vector2d vector) {
        return Point.of(
                x + vector.x(),
                y + vector.y()
        );
    }

    public Vector2d towards(final Point2d point) {
        return Vector.of(
                point.x() - x,
                point.y() - y
        );
    }
}
