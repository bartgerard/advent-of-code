package be.gerard.aoc2023.day8;

enum Direction {
    LEFT,
    RIGHT;

    static Direction parse(final int character) {
        return switch ((char) character) {
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("invalid character");
        };
    }
}
