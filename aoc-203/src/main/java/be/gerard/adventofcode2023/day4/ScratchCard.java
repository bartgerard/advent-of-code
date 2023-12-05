package be.gerard.adventofcode2023.day4;

import be.gerard.adventofcode2023.util.Line;
import be.gerard.adventofcode2023.util.Lines;
import be.gerard.adventofcode2023.util.Numbers;
import be.gerard.adventofcode2023.util.Tokens;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toUnmodifiableSet;

public record ScratchCard(
        int id,
        Set<Integer> winningNumbers,
        Set<Integer> numbers
) {
    static final String NUMBER_REGEX = "\\d+";

    static Lines<ScratchCard> parse(final Lines<String> lines) {
        return lines.mapLine(Line::new)
                .map(ScratchCard::parse);
    }

    static ScratchCard parse(final Line line) {
        final Tokens<Line> lineTokens = line.splitLine(": ");
        final int cardId = Integer.parseInt(lineTokens.first().tokenize(NUMBER_REGEX).first());

        final Tokens<List<Integer>> scratchNumbers = lineTokens.last()
                .splitLine(" \\| ")
                .map(lineSegment -> lineSegment.tokenize(NUMBER_REGEX)
                        .as(Numbers::parseInt)
                );

        return new ScratchCard(
                cardId,
                Set.copyOf(scratchNumbers.first()),
                Set.copyOf(scratchNumbers.last())
        );
    }

    static int totalNumberOfScratchCardsWon(final Collection<ScratchCard> scratchcards) {
        final List<ScratchCard> reversedCards = scratchcards.stream()
                .sorted(comparing(ScratchCard::id, reverseOrder()))
                .toList();

        final Map<Integer, Integer> numberOfScratchcardsWonById = new HashMap<>();

        for (final ScratchCard reversedCard : reversedCards) {
            final int nbMatchingNumbers = reversedCard.matchingNumbers().size();

            final int numberOfScratchCardsWonForId = IntStream.rangeClosed(1, nbMatchingNumbers)
                    .map(i -> reversedCard.id() + i)
                    .map(id -> numberOfScratchcardsWonById.getOrDefault(id, 0))
                    .sum();

            numberOfScratchcardsWonById.put(reversedCard.id(), 1 + numberOfScratchCardsWonForId);
        }

        return numberOfScratchcardsWonById.values()
                .stream()
                .reduce(0, Integer::sum);
    }

    Set<Integer> matchingNumbers() {
        return numbers.stream()
                .filter(winningNumbers::contains)
                .collect(toUnmodifiableSet());
    }

    int points() {
        final int numberOfWinningNumbers = matchingNumbers().size();

        return numberOfWinningNumbers == 0
                ? 0
                : 1 << numberOfWinningNumbers - 1;
        //return (int) Math.pow(2, numberOfWinningNumbers - 1);
    }
}
