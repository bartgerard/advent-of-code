package be.gerard.aoc2023.day23;

import be.gerard.aoc.util.collection.Maps;
import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.GenericMatrix;
import be.gerard.aoc.util.matrix.Matrix;
import be.gerard.aoc.util.geometry.Point;
import be.gerard.aoc.util.geometry.Point2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableMap;

public record HikingTrail(
        GenericMatrix<Tile> grid,
        Point2d start,
        Point2d end
) {

    static HikingTrail parse(final Lines<String> lines) {
        final GenericMatrix<Tile> grid = lines.as(Matrix::parse).map(Tile::parse);
        final int startX = grid.values().getFirst().indexOf(Tile.PATH_TILE);
        final int endX = grid.values().getLast().indexOf(Tile.PATH_TILE);
        return new HikingTrail(
                grid,
                Point.of(startX, 0),
                Point.of(endX, grid.height() - 1)
        );
    }

    private static int findLongestPath(
            final Point2d current,
            final Point2d end,
            final Map<Point2d, Map<Point2d, Integer>> graph,
            final List<Point2d> path
    ) {
        path.add(current);

        final int result;

        if (Objects.equals(current, end)) {
            result = 0;
        } else {
            final Map<Point2d, Integer> nextIntersections = graph.get(current);

            result = nextIntersections.entrySet()
                    .stream()
                    .filter(entry -> !path.contains(entry.getKey()))
                    .mapToInt(entry -> findLongestPath(
                                    entry.getKey(),
                                    end,
                                    graph,
                                    path
                            ) + entry.getValue()
                    )
                    .max()
                    .orElse(Integer.MIN_VALUE);
        }

        path.remove(current);

        return result;
    }

    long findLengthOfLongestHike() {
        final List<Point2d> allIntersections = findAllIntersections();
        final List<Point2d> pointsOfInterest = Stream.concat(
                        Stream.of(start(), end()),
                        allIntersections.stream()
                )
                .distinct()
                .toList();


        final Intersection start = new Intersection(null, start(), 0, false);

        final Map<Point2d, Map<Point2d, Integer>> graph = generateGraph(
                start,
                pointsOfInterest
        );

        final List<Point2d> path = new ArrayList<>();

        return findLongestPath(
                start(),
                end(),
                graph,
                path
        );
    }

    private Map<Point2d, Map<Point2d, Integer>> generateGraph(
            final Intersection intersection,
            final List<Point2d> pointsOfInterest
    ) {
        final Map<Point2d, Map<Point2d, Integer>> costByIntersection = new HashMap<>();

        generateGraph(
                intersection,
                pointsOfInterest,
                costByIntersection
        );

        return costByIntersection;
    }

    private void generateGraph(
            final Intersection intersection,
            final List<Point2d> pointsOfInterest,
            final Map<Point2d, Map<Point2d, Integer>> costByIntersection
    ) {
        final Point2d current = intersection.current();
        final List<Point2d> nextPoints = nextSteps(current).stream()
                .filter(not(point -> Objects.equals(point, intersection.previous())))
                .toList();

        final List<Intersection> nextIntersections = nextPoints.stream()
                .map(nextPoint -> nextIntersection(
                        List.of(current, nextPoint),
                        pointsOfInterest
                ))
                .toList();

        final Map<Point2d, Integer> paths = nextIntersections.stream()
                .collect(toUnmodifiableMap(
                        Intersection::current,
                        Intersection::length
                ));

        costByIntersection.put(current, paths);

        nextIntersections.stream()
                .filter(nextIntersection -> !costByIntersection.containsKey(nextIntersection.current()))
                .forEach(nextIntersection -> generateGraph(
                        nextIntersection,
                        pointsOfInterest,
                        costByIntersection
                ));

        final Map<Point2d, Map<Point2d, Integer>> bidirectionalIntersections = nextIntersections.stream()
                .filter(Intersection::bidirectional)
                .filter(nextIntersection -> costByIntersection.containsKey(nextIntersection.current()))
                .collect(toUnmodifiableMap(
                        Intersection::current,
                        nextIntersection -> Maps.put(costByIntersection.get(nextIntersection.current()), current, nextIntersection.length())
                ));

        costByIntersection.putAll(bidirectionalIntersections);
    }

    private Intersection nextIntersection(
            final List<Point2d> lastSteps,
            final List<Point2d> pointsOfInterest
    ) {
        final List<Point2d> path = new ArrayList<>(lastSteps);

        Point2d current = path.getLast();

        boolean bidirectional = true;

        while (!pointsOfInterest.contains(current)) {
            final List<Point2d> nextSteps = nextSteps(current);

            if (nextSteps.size() == 1) {
                bidirectional = false;
            }

            final Optional<Point2d> nextStep = nextSteps.stream()
                    .filter(not(path::contains))
                    .findFirst(); //end?

            if (nextStep.isEmpty()) {
                break;
            }

            current = nextStep.orElseThrow();
            path.add(current);
        }

        path.remove(current);

        return new Intersection(
                path.getLast(),
                current,
                path.size() - lastSteps.size() + 2, // +1 for current
                bidirectional
        );
    }

    private List<Point2d> findAllIntersections() {
        return grid.points()
                .filter(point -> grid.at(point).isAccessible() && nextSteps(point).size() > 2)
                .toList();
    }

    private List<Point2d> nextSteps(final Point2d point) {
        final Tile tile = grid.at(point);

        return tile.directions().stream()
                .map(point::add)
                .filter(grid::isValid)
                .filter(destination -> grid.at(destination).isAccessible())
                .toList();
    }

    HikingTrail makeSlopesClimbable() {
        final GenericMatrix<Tile> gridWithoutSlopes = grid().map(Tile::makeClimbable);
        return new HikingTrail(
                gridWithoutSlopes,
                start(),
                end()
        );
    }
}
