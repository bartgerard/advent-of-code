package be.gerard.aoc2023.day18;

import be.gerard.aoc.util.input.Tokens;
import be.gerard.aoc.util.spatial.Direction;

import java.util.List;

public record DigStep(
        Direction direction,
        int distance,
        String colorCode
) {
    public static DigStep parse(final String value) {
        final List<String> tokens = Tokens.split(value, " ");
        final String colorCode = tokens.get(2);

        return new DigStep(
                Direction.parse(tokens.getFirst()),
                Integer.parseInt(tokens.get(1)),
                colorCode.substring(1, colorCode.length() - 1)
        );
    }
}