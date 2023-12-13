package be.gerard.aoc.util;

import java.util.Arrays;
import java.util.stream.IntStream;

public record IntMatrix(
        int[][] values
) implements Matrix {
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

    public int[] size() {
        return new int[]{
                values.length,
                values[0].length
        };
    }

    public int[] row(final int row) {
        return values[row];
    }

    @Override
    public String toString() {
        return Arrays.deepToString(values);
    }
}
