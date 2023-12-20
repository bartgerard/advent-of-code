package be.gerard.aoc2023.day19;

import be.gerard.aoc.util.input.Tokens;

import java.util.List;

record Rule(
        Condition condition,
        String destination
) {

    static Rule parse(final String value) {
        final List<String> conditionAndDestination = Tokens.split(value, ":");
        return new Rule(
                Condition.parse(conditionAndDestination.getFirst()),
                conditionAndDestination.getLast()
        );
    }

    public boolean isApplicable(final Part part) {
        return condition().isApplicable(part);
    }

}
