package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point2d;

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

    public void set(
            final Point2d point,
            final char value
    ) {
        values[point.y()][point.x()] = value;
    }

    @Override
    public int width() {
        return values[0].length;
    }

    @Override
    public int height() {
        return values.length;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }
}
