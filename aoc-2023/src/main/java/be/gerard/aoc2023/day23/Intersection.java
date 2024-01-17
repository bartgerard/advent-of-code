package be.gerard.aoc2023.day23;

import be.gerard.aoc.util.geometry.Point2d;

public record Intersection(
        Point2d previous,
        Point2d current,
        int length,
        boolean bidirectional
) {
}
