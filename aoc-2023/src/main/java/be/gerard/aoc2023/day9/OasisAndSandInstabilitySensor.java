package be.gerard.aoc2023.day9;

import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Tokens;

import java.util.List;

record OasisAndSandInstabilitySensor(
        List<History> histories
) {
    static OasisAndSandInstabilitySensor parse(final Lines<String> lines) {
        return lines
                .map(Tokens::asNumbers)
                .map(History::new)
                .as(OasisAndSandInstabilitySensor::new);
    }

    long sumOfExtrapolations() {
        return histories.stream()
                .mapToLong(History::extrapolate)
                .sum();
    }

    long sumOfBackwardsExtrapolations() {
        return histories.stream()
                .mapToLong(History::extrapolateBackwards)
                .sum();
    }
}
