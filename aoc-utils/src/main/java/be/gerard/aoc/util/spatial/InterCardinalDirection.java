package be.gerard.aoc.util.spatial;

public enum InterCardinalDirection {
    NORTH_EAST,
    SOUTH_EAST,
    SOUTH_WEST,
    NORTH_WEST;

    public InterCardinalDirection inverse() {
        return switch (this) {
            case NORTH_EAST -> SOUTH_WEST;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH_WEST -> NORTH_EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }
}
