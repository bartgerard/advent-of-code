package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.math.IntRange;
import be.gerard.aoc.util.math.Line;
import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.spatial.Direction;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static be.gerard.aoc.util.matrix.Corners.State.IN;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toUnmodifiableSet;

public record InfiniteGrid(
        List<Line> lines
) {
    public static InfiniteGrid of(
            final List<Line> lines
    ) {
        return new InfiniteGrid(lines);
    }

    public long volume() {
        final Map<IntRange, Set<Line>> linesByRange = lines.stream()
                .collect(groupingBy(
                        line -> IntRange.between(
                                line.p1().y(),
                                line.p2().y()
                        ),
                        toUnmodifiableSet()
                ));
        final List<IntRange> rowRegions = IntRange.allIntersections(linesByRange.keySet());

        return rowRegions.stream()
                .mapToLong(region -> {
                    final List<Line> linesWithinRegion = linesByRange.entrySet()
                            .stream()
                            .filter(entry -> region.overlaps(entry.getKey()))
                            .map(Map.Entry::getValue)
                            .flatMap(Set::stream)
                            .toList();

                    return volumeForRegion(region, linesWithinRegion);
                })
                .sum();
    }

    private long volumeForRegion(
            final IntRange region,
            final Collection<Line> linesWithinRegion
    ) {
        final List<IntRange> columnRanges = linesWithinRegion.stream()
                .map(line -> IntRange.between(
                        line.p1().x(),
                        line.p2().x()
                ))
                .toList();
        final List<IntRange> columnIntersections = IntRange.allIntersections(columnRanges);

        Corners previousState = Corners.OUTSIDE;
        long volumeForOneRow = 0;

        for (final IntRange columnIntersection : columnIntersections) {
            final Point2d destination = Point.of(columnIntersection.toInclusive(), region.toInclusive());

            final List<Direction> destinationDirections = linesWithinRegion.stream()
                    .filter(line -> line.containsX(columnIntersection.toInclusive()))
                    .map(line -> line.directionsFrom(destination))
                    .flatMap(List::stream)
                    .toList();

            final Corners nextState = destinationDirections.isEmpty()
                    ? previousState.shiftLeft()
                    : previousState.whenCrossing(destinationDirections);

            if (nextState.topLeft() == IN || nextState.bottomLeft() == IN) {
                volumeForOneRow += columnIntersection.length();
            } else if (!nextState.isOutside()) {
                volumeForOneRow += 1;
            }

            previousState = nextState;
        }

        return region.length() * volumeForOneRow;
    }

}
