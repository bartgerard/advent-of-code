package be.gerard.aoc2023.day8;

public record Node(
        String nodeId,
        String leftNodeId,
        String rightNodeId
) {
    String go(final Direction direction) {
        return switch (direction) {
            case LEFT -> leftNodeId;
            case RIGHT -> rightNodeId;
        };
    }
}
