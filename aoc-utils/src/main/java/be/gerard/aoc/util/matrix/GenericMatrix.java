package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.geometry.Point;
import be.gerard.aoc.util.geometry.Point2d;

import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;
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
    public int width() {
        return values.getFirst().size();
    }

    @Override
    public int height() {
        return values.size();
    }

    public <O> GenericMatrix<O> map(final Function<T, O> mapper) {
        return IntStream.range(0, width())
                .mapToObj(column -> IntStream.range(0, height())
                        .mapToObj(row -> mapper.apply(at(column, row)))
                        .toList()
                )
                .collect(collectingAndThen(
                        toUnmodifiableList(),
                        GenericMatrix::new
                ));
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
        return new GenericMatrix<>(IntStream.range(0, width())
                .mapToObj(column -> IntStream.range(0, height())
                        .mapToObj(row -> at(row, column))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> flipHorizontal() {
        return new GenericMatrix<>(IntStream.range(0, height())
                .mapToObj(row -> IntStream.range(0, width())
                        .mapToObj(column -> at(row, width() - column - 1))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> flipVertical() {
        return new GenericMatrix<>(IntStream.range(0, height())
                .mapToObj(row -> IntStream.range(0, width())
                        .mapToObj(column -> at(height() - row - 1, column))
                        .toList()
                )
                .toList()
        );
    }

    public GenericMatrix<T> flipDiagonal() {
        return new GenericMatrix<>(IntStream.range(0, height())
                .mapToObj(row -> IntStream.range(0, width())
                        .mapToObj(column -> at(height() - row - 1, width() - column - 1))
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

    public Stream<Point2d> points() {
        return IntStream.range(0, height())
                .boxed()
                .flatMap(y -> IntStream.range(0, width())
                        .mapToObj(x -> Point.of(x, y))
                );
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
