package be.gerard.aoc.util.geometry;

import be.gerard.aoc.util.math.LongRange;

import static org.apache.commons.lang3.Validate.isTrue;

public record Rectangle(
        LongRange x,
        LongRange y
) {
    public static Rectangle parse(final String value) {
        final String[] ranges = value.split(",");

        return new Rectangle(
                LongRange.parse(ranges[0]),
                LongRange.parse(ranges[1])
        );
    }

    public boolean overlaps(final Rectangle other) {
        return x().overlaps(other.x())
                && y().overlaps(other.y());
    }

    public Rectangle intersect(final Rectangle other) {
        isTrue(overlaps(other));

        return new Rectangle(
                x().intersect(other.x()),
                y().intersect(other.y())
        );
    }

    @Override
    public String toString() {
        return "%s,%s".formatted(x, y);
    }
}
