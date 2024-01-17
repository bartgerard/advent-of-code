package be.gerard.aoc.util.geometry;

public record Vector3d(
        long x,
        long y,
        long z
) implements Vector {

    public Vector3d dot(final long scalar) {
        return Vector.of(x * scalar, y * scalar, z * scalar);
    }
}
