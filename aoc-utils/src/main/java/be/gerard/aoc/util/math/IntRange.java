package be.gerard.aoc.util.math;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public record IntRange(
        int fromInclusive,
        int toInclusive
) {
    public IntRange {
        isTrue(fromInclusive <= toInclusive);
    }

    public static List<IntRange> parse(final Collection<String> values) {
        return values.stream()
                .map(IntRange::parse)
                .toList();
    }

    public static IntRange parse(final String value) {
        notEmpty(value);

        final String[] ints = value.split("\\.\\.");

        if (ints.length == 1) {
            final int from = Integer.parseInt(ints[0]);
            return value.contains("..")
                    ? IntRange.of(from, Integer.MAX_VALUE)
                    : IntRange.of(from, from);
        } else if (ints.length == 2) {
            return IntRange.of(
                    Integer.parseInt(ints[0]),
                    Integer.parseInt(ints[1])
            );
        } else {
            throw new IllegalArgumentException("The value is not a valid range [value=%s]".formatted(value));
        }
    }

    public static IntRange of(
            final int fromInclusive
    ) {
        return new IntRange(fromInclusive, fromInclusive);
    }

    public static IntRange of(
            final int fromInclusive,
            final int toInclusive
    ) {
        return new IntRange(fromInclusive, toInclusive);
    }

    public static IntRange between(
            final int i,
            final int j
    ) {
        return IntRange.of(
                Integer.min(i, j),
                Integer.max(i, j)
        );
    }

    public static IntRange withLength(
            int fromInclusive,
            int length
    ) {
        return new IntRange(fromInclusive, fromInclusive + length - 1);
    }

    public static List<IntRange> intervals(
            final Collection<IntRange> ranges
    ) {
        if (ranges.isEmpty()) {
            return emptyList();
        }

        final List<Integer> borders = Stream.concat(
                        ranges.stream()
                                .map(IntRange::fromInclusive),
                        ranges.stream()
                                .map(IntRange::toExclusive)
                )
                .distinct()
                .sorted()
                .toList();

        if (borders.size() == 1) {
            return List.of(IntRange.of(borders.getFirst()));
        }

        return IntStream.range(1, borders.size())
                .mapToObj(i -> IntRange.of(
                        borders.get(i - 1),
                        borders.get(i) - 1
                ))
                .toList();
    }

    public static List<IntRange> usedIntervals(
            final Collection<IntRange> ranges
    ) {
        return usedIntervals(ranges, ranges);
    }

    public static List<IntRange> usedIntervals(
            final Collection<IntRange> allRanges,
            final Collection<IntRange> usedByRanges
    ) {
        final List<IntRange> intervals = intervals(allRanges);

        return intervals.stream()
                .filter(intersection -> usedByRanges.stream()
                        .anyMatch(range -> range.contains(intersection.fromInclusive()))
                )
                .toList();
    }

    public static List<IntRange> allGaps(
            final Collection<IntRange> ranges
    ) {
        final List<IntRange> sortedIntervals = usedIntervals(ranges);

        if (sortedIntervals.size() <= 1) {
            return emptyList();
        }

        return IntStream.range(1, sortedIntervals.size())
                .filter(i -> sortedIntervals.get(i - 1).toExclusive() != sortedIntervals.get(i).fromInclusive())
                .mapToObj(i -> IntRange.of(
                        sortedIntervals.get(i - 1).toExclusive(),
                        sortedIntervals.get(i).fromExclusive()
                ))
                .toList();
    }

    public static List<IntRange> merge(
            final Collection<IntRange> ranges
    ) {
        final List<IntRange> sortedIntervals = usedIntervals(ranges);

        if (sortedIntervals.isEmpty()) {
            return emptyList();
        } else if (sortedIntervals.size() == 1) {
            return List.of(sortedIntervals.getFirst());
        }

        final int[] nonConsecutiveIndices = IntStream.range(1, sortedIntervals.size())
                .filter(i -> sortedIntervals.get(i - 1).toExclusive() != sortedIntervals.get(i).fromInclusive())
                .toArray();

        final int[] borders = IntStream.concat(
                        IntStream.of(0, sortedIntervals.size()),
                        Arrays.stream(nonConsecutiveIndices)
                )
                .sorted()
                .toArray();

        return IntStream.range(1, borders.length)
                .mapToObj(i -> IntRange.of(
                        sortedIntervals.get(borders[i - 1]).fromInclusive(),
                        sortedIntervals.get(borders[i] - 1).toInclusive()
                ))
                .toList();
    }

    public int fromExclusive() {
        return fromInclusive - 1;
    }

    public int toExclusive() {
        return toInclusive + 1;
    }

    public IntStream unroll() {
        return IntStream.range(fromInclusive, toInclusive + 1);
    }

    public List<Integer> toList() {
        return unroll().boxed().toList();
    }

    public boolean contains(final int value) {
        return fromInclusive <= value && value <= toInclusive;
    }

    public boolean contains(final IntRange range) {
        return fromInclusive <= range.fromInclusive() && range.toInclusive() <= toInclusive;
    }

    public boolean overlaps(final IntRange range) {
        return fromInclusive <= range.toInclusive() && range.fromInclusive() <= toInclusive;
    }

    public IntRange intersect(final IntRange range) {
        isTrue(overlaps(range));

        return IntRange.of(
                Integer.max(fromInclusive, range.fromInclusive()),
                Integer.min(toInclusive, range.toInclusive())
        );
    }

    public int length() {
        return toInclusive - fromInclusive + 1;
    }

    @Override
    public String toString() {
        if (fromInclusive == toInclusive) {
            return Integer.toString(fromInclusive);
        } else if (toInclusive < Integer.MAX_VALUE) {
            return "%s..%s".formatted(fromInclusive, toInclusive);
        } else {
            return "%s..".formatted(fromInclusive);
        }
    }
}
