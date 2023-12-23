package be.gerard.aoc.util.point;

import be.gerard.aoc.util.math.IntRange;

public record BoundedBox(
        IntRange x,
        IntRange y,
        IntRange z
) {
}
