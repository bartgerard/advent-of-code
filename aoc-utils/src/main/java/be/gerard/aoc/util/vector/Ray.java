package be.gerard.aoc.util.vector;

import be.gerard.aoc.util.point.Point2d;

public interface Ray {
    static Ray2d of(
            final Point2d point,
            final Vector2d direction
    ) {
        return new Ray2d(point, direction);
    }
}
