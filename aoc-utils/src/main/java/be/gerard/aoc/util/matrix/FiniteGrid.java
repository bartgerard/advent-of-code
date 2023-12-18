package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.spatial.Direction;
import be.gerard.aoc.util.vector.Vector;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static be.gerard.aoc.util.matrix.Corners.State.IN;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toUnmodifiableSet;

public record FiniteGrid(
        Map<Integer, Set<Integer>> grid
) {
    public static FiniteGrid of(
            final List<Point2d> points
    ) {
        return new FiniteGrid(points.stream()
                .collect(groupingBy(
                        Point2d::y,
                        mapping(Point2d::x, toUnmodifiableSet())
                ))
        );
    }

    public long volume() {
        return grid.entrySet()
                .stream()
                .mapToLong(this::volumeForRow)
                .sum();
    }

    private long volumeForRow(final Map.Entry<Integer, Set<Integer>> entry) {
        final int row = entry.getKey();
        final List<Integer> columns = entry.getValue()
                .stream()
                .sorted()
                .toList();

        int previousColumn = Integer.MIN_VALUE;
        Corners previousState = Corners.OUTSIDE;
        long volume = 0;

        for (final int currentColumn : columns) {
            final Set<Direction> directions = directionsFrom(Point.of(currentColumn, row));
            final Corners currentState = previousState.whenCrossing(directions);

            if (currentState.topLeft() == IN || currentState.bottomLeft() == IN) {
                volume += currentColumn - previousColumn;
            } else {
                volume += 1;
            }

            previousColumn = currentColumn;
            previousState = currentState;
        }

        return volume;
    }

    public boolean contains(final Point2d point) {
        return contains(point.y(), point.x());
    }

    private boolean contains(
            final int row,
            final int column
    ) {
        return grid.containsKey(row) && grid.get(row).contains(column);
    }

    public Set<Direction> directionsFrom(final Point2d point) {
        if (!contains(point)) {
            return emptySet();
        }

        return Direction.ORTHOGONAL_DIRECTIONS
                .stream()
                .filter(direction -> contains(point.add(Vector.in(direction))))
                .collect(toUnmodifiableSet());
    }

}
