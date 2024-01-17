package be.gerard.aoc.util.geometry;

import be.gerard.aoc.util.spatial.Direction;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public record Line(
        Point2d p1,
        Point2d p2
) {
    public static Line of(
            final Point2d p1,
            final Point2d p2
    ) {
        return new Line(p1, p2);
    }

    private static Direction toDirection(final Point2d point, final Point2d otherPoint) {
        if (point.x() < otherPoint.x()) {
            return Direction.RIGHT;
        } else if (otherPoint.x() < point.x()) {
            return Direction.LEFT;
        } else if (point.y() < otherPoint.y()) {
            return Direction.DOWN;
        } else {
            return Direction.UP;
        }
    }

    public boolean contains(
            final Point2d point
    ) {
        return Objects.equals(p1, point) || Objects.equals(p2, point);
    }

    public boolean containsX(
            final long x
    ) {
        return Long.min(p1.x(), p2.x()) <= x && x <= Long.max(p1.x(), p2.x());
    }

    public boolean containsY(
            final long y
    ) {
        return Long.min(p1.y(), p2.y()) <= y && y <= Long.max(p1.y(), p2.y());
    }

    public List<Direction> directionsFrom(
            final Point2d point
    ) {
        return Stream.concat(
                        Objects.equals(p1, point) ? Stream.empty() : Stream.of(toDirection(point, p1)),
                        Objects.equals(p2, point) ? Stream.empty() : Stream.of(toDirection(point, p2))
                )
                .toList();
    }
}
