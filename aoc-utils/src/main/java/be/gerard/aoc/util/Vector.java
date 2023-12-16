package be.gerard.aoc.util;

public interface Vector {
    static Vector2d of(
            final int x,
            final int y
    ) {
        return new Vector2d(x, y);
    }
}
