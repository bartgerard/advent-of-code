package be.gerard.aoc.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public record LongRange(
        long fromInclusive,
        long toInclusive
) {
    public LongRange {
        isTrue(fromInclusive <= toInclusive);
    }

    public static List<LongRange> parse(final Collection<String> values) {
        return values.stream()
                .map(LongRange::parse)
                .toList();
    }

    public static LongRange parse(final String value) {
        notEmpty(value);

        final String[] longs = value.split("\\.\\.");

        if (longs.length == 1) {
            final long from = Long.parseLong(longs[0]);
            return value.contains("..")
                    ? LongRange.of(from, Long.MAX_VALUE)
                    : LongRange.of(from, from);
        } else if (longs.length == 2) {
            return LongRange.of(
                    Long.parseLong(longs[0]),
                    Long.parseLong(longs[1])
            );
        } else {
            throw new IllegalArgumentException("The value is not a valid range [value=%s]".formatted(value));
        }
    }

    public static LongRange of(
            long fromInclusive
    ) {
        return new LongRange(fromInclusive, fromInclusive);
    }

    public static LongRange of(
            long fromInclusive,
            long toInclusive
    ) {
        return new LongRange(fromInclusive, toInclusive);
    }

    public static LongRange withLength(
            long fromInclusive,
            long length
    ) {
        return new LongRange(fromInclusive, fromInclusive + length - 1);
    }

    public static List<LongRange> allIntersections(
            final Collection<LongRange> ranges
    ) {
        if (ranges.isEmpty()) {
            return emptyList();
        }

        final List<Long> borders = Stream.concat(
                        ranges.stream()
                                .map(LongRange::fromInclusive),
                        ranges.stream()
                                .map(LongRange::toExclusive)
                )
                .distinct()
                .sorted()
                .toList();

        if (borders.size() == 1) {
            return List.of(LongRange.of(borders.get(0)));
        }

        return IntStream.range(1, borders.size())
                .mapToObj(i -> LongRange.of(
                        borders.get(i - 1),
                        borders.get(i) - 1
                ))
                .toList();
    }

    public static List<LongRange> usedIntersections(
            final Collection<LongRange> ranges
    ) {
        return usedIntersections(ranges, ranges);
    }

    public static List<LongRange> usedIntersections(
            final Collection<LongRange> allRanges,
            final Collection<LongRange> usedByRanges
    ) {
        final List<LongRange> allIntersections = allIntersections(allRanges);

        return allIntersections.stream()
                .filter(intersection -> usedByRanges.stream()
                        .anyMatch(range -> range.contains(intersection.fromInclusive()))
                )
                .toList();
    }

    public static List<LongRange> allGaps(
            final Collection<LongRange> ranges
    ) {
        final List<LongRange> sortedIntersections = usedIntersections(ranges);

        if (sortedIntersections.size() <= 1) {
            return emptyList();
        }

        return IntStream.range(1, sortedIntersections.size())
                .filter(i -> sortedIntersections.get(i - 1).toExclusive() != sortedIntersections.get(i).fromInclusive())
                .mapToObj(i -> LongRange.of(
                        sortedIntersections.get(i - 1).toExclusive(),
                        sortedIntersections.get(i).fromExclusive()
                ))
                .toList();
    }

    public static List<LongRange> merge(
            final Collection<LongRange> ranges
    ) {
        final List<LongRange> sortedIntersections = usedIntersections(ranges);

        if (sortedIntersections.isEmpty()) {
            return emptyList();
        } else if (sortedIntersections.size() == 1) {
            return List.of(sortedIntersections.get(0));
        }

        final int[] nonConsecutiveIndices = IntStream.range(1, sortedIntersections.size())
                .filter(i -> sortedIntersections.get(i - 1).toExclusive() != sortedIntersections.get(i).fromInclusive())
                .toArray();

        final int[] borders = IntStream.concat(
                        IntStream.of(0, sortedIntersections.size()),
                        Arrays.stream(nonConsecutiveIndices)
                )
                .sorted()
                .toArray();

        return IntStream.range(1, borders.length)
                .mapToObj(i -> LongRange.of(
                        sortedIntersections.get(borders[i - 1]).fromInclusive(),
                        sortedIntersections.get(borders[i] - 1).toInclusive()
                ))
                .toList();
    }

    public long fromExclusive() {
        return fromInclusive - 1;
    }

    public long toExclusive() {
        return toInclusive + 1;
    }

    public LongStream unroll() {
        return LongStream.range(fromInclusive, toInclusive + 1);
    }

    public List<Long> toList() {
        return unroll().boxed().toList();
    }

    public boolean contains(final long value) {
        return fromInclusive <= value && value <= toInclusive;
    }

    public boolean contains(final LongRange range) {
        return fromInclusive <= range.fromInclusive() && range.toInclusive() <= toInclusive;
    }

    public boolean overlaps(final LongRange range) {
        return fromInclusive <= range.toInclusive() && range.fromInclusive() <= toInclusive;
    }

    public LongRange intersect(final LongRange range) {
        isTrue(overlaps(range));

        return LongRange.of(
                Long.max(fromInclusive, range.fromInclusive()),
                Long.min(toInclusive, range.toInclusive())
        );
    }

    public LongRange plus(final long value) {
        return LongRange.of(
                fromInclusive + value,
                toInclusive + value
        );
    }

    public LongRange min(final long value) {
        return plus(-value);
    }

    public long length() {
        return toInclusive - fromInclusive + 1;
    }

    @Override
    public String toString() {
        if (fromInclusive == toInclusive) {
            return Long.toString(fromInclusive);
        } else if (toInclusive < Long.MAX_VALUE) {
            return "%s..%s".formatted(fromInclusive, toInclusive);
        } else {
            return "%s..".formatted(fromInclusive);
        }
    }
}
