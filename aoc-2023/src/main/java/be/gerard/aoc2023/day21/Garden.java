package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.matrix.Matrix;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.vector.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

record Garden(
        IntMatrix grid
) {
    static final char START = 'S';
    static final char GARDEN_PLOT = '.';
    static final char ROCK = '#';

    static Garden parse(final Lines<String> lines) {
        return new Garden(
                lines.as(Matrix::parse)
                        .mapToInt(value -> switch (value) {
                            case START -> 0;
                            case GARDEN_PLOT -> -1;
                            case ROCK -> Integer.MAX_VALUE;
                            default -> throw new IllegalStateException("Unexpected value: " + value);
                        })
        );
    }

    long numberOfPlotsReachableAfter(final int steps) {
        return move(steps).get(steps - 1);
    }

    List<Long> move(final int steps) {
        final List<Long> numberOfReachablePlotsByStep = new ArrayList<>();

        for (int step = 1; step <= steps; step++) {
            long numberOfReachablePlots = 0;

            final Set<Point2d> points = grid.findAllPointsWithValue(step - 1);

            final Set<Point2d> newPoints = points.stream()
                    .flatMap(point -> Vector.ORTHOGONAL_DIRECTIONS.stream()
                            .map(point::add)
                    )
                    .filter(grid::isValid)
                    .collect(toUnmodifiableSet());

            for (final Point2d newPoint : newPoints) {
                if (grid.at(newPoint) < step) {
                    grid.set(newPoint, step);
                    numberOfReachablePlots++;
                }
            }

            numberOfReachablePlotsByStep.add(numberOfReachablePlots);
        }

        return unmodifiableList(numberOfReachablePlotsByStep);
    }

    InfiniteGarden infinite() {
        return new InfiniteGarden(grid);
    }
}
