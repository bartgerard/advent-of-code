package be.gerard.aoc.util.geometry;

import be.gerard.aoc.util.math.LongRange;

public record BoundedBox(
        LongRange x,
        LongRange y,
        LongRange z
) {
    public boolean isAbove(final BoundedBox other) {
        return x().overlaps(other.x())
                && y().overlaps(other.y())
                && other.z().toInclusive() < z().fromInclusive();
    }

    public boolean isDirectlyAbove(final BoundedBox other) {
        return x().overlaps(other.x())
                && y().overlaps(other.y())
                && other.z().toInclusive() + 1 == z().fromInclusive();
    }

    public boolean isDirectlyBelow(final BoundedBox other) {
        return x().overlaps(other.x())
                && y().overlaps(other.y())
                && z().toInclusive() + 1 == other.z().fromInclusive();
    }

    public BoundedBox translate(final Vector3d vector) {
        return new BoundedBox(
                x().translate(vector.x()),
                y().translate(vector.y()),
                z().translate(vector.z())
        );
    }
}
