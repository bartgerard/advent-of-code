package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point2d;

import java.util.List;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public interface Matrix {
    static CharMatrix parse(final List<String> rows) {
        notEmpty(rows, "rows is invalid");
        isTrue(rows.stream().map(String::length).allMatch(length -> rows.getFirst().length() == length));

        final char[][] values = rows.stream()
                .map(String::toCharArray)
                .toArray(char[][]::new);

        return new CharMatrix(values);
    }

    static int[][] array(
            final int height,
            final int width,
            final int initialValue
    ) {
        return IntStream.range(0, height)
                .mapToObj(row -> IntStream.range(0, width)
                        .map(column -> initialValue)
                        .toArray()
                )
                .toArray(int[][]::new);
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
