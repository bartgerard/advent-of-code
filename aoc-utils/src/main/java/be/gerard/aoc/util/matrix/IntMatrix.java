package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;

import java.util.Arrays;
import java.util.stream.IntStream;

public record IntMatrix(
        int[][] values
) implements Matrix {
    public IntMatrix transpose() {
        final int columns = values[0].length;

        return new IntMatrix(IntStream.range(0, columns)
                .mapToObj(i -> Arrays.stream(values)
                        .mapToInt(value -> value[i])
                        .toArray()
                )
                .toArray(int[][]::new)
        );
    }

    @Override
    public int width() {
        return values[0].length;
    }

    @Override
    public int height() {
        return values.length;
    }

    public int at(final Point2d point) {
        return at(point.y(), point.x());
    }

    public int at(
            final int row,
            final int column
    ) {
        return values[row][column];
    }

    public void set(
            final Point2d point,
            final int value
    ) {
        values[point.y()][point.x()] = value;
    }

    public Point2d start() {
        return Point.of(0, 0);
    }

    public Point2d end() {
        return Point.of(
                width() - 1,
                height() - 1
        );
    }

    public int[] row(final int row) {
        return values[row];
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }
}
