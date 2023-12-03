package be.gerard.adventofcode2023.day3;

import java.util.Set;

record Range(
        int start,
        int end
) {
    boolean contains(final int value) {
        return start <= value && value <= end;
    }

    boolean containsAny(final Set<Integer> symbolAdjacentIndices) {
        return symbolAdjacentIndices.stream()
                .anyMatch(this::contains);
    }

    boolean adjacentTo(final int value) {
        return start - 1 <= value && value <= end + 1;
    }
}
