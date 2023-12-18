package be.gerard.aoc.util.spatial;

public enum Orientation {
    HORIZONTAL,
    VERTICAL;

    Orientation inverse() {
        return switch (this) {
            case VERTICAL -> HORIZONTAL;
            case HORIZONTAL -> VERTICAL;
        };
    }
}
