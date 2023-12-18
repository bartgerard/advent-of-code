package be.gerard.aoc2023.day13;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.GenericMatrix;
import be.gerard.aoc.util.matrix.IntMatrix;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

record Mirrors(
        List<IntMatrix> patterns
) {
    public static final char ASH = '.';
    public static final char ROCKS = '#';

    static Mirrors parse(final Lines<String> lines) {
        final List<IntMatrix> patterns = lines.splitBy(String::isBlank)
                .map(Lines::values)
                .map(GenericMatrix::parse)
                .map(matrix -> matrix.mapToInt(c -> c == ROCKS ? 1 : 0))
                .values();

        return new Mirrors(patterns);
    }

    private static long summarize(
            final IntMatrix pattern,
            final int numberOfSmudges
    ) {
        final int numberOfHorizontalReflections = countReflections(pattern, numberOfSmudges);

        if (numberOfHorizontalReflections > 0) {
            return numberOfHorizontalReflections * 100L;
        }

        final IntMatrix transpose = pattern.transpose();
        return countReflections(transpose, numberOfSmudges); // numberOfVerticalReflections
    }

    private static int countReflections(
            final IntMatrix pattern,
            final int numberOfSmudges
    ) {
        return numberOfSmudges == 0
                ? countReflections(pattern)
                : countReflectionsWithSmudges(pattern, numberOfSmudges);
    }

    private static int countReflections(final IntMatrix pattern) {
        final int size = pattern.values().length;

        return IntStream.range(0, size - 1)
                .filter(i -> IntStream.range(0, Integer.min(size - i - 1, i + 1))
                        .allMatch(j -> Arrays.equals(pattern.row(i - j), pattern.row(i + j + 1)))
                )
                .map(i -> i + 1)
                .findFirst()
                .orElse(0);
    }

    private static int countReflectionsWithSmudges(
            final IntMatrix pattern,
            final int numberOfSmudges
    ) {
        final int size = pattern.values().length;

        return IntStream.range(0, size - 1)
                .filter(i -> IntStream.range(0, Integer.min(size - i - 1, i + 1))
                        .mapToLong(j -> {
                            final int[] row1 = pattern.row(i - j);
                            final int[] row2 = pattern.row(i + j + 1);

                            return IntStream.range(0, row1.length)
                                    .filter(k -> row1[k] != row2[k])
                                    .count();
                        })
                        .sum() == numberOfSmudges
                )
                .map(i -> i + 1)
                .findFirst()
                .orElse(0);
    }

    long summarize(final int numberOfSmudges) {
        return patterns.stream()
                .mapToLong(pattern -> summarize(pattern, numberOfSmudges))
                .sum();
    }
}
