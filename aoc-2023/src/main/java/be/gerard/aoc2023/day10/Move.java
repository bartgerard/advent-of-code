package be.gerard.aoc2023.day10;

import be.gerard.aoc.util.Point2d;

record Move(
        Point2d point,
        Direction sourceDirection
) {
    static Move of(
            final Point2d point,
            final Direction sourceDirection
    ) {
        return new Move(point, sourceDirection);
    }
}
