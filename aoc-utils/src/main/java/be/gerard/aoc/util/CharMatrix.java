package be.gerard.aoc.util;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public record CharMatrix(
        char[][] values
) implements Matrix {
    public IntMatrix mapToInt(final IntUnaryOperator mapper) {
        return new IntMatrix(Arrays.stream(values)
                .map(row -> IntStream.range(0, row.length)
                        .map(i -> mapper.applyAsInt(row[i]))
                        .toArray()
                )
                .toArray(int[][]::new)
        );
    }

    public char at(final Point2d point) {
        return values[point.y()][point.x()];
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }
}
