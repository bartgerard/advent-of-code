package be.gerard.aoc.util.vector;

import be.gerard.aoc.util.spatial.Direction;

import java.util.List;

public interface Vector {
    Vector2d UP = Vector.of(0, -1);
    Vector2d DOWN = Vector.of(0, 1);
    Vector2d LEFT = Vector.of(-1, 0);
    Vector2d RIGHT = Vector.of(1, 0);
    List<Vector2d> ORTHOGONAL_DIRECTIONS = Direction.ORTHOGONAL_DIRECTIONS.stream()
            .map(Vector::in)
            .toList();

    static Vector2d of(
            final int x,
            final int y
    ) {
        return new Vector2d(x, y);
    }


    static Vector2d in(final Direction direction) {
        return switch (direction) {
            case UP -> UP;
            case DOWN -> DOWN;
            case LEFT -> LEFT;
            case RIGHT -> RIGHT;
        };
    }

}
