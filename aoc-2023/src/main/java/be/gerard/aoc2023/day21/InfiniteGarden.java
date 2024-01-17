package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.geometry.Point2d;
import be.gerard.aoc.util.geometry.Vector;

import java.util.Set;

import static java.util.stream.Collectors.toUnmodifiableSet;

record InfiniteGarden(
        IntMatrix grid,
        OddSizedSquaredRegionMatrix infiniteGrid
) {
    InfiniteGarden(final IntMatrix grid) {
        this(grid, region(grid));
    }

    private static OddSizedSquaredRegionMatrix region(final IntMatrix grid) {
        final IntMatrix[][] expanded = expand(grid);

        return OddSizedSquaredRegionMatrix.of(expanded);
    }

    static IntMatrix[][] expand(final IntMatrix grid) {
        final IntMatrix center = grid.copy();

        final IntMatrix other = grid.copy();
        final Set<Point2d> startPoints = other.findAllPointsWithValue(0);
        startPoints.forEach(startPoint -> other.set(startPoint, -1));

        return new IntMatrix[][]{
                {other, other, other},
                {other, center, other},
                {other, other, other}
        };
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
                    .filter(infiniteGrid::isValid)
                    //.filter(point -> tracking.isFinished(infiniteGrid.regionIdFor(point)))
                    .collect(toUnmodifiableSet());

            if (newPoints.isEmpty()) {
                break;
            }

            final long[] reachablePlotsByRegion = new long[9];

            for (final Point2d newPoint : newPoints) {
                if (infiniteGrid.at(newPoint) < step) {
                    infiniteGrid.set(newPoint, step);
                    final int regionId = infiniteGrid.regionIdFor(newPoint);
                    reachablePlotsByRegion[regionId]++;
                }
            }

            tracking.add(reachablePlotsByRegion);

            if (tracking.finished()) {
                break;
            }
        }

        return tracking;
    }

}
