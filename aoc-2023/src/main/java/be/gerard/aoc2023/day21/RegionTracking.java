package be.gerard.aoc2023.day21;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;

public record RegionTracking(
        List<PatternForRegion> regions
) {
    public static RegionTracking of(final OddSizedSquaredRegionMatrix grid) {
        return IntStream.range(0, grid.regions().size())
                .boxed()
                .flatMap(y -> IntStream.range(0, grid.regions().getFirst().size())
                        .mapToObj(x -> {
                            final int regionId = grid.regionIdFor(x, y);
                            //final int regionId = y * grid.regions().getFirst().size() + x;

                            return new PatternForRegion(
                                    regionId,
                                    grid.regions().get(y).get(x),
                                    grid.center()
                            );
                        })
                )
                .collect(collectingAndThen(
                        toUnmodifiableList(),
                        RegionTracking::new
                ));
    }

    public boolean isFinished(final int regionId) {
        return regions.get(regionId).isCycleFound();
    }


    public void add(
            final long[] reachablePlotsByRegion
    ) {
        IntStream.range(0, regions.size())
                .forEach(i -> regions.get(i).add(reachablePlotsByRegion[i]));
    }

    public boolean finished() {
        return regions.stream().allMatch(PatternForRegion::isCycleFound);
    }

    public long extrapolate(final int steps) {
        // {1 .. 1 2 3 .. 3}
        // {1 .. .. .. .. 3}
        // {1 .. 1 2 3 .. 3}
        // {4 .. 4 5 6 .. 6}
        // {7 .. 7 8 9 .. 9}
        // {7 .. .. .. .. 9}
        // {7 .. 7 8 9 .. 9}


        final int horizontalCycle = regions.get(4).minSteps() + regions.get(6).minSteps();
        final int verticalCycle = regions.get(2).minSteps() + regions.get(8).minSteps();

        return regions.get(5).extrapolate(steps);
        //return regions.stream()
        //        .mapToLong(region -> region.extrapolate(steps))
        //        .sum();
    }
}
