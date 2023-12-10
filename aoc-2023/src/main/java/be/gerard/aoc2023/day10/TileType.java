package be.gerard.aoc2023.day10;

import java.util.Set;

import static be.gerard.aoc2023.day10.Direction.EAST;
import static be.gerard.aoc2023.day10.Direction.NORTH;
import static be.gerard.aoc2023.day10.Direction.SOUTH;
import static be.gerard.aoc2023.day10.Direction.WEST;
import static java.util.Collections.emptySet;

/**
 * | is a vertical pipe connecting north and south.
 * - is a horizontal pipe connecting east and west.
 * L is a 90-degree bend connecting north and east.
 * J is a 90-degree bend connecting north and west.
 * 7 is a 90-degree bend connecting south and west.
 * F is a 90-degree bend connecting south and east.
 * . is ground; there is no pipe in this tile.
 * S is the starting position of the animal; there is a pipe on this tile, but your sketch doesn't show what shape the pipe has.
 *
 * @param directions
 */
enum TileType {

    GROUND(emptySet()),

    START_TILE(Set.of(NORTH, EAST, SOUTH, WEST)),

    VERTICAL_PIPE(Set.of(NORTH, SOUTH)),

    HORIZONTAL_PIPE(Set.of(WEST, EAST)),

    NORTH_EAST_BEND(Set.of(NORTH, EAST)),

    NORTH_WEST_BEND(Set.of(NORTH, WEST)),

    SOUTH_WEST_BEND(Set.of(WEST, SOUTH)),

    SOUTH_EAST_BEND(Set.of(EAST, SOUTH));

    private final Set<Direction> directions;

    TileType(final Set<Direction> directions) {
        this.directions = directions;
    }

    static TileType parse(final char tile) {
        return switch (tile) {
            case '|' -> VERTICAL_PIPE;
            case '-' -> HORIZONTAL_PIPE;
            case 'L' -> NORTH_EAST_BEND;
            case 'J' -> NORTH_WEST_BEND;
            case '7' -> SOUTH_WEST_BEND;
            case 'F' -> SOUTH_EAST_BEND;
            case 'S' -> START_TILE;
            //case '.' -> GROUND;
            default -> GROUND;
        };
    }

    public Set<Direction> directions() {
        return directions;
    }
}
