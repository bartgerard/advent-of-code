package be.gerard.aoc2023.day21;

import java.util.List;

public record Pattern(
        List<Long> start,
        List<Long> cycle,
        java.util.Map<be.gerard.aoc.util.spatial.Direction, Integer> numberOfStepsToReachBorder) {
}
