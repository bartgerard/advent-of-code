package be.gerard.aoc.util.geometry;

public record Vector2d(
        int x,
        int y
) implements Vector {

    public Vector2d dot(final int scalar) {
        return Vector.of(x * scalar, y * scalar);
    }
}
