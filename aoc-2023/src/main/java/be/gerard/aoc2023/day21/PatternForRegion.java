package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.matrix.IntMatrix;

import java.util.ArrayList;
import java.util.List;

class PatternForRegion {
    private final int regionId;
    private final IntMatrix grid;
    private final List<Long> start = new ArrayList<>();
    private final List<Long> cycle = new ArrayList<>();
    private long previousRemainingPlots;
    private boolean flooded;
    private boolean cycleFound;
    private int minSteps = -1;

    PatternForRegion(int regionId, IntMatrix grid) {
        this.regionId = regionId;
        this.grid = grid;
        this.previousRemainingPlots = grid.findAllPointsWithValue(-1).size();
    }

    public void add(final long reachablePlots) {
        if (cycleFound) {
            return;
        }

        if (flooded) {
            if (cycle.contains(reachablePlots)) {
                this.cycleFound = true;
            } else {
                cycle.add(reachablePlots);
            }
            return;
        }

        start.add(reachablePlots);
        final int remainingPlots = grid.findAllPointsWithValue(-1).size();

        if (minSteps >= 0 && previousRemainingPlots <= remainingPlots || remainingPlots == 0) {
            this.flooded = true;
        }

        if (minSteps < 0 && 0 < reachablePlots) {
            minSteps = start.size();
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
        return minSteps;
    }
}
