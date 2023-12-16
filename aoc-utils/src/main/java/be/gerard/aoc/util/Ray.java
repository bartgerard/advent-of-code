package be.gerard.aoc.util;

public interface Ray {
    static Ray2d of(
            final Point2d point,
            final Vector2d direction
    ) {
        return new Ray2d(point, direction);
    }
}
