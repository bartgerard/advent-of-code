package be.gerard.aoc2023.day7;

import be.gerard.aoc.util.Lines;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Comparator.comparing;

record CamelCards(
        List<Hand> hands
) {
    static CamelCards parse(final Lines<String> lines) {
        return lines.map(Hand::parse).as(CamelCards::new);
    }

    static CamelCards parseWithJokers(final Lines<String> lines) {
        return parse(lines.map(line -> line.replaceAll("J", "0")));
    }

    long totalWinnings() {
        final List<Hand> handsFromWorstToBest = hands.stream()
                .sorted(comparing(Hand::frequencies, Arrays::compare)
                        .thenComparing(Hand::strengths, Arrays::compare)
                )
                .toList();

        return IntStream.range(0, handsFromWorstToBest.size())
                .mapToLong(i -> (i + 1) * handsFromWorstToBest.get(i).bid())
                .sum();
    }
}
