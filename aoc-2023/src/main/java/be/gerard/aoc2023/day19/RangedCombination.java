package be.gerard.aoc2023.day19;

import be.gerard.aoc.util.math.IntRange;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableMap;

/*
Ratings
x: Extremely cool looking
m: Musical (it makes a noise when you hit it)
a: Aerodynamic
s: Shiny
 */
public record RangedCombination(
        Map<String, IntRange> ratings
) {
    static RangedCombination between(
            final int min,
            final int max
    ) {
        final IntRange range = IntRange.of(min, max);
        return new RangedCombination(Stream.of("x", "m", "a", "s")
                .collect(toUnmodifiableMap(
                        Function.identity(),
                        category -> range
                ))
        );
    }

    static List<RangedCombination> findOverlappingCombinations(final Collection<RangedCombination> combinations) {
        return combinations.stream()
                .filter(combination -> combinations.stream()
                        .filter(not(combination::equals))
                        .anyMatch(combination::overlaps)
                )
                .toList();
    }

    RangedCombination with(
            final String category,
            final IntRange newRange
    ) {
        final Map<String, IntRange> newRatings = new HashMap<>(ratings);
        newRatings.put(category, newRange);
        return new RangedCombination(Collections.unmodifiableMap(newRatings));
    }

    long countCombinations() {
        return ratings.values()
                .stream()
                .mapToLong(IntRange::length)
                .reduce(1, (x, y) -> x * y);
    }

    boolean overlaps(final RangedCombination other) {
        return ratings.entrySet()
                .stream()
                .allMatch(entry -> other.ratings().get(entry.getKey()).overlaps(entry.getValue()));
    }

    IntRange rangeFor(final String category) {
        return ratings.get(category);
    }
}
