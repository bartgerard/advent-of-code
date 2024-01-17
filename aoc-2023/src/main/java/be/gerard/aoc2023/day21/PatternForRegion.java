package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.geometry.Point2d;

import java.util.ArrayList;
import java.util.List;

class PatternForRegion {
    private final int regionId;
    private final IntMatrix grid;
    private final Point2d center;
    private final List<Long> start = new ArrayList<>();
    private final List<Long> cycle = new ArrayList<>();
    private long previousRemainingPlots;
    private boolean cycleFound;
    private int stepsToRegion = -1;
    private int stepsToCenter = -1;
    private int stepsToFull = -1;

    PatternForRegion(
            final int regionId,
            final IntMatrix grid,
            final Point2d center
    ) {
        this.regionId = regionId;
        this.grid = grid;
        this.previousRemainingPlots = grid.findAllPointsWithValue(-1).size();
        this.center = center;
    }

    public void add(final long reachablePlots) {
        if (this.cycleFound) {
            return;
        }

        if (this.stepsToFull >= 0) {
            if (this.cycle.contains(reachablePlots)) {
                this.cycleFound = true;
            } else {
                this.cycle.add(reachablePlots);
            }
            return;
        }

        this.start.add(reachablePlots);
        final int remainingPlots = this.grid.findAllPointsWithValue(-1).size();

        if (0 <= this.stepsToRegion && this.previousRemainingPlots <= remainingPlots || remainingPlots == 0) {
            this.stepsToFull = start.size();
        }
        if (remainingPlots < this.previousRemainingPlots) {
            this.previousRemainingPlots = remainingPlots;
        }
        if (this.stepsToCenter < 0 && grid.at(center) > -1) {
            this.stepsToCenter = start.size();
        }
        if (this.stepsToRegion < 0 && 0 < reachablePlots) {
            this.stepsToRegion = start.size();
        }
    }

    public boolean isCycleFound() {
        return cycleFound;
    }

    public long extrapolate(final int steps) {
        if (steps <= start.size()) {
            return start.get(steps - 1);
        }

        return cycle.get((steps - start.size()) % cycle.size());
    }

    public int minSteps() {
        return stepsToRegion;
    }
}
