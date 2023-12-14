package be.gerard.aoc.util;

public enum CardinalDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public CardinalDirection inverse() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }
}
