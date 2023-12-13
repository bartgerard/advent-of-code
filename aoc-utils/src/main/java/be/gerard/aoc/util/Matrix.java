package be.gerard.aoc.util;

import java.util.List;

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
}
