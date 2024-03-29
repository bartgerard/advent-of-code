package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.geometry.Point;
import be.gerard.aoc.util.geometry.Point2d;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.IntStream;

import static java.lang.Math.floorMod;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public record IntMatrix(
        int[][] values
) implements Matrix {
    public static IntMatrix of(final IntMatrix[][] matrices) {
        notEmpty(matrices);
        final IntMatrix first = matrices[0][0];
        final int height = first.height();
        final int width = first.width();

        isTrue(IntStream.range(0, matrices.length)
                .allMatch(i -> IntStream.range(0, matrices[i].length)
                        .allMatch(j -> matrices[i][j].width() == width && matrices[i][j].height() == height)
                )
        );

        final int[][] values = new int[height * matrices.length][width * matrices[0].length];

        for (int i = 0; i < matrices.length; i++) {
            for (int j = 0; j < matrices[i].length; j++) {
                final IntMatrix matrix = matrices[i][j];

                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        values[i * height + y][j * width + x] = matrix.at(y, x);
                    }
                }
            }
        }

        return new IntMatrix(values);
    }

    public IntMatrix copy() {
        final int[][] newValues = IntStream.range(0, height())
                .mapToObj(y -> Arrays.copyOf(values[y], values[y].length))
                .toArray(int[][]::new);
        return new IntMatrix(newValues);
    }

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

    public int cyclicAt(final Point2d point) {
        return cyclicAt(point.y(), point.x());
    }

    public int at(
            final int row,
            final int column
    ) {
        return values[row][column];
    }

    public int cyclicAt(
            final int row,
            final int column
    ) {
        return at(floorMod(row, height()), floorMod(column, width()));
    }

    public void set(
            final Point2d point,
            final int value
    ) {
        values[point.y()][point.x()] = value;
    }

    public Set<Point2d> findAllPointsWithValue(final int value) {
        return IntStream.range(0, height())
                .boxed()
                .flatMap(y -> IntStream.range(0, width())
                        .filter(x -> at(y, x) == value)
                        .mapToObj(x -> Point.of(x, y))
                )
                .collect(toUnmodifiableSet());
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

    public boolean contains(final int value) {
        return IntStream.range(0, height())
                .anyMatch(y -> IntStream.range(0, width())
                        .anyMatch(x -> at(y, x) == value)
                );
    }

    public Set<Point2d> cycle(final Collection<Point2d> points) {
        return points.stream()
                .map(this::cycle)
                .collect(toUnmodifiableSet());
    }

    public Point2d cycle(final Point2d point) {
        return Point.of(
                floorMod(point.x(), width()),
                floorMod(point.y(), height())
        );
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }
}
