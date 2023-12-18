package be.gerard.aoc2023.day18;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.math.Line;
import be.gerard.aoc.util.matrix.FiniteGrid;
import be.gerard.aoc.util.matrix.InfiniteGrid;
import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.vector.Vector;
import be.gerard.aoc.util.vector.Vector2d;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

record Lagoon(
        List<DigStep> digPlan
) {
    static Lagoon parse(final Lines<String> lines) {
        return lines.map(DigStep::parse).as(Lagoon::new);
    }

    static Lagoon parseHexadecimal(final Lines<String> lines) {
        return lines.map(DigStep::parseHexadecimal).as(Lagoon::new);
    }

    long volume() {
        final List<Line> trenches = trenches();
        final InfiniteGrid grid = InfiniteGrid.of(trenches);
        return grid.volume();
    }

    private List<Line> trenches() {
        final List<Line> trenches = new ArrayList<>();
        Point2d previousPoint = Point.of(0, 0);

        for (final DigStep digStep : digPlan) {
            final Vector2d vector = Vector.in(digStep.direction()).dot(digStep.distance());
            final Point2d nextPoint = previousPoint.add(vector);

            trenches.add(Line.of(previousPoint, nextPoint));
            previousPoint = nextPoint;
        }

        return unmodifiableList(trenches);
    }
}
