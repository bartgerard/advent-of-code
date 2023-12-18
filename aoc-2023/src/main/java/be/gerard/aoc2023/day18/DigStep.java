package be.gerard.aoc2023.day18;

import be.gerard.aoc.util.input.Tokens;
import be.gerard.aoc.util.spatial.Direction;

import java.util.List;

import static be.gerard.aoc.util.spatial.Direction.DOWN;
import static be.gerard.aoc.util.spatial.Direction.LEFT;
import static be.gerard.aoc.util.spatial.Direction.RIGHT;
import static be.gerard.aoc.util.spatial.Direction.UP;

public record DigStep(
        Direction direction,
        int distance
) {
    public static DigStep parse(final String value) {
        final List<String> tokens = Tokens.split(value, " ");

        return new DigStep(
                Direction.parse(tokens.getFirst()),
                Integer.parseInt(tokens.get(1))
        );
    }

    public static DigStep parseHexadecimal(final String value) {
        final List<String> tokens = Tokens.split(value, " ");
        final String colorCode = tokens.get(2);
        final String hexadecimalDigits = colorCode.substring(2, colorCode.length() - 1);

        final int distance = Integer.parseInt(hexadecimalDigits.substring(0, hexadecimalDigits.length() - 1), 16);
        final String hexadecimalDirection = hexadecimalDigits.substring(hexadecimalDigits.length() - 1);

        final Direction direction = switch (hexadecimalDirection) {
            case "3" -> UP;
            case "0" -> RIGHT;
            case "1" -> DOWN;
            case "2" -> LEFT;
            default-> throw new IllegalArgumentException("invalid direction [%s]".formatted(hexadecimalDirection));
        };

        return new DigStep(
                direction,
                distance
        );
    }
}