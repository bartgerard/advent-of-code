package be.gerard.aoc2023.day15;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.input.Tokens;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.Validate.isTrue;

// Holiday ASCII String Helper Manual Arrangement Procedure, or HASHMAP for short
record LensLibrary(
        Box[] boxes
) {
    private static final String EQUALS = "=";
    private static final String DASH = "-";
    public static final String DASH_OR_EQUALS = "[" + DASH + "|" + EQUALS + "]";

    static LensLibrary parse(final Lines<String> lines) {
        final Box[] boxes = IntStream.range(0, 256)
                .mapToObj(Box::withNumber)
                .toArray(Box[]::new);

        final List<String> values = Tokens.split(lines.getFirst(), ",");

        for (final String value : values) {
            final String[] split = value.split(DASH_OR_EQUALS);

            isTrue(split.length > 0);

            final String label = split[0];
            final int hash = Hash.hash(label);

            if (split.length == 1) {
                boxes[hash].remove(new Lens(
                        label,
                        0
                ));
            } else if (split.length == 2) {
                final Lens lens = new Lens(
                        label,
                        Integer.parseInt(split[1])
                );

                boxes[hash].add(lens);
            }
        }

        return new LensLibrary(boxes);
    }

    static int sumOfHashes(final Lines<String> lines) {
        return Tokens.split(lines.getFirst(), ",").stream()
                .mapToInt(Hash::hash)
                .sum();
    }

    int focussingPower() {
        return Arrays.stream(boxes)
                .mapToInt(Box::focussingPower)
                .sum();
    }
}
