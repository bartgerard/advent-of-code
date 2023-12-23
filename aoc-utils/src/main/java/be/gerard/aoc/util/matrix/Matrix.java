package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point2d;

import java.util.List;

import static org.apache.commons.lang3.Validate.notEmpty;

public interface Matrix {

    static <T> GenericMatrix<T> of(final List<List<T>> values) {
        return new GenericMatrix<>(values);
    }

    static GenericMatrix<Character> parse(final List<String> rows) {
        notEmpty(rows, "rows is invalid");

        final List<List<Character>> values = rows.stream()
                .map(row -> row.chars()
                        .mapToObj(c -> (char) c)
                        .toList()
                )
                .toList();

        return new GenericMatrix<>(values);
    }

    int width();

    int height();

    default boolean isValid(
            final Point2d point
    ) {
        return point.x() >= 0
                && point.y() >= 0
                && point.y() < height()
                && point.x() < width();
    }

}
