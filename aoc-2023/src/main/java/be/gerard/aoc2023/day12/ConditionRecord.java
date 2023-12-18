package be.gerard.aoc2023.day12;

import be.gerard.aoc.util.input.Tokens;
import org.apache.commons.math3.util.CombinatoricsUtils;

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
    public static final String UNKNOWN = "?";
    public static final String OPERATIONAL_SPRING = ".";
    public static final String DAMAGED_SPRING = "#";
    private static final Map<ConditionRecord, Long> CACHE = new HashMap<>();

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
        }

        final boolean containsOnlyUnknowns = !conditions.contains(OPERATIONAL_SPRING) && !conditions.contains(DAMAGED_SPRING);

        if (record.groups().size() == 1) {
            if (conditions.length() < record.groups().getFirst()) {
                return 0;
            } else if (containsOnlyUnknowns) {
                return conditions.length() - record.groups().getFirst() + 1;
            }
        }

        if (record.groups().getLast() > record.groups().getFirst()) {
            return countPossibleArrangementsAndCacheCount(record.reverse());
        }

        if (!conditions.contains(OPERATIONAL_SPRING) && !conditions.contains(DAMAGED_SPRING)) {
            final int sum = sum(record.groups());
            final int lengthIfGroupsOfOne = conditions.length() - sum + 1;
            // == length - sum '#' + # groups - # holes between groups

            return CombinatoricsUtils.binomialCoefficient(lengthIfGroupsOfOne, record.groups().size());
            //return Numbers.combinations(record.groups().size(), lengthIfGroupsOfOne);
        }

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

    private static String removeOuterOperationalSprings(final String conditions) {
        return conditions
                .replaceAll("\\.+$", "")
                .replaceAll("^\\.+", "");
    }

    long countPossibleArrangements() {
        return countPossibleArrangementsAndCacheCount(this);
    }

    ConditionRecord reverse() {
        return new ConditionRecord(
                new StringBuilder(conditions()).reverse().toString(),
                groups().reversed()
        );
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
