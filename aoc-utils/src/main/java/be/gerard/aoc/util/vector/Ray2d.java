package be.gerard.aoc.util.vector;

import be.gerard.aoc.util.point.Point2d;

public record Ray2d(
        Point2d point,
        Vector2d direction
) {
    public Point2d end() {
        return point.add(direction);
    }
}
