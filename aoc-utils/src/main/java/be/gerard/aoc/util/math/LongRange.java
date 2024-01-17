package be.gerard.aoc.util.math;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Comparator.comparingLong;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

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
            final long fromInclusive
    ) {
        return new LongRange(fromInclusive, fromInclusive);
    }

    public static LongRange of(
            final long fromInclusive,
            final long toInclusive
    ) {
        return new LongRange(fromInclusive, toInclusive);
    }

    public static LongRange between(
            final long i,
            final long j
    ) {
        return LongRange.of(
                Long.min(i, j),
                Long.max(i, j)
        );
    }

    public static LongRange withLength(
            long fromInclusive,
            long length
    ) {
        return new LongRange(fromInclusive, fromInclusive + length - 1);
    }

    public static List<LongRange> intervals(
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
            return List.of(LongRange.of(borders.getFirst()));
        }

        return IntStream.range(1, borders.size())
                .mapToObj(i -> LongRange.of(
                        borders.get(i - 1),
                        borders.get(i) - 1
                ))
                .toList();
    }

    public static List<LongRange> usedIntervals(
            final Collection<LongRange> ranges
    ) {
        return usedIntervals(ranges, ranges);
    }

    public static List<LongRange> usedIntervals(
            final Collection<LongRange> allRanges,
            final Collection<LongRange> usedByRanges
    ) {
        final List<LongRange> intervals = intervals(allRanges);

        return intervals.stream()
                .filter(interval -> usedByRanges.stream()
                        .anyMatch(range -> range.contains(interval.fromInclusive()))
                )
                .toList();
    }

    public static List<LongRange> allGaps(
            final Collection<LongRange> ranges
    ) {
        final List<LongRange> sortedIntervals = usedIntervals(ranges);

        if (sortedIntervals.size() <= 1) {
            return emptyList();
        }

        return IntStream.range(1, sortedIntervals.size())
                .filter(i -> sortedIntervals.get(i - 1).toExclusive() != sortedIntervals.get(i).fromInclusive())
                .mapToObj(i -> LongRange.of(
                        sortedIntervals.get(i - 1).toExclusive(),
                        sortedIntervals.get(i).fromExclusive()
                ))
                .toList();
    }

    public static List<LongRange> merge(
            final Collection<LongRange> ranges
    ) {
        final List<LongRange> sortedIntervals = usedIntervals(ranges);

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
                .mapToObj(i -> LongRange.of(
                        sortedIntervals.get(borders[i - 1]).fromInclusive(),
                        sortedIntervals.get(borders[i] - 1).toInclusive()
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

    public boolean containsAny(final Set<Integer> values) {
        return values.stream().anyMatch(this::contains);
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

    public List<LongRange> subtract(final LongRange subtrahend) {
        notNull(subtrahend, "subtrahend is invalid [null]");

        if (equals(subtrahend)) {
            return emptyList();
        } else if (!overlaps(subtrahend)) {
            return singletonList(this);
        }

        return Stream.concat(
                        fromInclusive() < subtrahend.fromInclusive()
                                ? Stream.of(LongRange.of(fromInclusive(), subtrahend.fromInclusive() - 1))
                                : Stream.empty(),
                        subtrahend.toInclusive() < toInclusive()
                                ? Stream.of(LongRange.of(subtrahend.toInclusive() + 1, toInclusive()))
                                : Stream.empty()
                )
                .toList();
    }

    public List<LongRange> subtract(final Collection<LongRange> subtrahends) {
        final List<LongRange> applicableSubtrahends = subtrahends.stream()
                .filter(this::overlaps)
                .toList();

        if (applicableSubtrahends.isEmpty()) {
            return singletonList(this);
        } else if (applicableSubtrahends.size() == 1) {
            return subtract(applicableSubtrahends.getFirst());
        }

        final List<LongRange> innerGaps = allGaps(applicableSubtrahends).stream()
                .filter(this::overlaps)
                .toList();

        final List<LongRange> outerGaps = subtract(LongRange.of(
                applicableSubtrahends.stream()
                        .mapToLong(LongRange::fromInclusive)
                        .min()
                        .orElse(Long.MIN_VALUE),
                applicableSubtrahends.stream()
                        .mapToLong(LongRange::toInclusive)
                        .max()
                        .orElse(Long.MAX_VALUE)
        ));

        return Stream.concat(
                        innerGaps.stream(),
                        outerGaps.stream()
                )
                .sorted(comparingLong(LongRange::fromInclusive).thenComparingLong(LongRange::toInclusive))
                .toList();
    }

    public LongRange translate(final long offset) {
        return offset == 0
                ? this
                : LongRange.of(fromInclusive() + offset, toInclusive() + offset);
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
