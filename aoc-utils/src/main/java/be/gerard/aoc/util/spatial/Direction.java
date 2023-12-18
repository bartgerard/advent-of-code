package be.gerard.aoc.util.spatial;

public enum Direction {
    UP,
    RIGHT,
    DOWN,
    LEFT;

    public static Direction parse(final String value) {
        return switch (value) {
            case "U" -> UP;
            case "R" -> RIGHT;
            case "D" -> DOWN;
            case "L" -> LEFT;
            default -> throw new IllegalArgumentException("invalid direction [%s]".formatted(value));
        };
    }

    public Direction inverse() {
        return switch (this) {
            case UP -> DOWN;
            case RIGHT -> LEFT;
            case DOWN -> UP;
            case LEFT -> RIGHT;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case UP -> LEFT;
            case RIGHT -> UP;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
        };
    }

    public Direction turnRight() {
        return switch (this) {
            case UP -> RIGHT;
            case RIGHT -> DOWN;
            case DOWN -> LEFT;
            case LEFT -> UP;
        };
    }
}
