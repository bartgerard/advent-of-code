package be.gerard.aoc.util;

public record Vector2d(
        int x,
        int y
) implements Point {
    public Vector2d dot(final int value) {
        return Vector.of(x * value, y * value);
    }
}
