package be.gerard.aoc.util.spatial;

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

    public Direction asDirection() {
        return switch (this) {
            case NORTH -> Direction.UP;
            case EAST -> Direction.RIGHT;
            case SOUTH -> Direction.DOWN;
            case WEST -> Direction.LEFT;
        };
    }
}
