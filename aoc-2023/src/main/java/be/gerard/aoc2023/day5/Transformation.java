package be.gerard.aoc2023.day5;


import be.gerard.aoc.util.math.LongRange;
import be.gerard.aoc.util.input.Tokens;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;

public record Transformation(
        LongRange sourceRange,
        long difference
) {
    public static List<Transformation> parse(final Collection<String> values) {
        return values.stream()
                .map(Transformation::parse)
                .toList();
    }

    public static Transformation parse(final String value) {
        final List<Long> numbers = Tokens.asNumbers(value);

        final long destinationRangeStart = numbers.get(0);
        final long sourceRangeStart = numbers.get(1);
        final long rangeLength = numbers.get(2);

        return new Transformation(
                LongRange.withLength(sourceRangeStart, rangeLength),
                sourceRangeStart - destinationRangeStart
        );
    }

    public static List<Transformation> combine(
            final Collection<Transformation> previousTransformations,
            final Collection<Transformation> nextTransformations
    ) {
        final List<LongRange> intersections = Stream.concat(
                        previousTransformations.stream()
                                .map(Transformation::destinationRange),
                        nextTransformations.stream()
                                .map(Transformation::sourceRange)
                )
                .collect(collectingAndThen(
                        toUnmodifiableList(),
                        LongRange::usedIntersections
                ));

        return intersections.stream()
                .map(intersection -> {
                    final long previousDifference = previousTransformations.stream()
                            .filter(transformation -> intersection.overlaps(transformation.destinationRange()))
                            .map(Transformation::difference)
                            .findFirst()
                            .orElse(0L);
                    final long nextDifference = nextTransformations.stream()
                            .filter(transformation -> intersection.overlaps(transformation.sourceRange()))
                            .map(Transformation::difference)
                            .findFirst()
                            .orElse(0L);

                    return new Transformation(
                            intersection.plus(previousDifference),
                            previousDifference + nextDifference
                    );
                })
                .filter(transformation -> transformation.difference() != 0)
                .toList();
    }

    public static List<Transformation> combine(
            final List<? extends Collection<Transformation>> consecutiveTransformations
    ) {
        if (consecutiveTransformations.isEmpty()) {
            return emptyList();
        }

        List<Transformation> result = new ArrayList<>(consecutiveTransformations.get(0));

        for (int i = 1; i < consecutiveTransformations.size(); i++) {
            final Collection<Transformation> current = consecutiveTransformations.get(i);
            result = combine(result, current);
        }

        return result.stream()
                .sorted(comparing(Transformation::sourceRange, comparingLong(LongRange::fromInclusive)))
                .toList();
    }

    public static long apply(
            final List<Transformation> transformations,
            final long value
    ) {
        return transformations.stream()
                .filter(transformation -> transformation.isApplicableFor(value))
                .findFirst()
                .map(transformation -> transformation.transform(value))
                .orElse(value);
    }

    public LongRange destinationRange() {
        return sourceRange.min(difference);
    }

    public boolean isApplicableFor(final long value) {
        return sourceRange.contains(value);
    }

    public long transform(final long value) {
        return value - difference;
    }

    public String toInput() {
        return "%d %d %d".formatted(sourceRange.fromInclusive() - difference, sourceRange.fromInclusive(), sourceRange.length());
    }

    @Override
    public String toString() {
        return "%s->%s".formatted(sourceRange, sourceRange.min(difference));
    }
}
