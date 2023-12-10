package be.gerard.aoc2023.day10;

enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    Direction inverse() {
        return switch (this) {
            case NORTH -> SOUTH;
            case EAST -> WEST;
            case SOUTH -> NORTH;
            case WEST -> EAST;
        };
    }
}
