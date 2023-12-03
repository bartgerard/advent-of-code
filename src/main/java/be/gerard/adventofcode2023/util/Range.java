package be.gerard.adventofcode2023.util;

import java.util.Set;

public record Range(
        int start,
        int end
) {
    public boolean contains(final int value) {
        return start <= value && value <= end;
    }

    public boolean containsAny(final Set<Integer> values) {
        return values.stream().anyMatch(this::contains);
    }

    public boolean adjacentTo(final int value) {
        return start - 1 <= value && value <= end + 1;
    }
}
