package be.gerard.aoc2023.day23;

import be.gerard.aoc.util.spatial.Direction;
import be.gerard.aoc.util.geometry.Vector;
import be.gerard.aoc.util.geometry.Vector2d;

import java.util.List;

import static java.util.Collections.emptyList;

public interface Tile {
    char PATH = '.';
    char FOREST = '#';
    char SLOPE_UP = '^';
    char SLOPE_RIGHT = '>';
    char SLOPE_DOWN = 'v';
    char SLOPE_LEFT = '<';

    Path PATH_TILE = new Path();
    Forest FOREST_TILE = new Forest();
    Slope SLOPE_UP_TILE = Slope.going(Direction.UP);
    Slope SLOPE_RIGHT_TILE = Slope.going(Direction.RIGHT);
    Slope SLOPE_DOWN_TILE = Slope.going(Direction.DOWN);
    Slope SLOPE_LEFT_TILE = Slope.going(Direction.LEFT);

    static Tile parse(final char character) {
        return switch (character) {
            case PATH -> PATH_TILE;
            case FOREST -> FOREST_TILE;
            case SLOPE_UP -> SLOPE_UP_TILE;
            case SLOPE_RIGHT -> SLOPE_RIGHT_TILE;
            case SLOPE_DOWN -> SLOPE_DOWN_TILE;
            case SLOPE_LEFT -> SLOPE_LEFT_TILE;
            default -> throw new IllegalArgumentException("unknown character");
        };
    }

    List<Vector2d> directions();

    boolean isAccessible();

    Tile makeClimbable();

    record Forest() implements Tile {
        @Override
        public List<Vector2d> directions() {
            return emptyList();
        }

        @Override
        public boolean isAccessible() {
            return false;
        }

        @Override
        public Tile makeClimbable() {
            return this;
        }
    }

    record Slope(
            List<Vector2d> directions
    ) implements Tile {
        public static Slope going(final Direction direction) {
            return new Slope(List.of(Vector.in(direction)));
        }

        @Override
        public boolean isAccessible() {
            return true;
        }

        @Override
        public Tile makeClimbable() {
            return PATH_TILE;
        }
    }

    record Path() implements Tile {
        @Override
        public List<Vector2d> directions() {
            return Vector2d.ORTHOGONAL_DIRECTIONS;
        }

        @Override
        public boolean isAccessible() {
            return true;
        }

        @Override
        public Tile makeClimbable() {
            return this;
        }
    }
}
