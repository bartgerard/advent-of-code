package be.gerard.aoc.util.vector;

import be.gerard.aoc.util.geometry.Point2d;
import be.gerard.aoc.util.geometry.Vector2d;

public record Ray2d(
        Point2d point,
        Vector2d direction
) {
    public Point2d end() {
        return point.add(direction);
    }
}
