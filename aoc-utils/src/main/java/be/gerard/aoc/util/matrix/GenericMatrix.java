package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point2d;

import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public record GenericMatrix<T>(
        List<List<T>> values
) implements Matrix {
    public GenericMatrix {
        notEmpty(values);
        final int width = values.getFirst().size();
        isTrue(values.stream().allMatch(row -> row.size() == width));
    }

    @Override
    public int regionWidth() {
        return values.getFirst().size();
    }

    @Override
    public int regionHeight() {
        return values.size();
    }

    public T at(
            final Point2d point
    ) {
        return at(point.y(), point.x());
    }

    public T at(
            final int row,
            final int column
    ) {
        return values.get(row).get(column);
    }

    public GenericMatrix<T> transpose() {
        return new GenericMatrix<>(IntStream.range(0, regionWidth())
                .mapToObj(column -> IntStream.range(0, regionHeight())
                        .mapToObj(row -> at(row, column))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> flipHorizontal() {
        return new GenericMatrix<>(IntStream.range(0, regionHeight())
                .mapToObj(row -> IntStream.range(0, regionWidth())
                        .mapToObj(column -> at(row, regionWidth() - column - 1))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> flipVertical() {
        return new GenericMatrix<>(IntStream.range(0, regionHeight())
                .mapToObj(row -> IntStream.range(0, regionWidth())
                        .mapToObj(column -> at(regionHeight() - row - 1, column))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> flipDiagonal() {
        return new GenericMatrix<>(IntStream.range(0, regionHeight())
                .mapToObj(row -> IntStream.range(0, regionWidth())
                        .mapToObj(column -> at(regionHeight() - row - 1, regionWidth() - column - 1))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> rotateLeft() {
        return transpose().flipVertical();
    }

    public GenericMatrix<T> rotateRight() {
        return flipVertical().transpose();
    }

    public IntMatrix mapToInt(final ToIntFunction<T> mapper) {
        return new IntMatrix(values.stream()
                .map(row -> row.stream()
                        .mapToInt(mapper)
                        .toArray()
                )
                .toArray(int[][]::new)
        );
    }
}
