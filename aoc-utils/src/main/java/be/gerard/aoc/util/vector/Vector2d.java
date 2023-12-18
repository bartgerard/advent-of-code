package be.gerard.aoc.util.vector;

public record Vector2d(
        int x,
        int y
) implements Vector {

    public Vector2d dot(final int value) {
        return Vector.of(x * value, y * value);
    }
}
