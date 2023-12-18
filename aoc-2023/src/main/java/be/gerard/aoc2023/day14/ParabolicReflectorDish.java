package be.gerard.aoc2023.day14;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.StringMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public record ParabolicReflectorDish(
        StringMatrix platform
) {
    public static final char ROUND_ROCKS_CHAR = 'O';
    public static final char SQUARE_ROCKS_CHAR = '#';
    private static final String EMPTY = ".";
    private static final String ROUND_ROCKS = "O";
    private static final String SQUARE_ROCKS = "#";

    static ParabolicReflectorDish parse(final Lines<String> lines) {
        return new ParabolicReflectorDish(lines.as(StringMatrix::parse));
    }

    static StringMatrix cycle(final StringMatrix platform) {
        final StringMatrix north = rollLeft(platform.transpose()); // north.transpose()
        final StringMatrix west = rollLeft(north.transpose()); // west
        final StringMatrix south = rollLeft(west.transpose().flip()); // south.flip().transpose()
        final StringMatrix east = rollLeft(south.flip().transpose().flip()); // east.flip()
        return east.flip();
    }

    private static StringMatrix rollLeft(final StringMatrix transpose) {
        final String[] rows = Arrays.stream(transpose.rows())
                .map(ParabolicReflectorDish::rollLeft)
                .toArray(String[]::new);

        return new StringMatrix(rows);
    }

    static String rollLeft(final String row) {
        if (row.isEmpty()) {
            return "";
        }

        final int rock = row.indexOf(SQUARE_ROCKS_CHAR);

        if (rock >= 0) {
            return rollLeft(row.substring(0, rock)) + SQUARE_ROCKS + rollLeft(row.substring(rock + 1));
        } else if (!row.contains(EMPTY) || !row.contains(ROUND_ROCKS)) {
            return row;
        }

        final int numberOfRoundRocks = (int) IntStream.range(0, row.length())
                .filter(i -> row.charAt(i) == ROUND_ROCKS_CHAR)
                .count();

        return ROUND_ROCKS.repeat(numberOfRoundRocks) + EMPTY.repeat(row.length() - numberOfRoundRocks);
    }

    static long totalLoadFor(final StringMatrix platform) {
        return IntStream.range(0, platform.rows().length)
                .mapToLong(i -> {
                    final String row = platform.rows()[i];
                    final int rowLoad = platform.rows().length - i;

                    return IntStream.range(0, row.length())
                            .filter(j -> row.charAt(j) == ROUND_ROCKS_CHAR)
                            .count() * rowLoad;
                })
                .sum();
    }

    ParabolicReflectorDish spin(final int cycles) {
        final List<StringMatrix> previousStates = new ArrayList<>();
        StringMatrix previous = platform();

        for (int cycle = 0; cycle < cycles; cycle++) {
            final StringMatrix current = cycle(previous);

            if (previousStates.contains(current)) {
                final int indexOfPreviousOccurrence = previousStates.indexOf(current);
                final int cycleLength = cycle - indexOfPreviousOccurrence;

                final List<StringMatrix> repeatingCycles = previousStates.subList(indexOfPreviousOccurrence, previousStates.size());

                final int remainingCycles = cycles - cycle - 1;

                return new ParabolicReflectorDish(repeatingCycles.get(remainingCycles % cycleLength));
            }

            previous = current;
            previousStates.add(previous);
        }

        return new ParabolicReflectorDish(previous);
    }

    ParabolicReflectorDish tiltNorth() {
        final StringMatrix transpose = platform.transpose();

        final StringMatrix tilted = rollLeft(transpose);

        final StringMatrix toInitialOrientation = tilted.transpose();

        return new ParabolicReflectorDish(toInitialOrientation);
    }

    long totalLoad() {
        return totalLoadFor(platform);
    }
}
