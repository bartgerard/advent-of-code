package be.gerard.aoc2023.day18;

import be.gerard.aoc.util.input.Lines;

import java.util.List;

record Lagoon(
        List<DigStep> digPlan
) {
    static Lagoon parse(final Lines<String> lines) {
        return lines.map(DigStep::parse).as(Lagoon::new);
    }

    long volume() {
        return 0;
    }
}
