package be.gerard.aoc.util;

public record Point2d(
        int x,
        int y
) {
    public static Point2d of(
            final int x,
            final int y
    ) {
        return new Point2d(x, y);
    }
}
