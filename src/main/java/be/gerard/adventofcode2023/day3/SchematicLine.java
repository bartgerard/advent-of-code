package be.gerard.adventofcode2023.day3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

record SchematicLine(
        int row,
        String line
) {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern SYMBOL_PATTERN = Pattern.compile("[^\\d|.]");
    private static final Pattern GEAR_PATTERN = Pattern.compile("\\*");

    List<PartNumber> candidatePartNumbers() {
        final Matcher numberMatcher = NUMBER_PATTERN.matcher(line);

        final List<PartNumber> candidatePartNumbers = new ArrayList<>();

        while (numberMatcher.find()) {
            candidatePartNumbers.add(new PartNumber(
                    row,
                    new Range(numberMatcher.start(), numberMatcher.end() - 1),
                    Integer.parseInt(numberMatcher.group())
            ));
        }

        return unmodifiableList(candidatePartNumbers);
    }

    Set<Integer> symbolIndices() {
        return indicesFor(SYMBOL_PATTERN);
    }

    List<GridPosition> candidateGearPositions() {
        return indicesFor(GEAR_PATTERN)
                .stream()
                .map(column -> new GridPosition(row, column))
                .toList();
    }

    Set<Integer> indicesFor(final Pattern pattern) {
        final Matcher symbolMatcher = pattern.matcher(line);
        final Set<Integer> result = new HashSet<>();

        while (symbolMatcher.find()) {
            result.add(symbolMatcher.start());
        }

        return unmodifiableSet(result);
    }
}
