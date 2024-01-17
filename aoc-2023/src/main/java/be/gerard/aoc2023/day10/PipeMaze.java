package be.gerard.aoc2023.day10;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.geometry.Point;
import be.gerard.aoc.util.geometry.Point2d;
import be.gerard.aoc.util.spatial.CardinalDirection;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableSet;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableSet;

record PipeMaze(
        Point2d start,
        TileType[][] tiles
) {
    static PipeMaze parse(final Lines<String> lines) {
        final TileType[][] tiles = lines.map(line -> line.chars()
                        .mapToObj(TileType::parse)
                        .toArray(TileType[]::new)
                )
                .as(row -> row.toArray(TileType[][]::new));

        final Point2d start = findStart(tiles).orElseThrow();

        return new PipeMaze(
                start,
                replaceStart(tiles, start)
        );
    }

    static TileType[][] replaceStart(
            final TileType[][] tiles,
            final Point2d start
    ) {
        final Set<CardinalDirection> directionsForStart = allMoves(tiles, start).stream()
                .map(Move::sourceDirection)
                .map(CardinalDirection::inverse)
                .collect(toUnmodifiableSet());

        final TileType startType = TileType.toType(directionsForStart);

        tiles[start.y()][start.x()] = startType;

        return tiles;
    }

    static Optional<Point2d> findStart(final TileType[][] tiles) {
        return IntStream.range(0, tiles.length)
                .boxed()
                .flatMap(i -> IntStream.range(0, tiles[i].length)
                        .filter(j -> tiles[i][j] == TileType.START)
                        .mapToObj(j -> Point.of(j, i))
                )
                .findFirst();
    }

    private static boolean isValid(
            final TileType[][] tiles,
            final Point2d point
    ) {
        return point.x() >= 0
                && point.y() >= 0
                && point.y() < tiles.length
                && point.x() < tiles[point.y()].length;
    }

    static Set<Move> allMoves(
            final TileType[][] tiles,
            final Point2d point
    ) {
        return tiles[point.y()][point.x()].directions().stream()
                .map(direction -> Move.of(move(point, direction), direction.inverse()))
                .filter(move -> isValid(tiles, move.point()))
                .filter(move -> tiles[move.point().y()][move.point().x()].directions().contains(move.sourceDirection()))
                .collect(toUnmodifiableSet());
    }

    private static Point2d move(
            final Point2d point,
            final CardinalDirection direction
    ) {
        return switch (direction) {
            case NORTH -> Point.of(point.x(), point.y() - 1);
            case EAST -> Point.of(point.x() + 1, point.y());
            case SOUTH -> Point.of(point.x(), point.y() + 1);
            case WEST -> Point.of(point.x() - 1, point.y());
        };
    }

    Set<Point2d> findLoopTiles() {
        final Set<Point2d> previousPoints = new HashSet<>();
        previousPoints.add(start);

        final Set<Point2d> newPoints = new HashSet<>(destinationsFor(start, previousPoints));

        while (!newPoints.isEmpty()) {
            previousPoints.addAll(newPoints);

            final Set<Point2d> destinations = newPoints.stream()
                    .map(point -> destinationsFor(point, previousPoints))
                    .flatMap(Set::stream)
                    .collect(toUnmodifiableSet());
            newPoints.clear();
            newPoints.addAll(destinations);
        }

        return unmodifiableSet(previousPoints);
    }

    int numberOfStepsToFarthestPointFromStartingPosition() {
        final int loopSize = findLoopTiles().size();
        return loopSize / 2 + loopSize % 2;
    }

    Set<Point2d> destinationsFor(final Point2d point, final Set<Point2d> previousPoints) {
        return allMoves(tiles, point)
                .stream()
                .map(Move::point)
                .filter(not(previousPoints::contains))
                .collect(toUnmodifiableSet());
    }

    long numberOfEnclosedTiles() {
        final Set<Point2d> loopTiles = findLoopTiles();

        final TileState[][] states = new TileState[tiles.length][tiles[0].length];

        for (int y = 0; y < tiles.length; y++) {
            Corners previous = Corners.OUTSIDE;

            for (int x = 0; x < tiles[y].length; x++) {
                final Point2d point = Point.of(x, y);
                final boolean isLoop = loopTiles.contains(point);
                final TileType tileType = isLoop ? tiles[y][x] : TileType.GROUND;

                final Corners current = previous.whenCrossing(tileType.directions());
                previous = current;

                states[y][x] = current.toTileState();
            }
        }

        return Arrays.stream(states)
                .mapToLong(state -> IntStream.range(0, state.length)
                        .filter(x -> state[x] == TileState.INSIDE)
                        .count()
                )
                .sum();
    }
}
