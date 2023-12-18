package be.gerard.aoc.util.point;

import be.gerard.aoc.util.spatial.Direction;
import be.gerard.aoc.util.vector.Vector;
import be.gerard.aoc.util.vector.Vector2d;

public record Point2d(
        int x,
        int y
) implements Point {
    public static long manhattanDistanceBetween(
            final Point2d p1,
            final Point2d p2
    ) {
        return Math.abs(p2.x() - p1.x()) + Math.abs(p2.y() - p1.y());
    }

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

    public Point2d go(final Direction direction) {
        return add(Vector.in(direction));
    }
}
