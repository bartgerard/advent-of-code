package be.gerard.aoc2023.day10;

import be.gerard.aoc.util.spatial.CardinalDirection;

import java.util.Arrays;
import java.util.Set;

import static be.gerard.aoc.util.spatial.CardinalDirection.EAST;
import static be.gerard.aoc.util.spatial.CardinalDirection.NORTH;
import static be.gerard.aoc.util.spatial.CardinalDirection.SOUTH;
import static be.gerard.aoc.util.spatial.CardinalDirection.WEST;
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

    GROUND('.', emptySet()),

    START('S', Set.of(NORTH, EAST, SOUTH, WEST)),

    VERTICAL_PIPE('|', Set.of(NORTH, SOUTH)),

    HORIZONTAL_PIPE('-', Set.of(WEST, EAST)),

    NORTH_EAST_BEND('L', Set.of(NORTH, EAST)),

    NORTH_WEST_BEND('J', Set.of(NORTH, WEST)),

    SOUTH_WEST_BEND('7', Set.of(WEST, SOUTH)),

    SOUTH_EAST_BEND('F', Set.of(EAST, SOUTH));

    private final char label;
    private final Set<CardinalDirection> directions;

    TileType(
            final char label,
            final Set<CardinalDirection> directions
    ) {
        this.label = label;
        this.directions = directions;
    }

    static TileType parse(final int value) {
        return switch ((char) value) {
            case '|' -> VERTICAL_PIPE;
            case '-' -> HORIZONTAL_PIPE;
            case 'L' -> NORTH_EAST_BEND;
            case 'J' -> NORTH_WEST_BEND;
            case '7' -> SOUTH_WEST_BEND;
            case 'F' -> SOUTH_EAST_BEND;
            case 'S' -> START;
            //case '.' -> GROUND;
            default -> GROUND;
        };
    }

    static TileType toType(final Set<CardinalDirection> directions) {
        return Arrays.stream(values())
                .filter(type -> directions.containsAll(type.directions()) && type.directions().containsAll(directions))
                .findFirst()
                .orElseThrow();
    }

    public char label() {
        return label;
    }

    public Set<CardinalDirection> directions() {
        return directions;
    }
}
