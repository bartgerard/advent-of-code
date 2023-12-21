package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.matrix.RegionIntMatrix;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.vector.Vector;

import java.util.Set;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableSet;

record InfiniteGarden(
        IntMatrix grid,
        RegionIntMatrix infiniteGrid
) {
    InfiniteGarden(final IntMatrix grid) {
        this(grid, region(grid));
    }

    private static RegionIntMatrix region(final IntMatrix grid) {
        final IntMatrix[][] expanded = Garden.expand(grid);

        return RegionIntMatrix.of(expanded);
    }

    long numberOfPlotsReachableAfter(final int steps) {
        final RegionTracking regionTracking = cycle();
        return regionTracking.extrapolate(steps);
    }

    RegionTracking cycle() {
        final RegionTracking tracking = RegionTracking.of(infiniteGrid);

        for (int step = 1; ; step++) {
            final Set<Point2d> points = infiniteGrid.findAllPointsWithValue(step - 1);

            final Set<Point2d> newPoints = points.stream()
                    .flatMap(point -> Vector.ORTHOGONAL_DIRECTIONS.stream()
                            .map(point::add)
                    )
                    .collect(collectingAndThen(
                            toUnmodifiableSet(),
                            infiniteGrid::cycle
                    ));

            final long[] reachablePlotsByRegion = new long[9];

            for (final Point2d newPoint : newPoints) {
                if (infiniteGrid.cyclicAt(newPoint) < step) {
                    infiniteGrid.set(newPoint, step);
                    final int regionId = infiniteGrid.regionIdFor(newPoint);
                    reachablePlotsByRegion[regionId]++;
                }
            }

            tracking.add(reachablePlotsByRegion);

            if (tracking.finished()) {// || newPoints.stream().anyMatch(p -> p.x() == 0)
                break;
            }
        }

        return tracking;
    }

}
