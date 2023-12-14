package be.gerard.aoc.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public record StringMatrix(
        String[] rows
) implements Matrix {
    public static StringMatrix parse(final List<String> rows) {
        notEmpty(rows, "rows is invalid");
        isTrue(rows.stream().map(String::length).allMatch(length -> rows.getFirst().length() == length));

        final String[] values = rows.toArray(String[]::new);

        return new StringMatrix(values);
    }

    public StringMatrix transpose() {
        final int columns = rows[0].length();

        return new StringMatrix(IntStream.range(0, columns)
                .mapToObj(i -> Arrays.stream(rows)
                        .map(value -> "" + value.charAt(i))
                        .collect(joining())
                )
                .toArray(String[]::new)
        );
    }

    public StringMatrix flip() {
        return new StringMatrix(Arrays.stream(rows)
                .map(row -> new StringBuilder(row).reverse().toString())
                .toArray(String[]::new)
        );
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final StringMatrix that = (StringMatrix) o;
        return Arrays.equals(rows, that.rows);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rows);
    }

    @Override
    public String toString() {
        return String.join("\\n", rows);
    }
}
