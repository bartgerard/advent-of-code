package be.gerard.aoc.util.vector;

import be.gerard.aoc.util.geometry.Point2d;
import be.gerard.aoc.util.geometry.Vector2d;

public interface Ray {
    static Ray2d of(
            final Point2d point,
            final Vector2d direction
    ) {
        return new Ray2d(point, direction);
    }
}
