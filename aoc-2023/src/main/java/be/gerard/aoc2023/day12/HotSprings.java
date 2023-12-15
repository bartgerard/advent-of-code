package be.gerard.aoc2023.day12;

import be.gerard.aoc.util.Lines;

import java.util.List;

record HotSprings(
        List<ConditionRecord> records
) {
    static HotSprings parse(final Lines<String> lines) {
        return lines.map(ConditionRecord::parse).as(HotSprings::new);
    }

    HotSprings unfold(final int factor) {
        return new HotSprings(records.stream()
                .map(record -> record.unfold(factor))
                .toList()
        );
    }

    long sumOfAllPossibleArrangements() {
        return records.stream()
                .mapToLong(ConditionRecord::countPossibleArrangements)
                //.peek(System.out::println)
                .sum();
    }
}
