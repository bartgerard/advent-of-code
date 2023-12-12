package be.gerard.aoc2023.day12;

import be.gerard.aoc.util.Tokens;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

record ConditionRecord(
        String conditions,
        List<Integer> groups // contiguous groups of damage springs
) {
    private static final Map<ConditionRecord, Long> CACHE = new HashMap<>();

    public static final String UNKNOWN = "?";
    public static final String OPERATIONAL_SPRING = ".";
    public static final String DAMAGED_SPRING = "#";

    static ConditionRecord parse(final String value) {
        final List<String> values = Tokens.split(value, " ");
        return new ConditionRecord(
                values.get(0),
                Tokens.asIntegers(values.get(1))
        );
    }

    static int minRequiredLength(final List<Integer> groups) {
        return groups.size() - 1 + sum(groups);
    }

    private static Integer sum(final List<Integer> groups) {
        return groups.stream().reduce(0, Integer::sum);
    }

    long countPossibleArrangements() {
        return countPossibleArrangementsAndCacheCount(this);
    }


    static long countPossibleArrangementsAndCacheCount(final ConditionRecord record) {
        if (CACHE.containsKey(record)) {
            return CACHE.get(record);
        }

        final long count = countPossibleArrangementsCached(record);

        CACHE.put(record, count);

        return count;
    }

    static long countPossibleArrangementsCached(final ConditionRecord record) {
        final String conditions = removeOuterOperationalSprings(record.conditions());

        if (record.groups().isEmpty()) {
            return conditions.contains(DAMAGED_SPRING) ? 0 : 1;
        } else if (!conditions.contains(UNKNOWN)) {
            final String[] damagedSpringGroups = conditions.split("\\.+");
            final List<Integer> groups = Arrays.stream(damagedSpringGroups)
                    .map(String::length)
                    .toList();

            return Objects.equals(groups, record.groups()) ? 1 : 0;
        } else if (conditions.length() < minRequiredLength(record.groups())) {
            return 0;
        } else if (conditions.startsWith(DAMAGED_SPRING)) {
            final int beginIndex = record.groups().getFirst();

            if (conditions.length() < beginIndex) {
                return 0;
            }

            final String firstGroup = conditions.substring(0, beginIndex);

            if (firstGroup.contains(OPERATIONAL_SPRING)) {
                return 0;
            } else if (beginIndex == conditions.length()) {
                return 1;
            } else if (conditions.charAt(beginIndex) == '#') {
                return 0;
            }

            final ConditionRecord subRecord = new ConditionRecord(
                    conditions.substring(beginIndex + 1),
                    record.groups().subList(1, record.groups().size())
            );

            return countPossibleArrangementsAndCacheCount(subRecord);
        } else if (conditions.endsWith(DAMAGED_SPRING)) {
            return countPossibleArrangementsAndCacheCount(record.reverse());
        } else if (record.groups().size() == 1 && conditions.length() < record.groups().getFirst()) {
            return 0;
        } else if (record.groups().getLast() > record.groups().getFirst()) {
            return countPossibleArrangementsAndCacheCount(record.reverse());
        } /*else if (!conditions.contains(OPERATIONAL_SPRING) && !conditions.contains(DAMAGED_SPRING)) {
            final int numberOfOperationalSprings = conditions.length() - sum(record.groups());

            return CombinatoricsUtils.binomialCoefficient(conditions.length() - 2, numberOfOperationalSprings);
        }*/

        return Stream.of(
                        new ConditionRecord(
                                OPERATIONAL_SPRING + conditions.substring(1),
                                record.groups()
                        ),
                        new ConditionRecord(
                                DAMAGED_SPRING + conditions.substring(1),
                                record.groups()
                        )
                )
                .mapToLong(ConditionRecord::countPossibleArrangements)
                .sum();
    }

    ConditionRecord reverse() {
        return new ConditionRecord(
                new StringBuilder(conditions()).reverse().toString(),
                groups().reversed()
        );
    }

    private static String removeOuterOperationalSprings(final String conditions) {
        return conditions
                .replaceAll("\\.+$", "")
                .replaceAll("^\\.+", "");
    }

    ConditionRecord unfold(final int factor) {
        return new ConditionRecord(
                IntStream.range(0, factor)
                        .mapToObj(i -> conditions())
                        .collect(joining("?")),
                IntStream.range(0, factor)
                        .mapToObj(i -> groups())
                        .flatMap(List::stream)
                        .toList()
        );
    }
}
