package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.spatial.Direction;
import be.gerard.aoc.util.vector.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableSet;

record InfiniteGarden(
        IntMatrix grid
) {

    long numberOfPlotsReachableAfter(final int steps) {
        final Pattern pattern = cycle();
        return 0;
    }

    Pattern cycle() {
        final List<Long> numberOfReachablePlotsByStep = new ArrayList<>();

        int previousRemainingPlots = Integer.MAX_VALUE;
        final List<Long> pattern = new ArrayList<>();

        final Map<Direction, Integer> numberOfStepsToReachBorder = new HashMap<>();

        for (int step = 1; ; step++) {
            final int remainingPlots = grid.findAllPointsWithValue(-1).size();

            long numberOfReachablePlots = 0;

            final Set<Point2d> points = grid.findAllPointsWithValue(step - 1);

            final Set<Point2d> newPoints = points.stream()
                    .flatMap(point -> Vector.ORTHOGONAL_DIRECTIONS.stream()
                            .map(point::add)
                    )
                    .collect(collectingAndThen(
                            toUnmodifiableSet(),
                            grid::cycle
                    ));

            if (numberOfStepsToReachBorder.size() < 4) {
                if (!numberOfStepsToReachBorder.containsKey(Direction.LEFT) && newPoints.stream().anyMatch(p -> p.x() == 0)) {
                    numberOfStepsToReachBorder.put(Direction.LEFT, step);
                }
                if (!numberOfStepsToReachBorder.containsKey(Direction.RIGHT) && newPoints.stream().anyMatch(p -> p.x() == grid.width() - 1)) {
                    numberOfStepsToReachBorder.put(Direction.RIGHT, step);
                }
                if (!numberOfStepsToReachBorder.containsKey(Direction.UP) && newPoints.stream().anyMatch(p -> p.y() == 0)) {
                    numberOfStepsToReachBorder.put(Direction.UP, step);
                }
                if (!numberOfStepsToReachBorder.containsKey(Direction.DOWN) && newPoints.stream().anyMatch(p -> p.y() == grid.height() - 1)) {
                    numberOfStepsToReachBorder.put(Direction.DOWN, step);
                }
            }

            for (final Point2d newPoint : newPoints) {
                if (grid.cyclicAt(newPoint) < step) {
                    grid.set(newPoint, step);
                    numberOfReachablePlots++;
                }
            }

            numberOfReachablePlotsByStep.add(numberOfReachablePlots);

            if (remainingPlots >= previousRemainingPlots) {
                pattern.add(numberOfReachablePlots);

                if (pattern.contains(numberOfReachablePlots)) {
                    break;
                }
            }

            previousRemainingPlots = remainingPlots;
        }

        return new Pattern(
                numberOfReachablePlotsByStep,
                pattern,
                numberOfStepsToReachBorder
        );
    }

}
