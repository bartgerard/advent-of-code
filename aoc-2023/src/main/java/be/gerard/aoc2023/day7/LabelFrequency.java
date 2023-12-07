package be.gerard.aoc2023.day7;

import org.springframework.lang.NonNull;

import java.util.Comparator;

import static java.util.Comparator.comparingLong;

record LabelFrequency(
        long frequency,
        int strength
) implements Comparable<LabelFrequency> {
    private static final Comparator<LabelFrequency> COMPARATOR = comparingLong(LabelFrequency::frequency)
            .thenComparingInt(LabelFrequency::strength)
            .reversed();

    @Override
    public int compareTo(@NonNull final LabelFrequency other) {
        return COMPARATOR.compare(this, other);
    }
}
