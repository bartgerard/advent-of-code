package be.gerard.aoc2023.day10;

import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Point2d;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableSet;

record PipeMaze(
        char[][] tiles
) {
    static PipeMaze parse(final Lines<String> lines) {
        return new PipeMaze(
                lines.map(String::toCharArray).as(row -> row.toArray(char[][]::new))
        );
    }

    Optional<Point2d> findStart() {
        return IntStream.range(0, tiles.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, tiles[i].length)
                        .filter(j -> tiles[i][j] == 'S')
                        .mapToObj(j -> Point2d.of(j, i))
                )
                .findFirst();
    }

    int numberOfStepsToFarthestPointFromStartingPosition() {
        final Point2d start = findStart().orElseThrow();

        final Set<Point2d> previousPoints = new HashSet<>();
        previousPoints.add(start);

        int steps = 0;

        final Set<Point2d> newPoints = new HashSet<>(destinationsFor(start, previousPoints));

        while (!newPoints.isEmpty()) {
            previousPoints.addAll(newPoints);

            final Set<Point2d> destinations = newPoints.stream()
                    .map(point -> destinationsFor(point, previousPoints))
                    .flatMap(Set::stream)
                    .collect(toUnmodifiableSet());
            newPoints.clear();
            newPoints.addAll(destinations);
            steps++;
        }

        return steps;
    }

    TileType tileTypeAt(final Point2d point) {
        return TileType.parse(tiles[point.y()][point.x()]);
    }

    Set<Point2d> destinationsFor(final Point2d point, final Set<Point2d> previousPoints) {
        final Set<Direction> directions = tileTypeAt(point).directions();

        return directions.stream()
                .map(direction -> Pair.of(direction, destinationPoint(point, direction)))
                .filter(pair -> isValid(pair.getValue()))
                .filter(pair -> tileTypeAt(pair.getValue()).directions().contains(pair.getKey().sourceDirection()))
                .map(Pair::getValue)
                .filter(not(previousPoints::contains))
                .collect(toUnmodifiableSet());
    }

    private boolean isValid(final Point2d point) {
        return point.x() >= 0
                && point.y() >= 0
                && point.y() < tiles.length
                && point.x() < tiles[point.y()].length;
    }

    Point2d destinationPoint(
            final Point2d point,
            final Direction direction
    ) {
        return switch (direction) {
            case NORTH -> Point2d.of(point.x(), point.y() - 1);
            case EAST -> Point2d.of(point.x() + 1, point.y());
            case SOUTH -> Point2d.of(point.x(), point.y() + 1);
            case WEST -> Point2d.of(point.x() - 1, point.y());
        };
    }
}
