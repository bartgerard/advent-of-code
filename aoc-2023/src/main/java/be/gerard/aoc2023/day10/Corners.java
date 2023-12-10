package be.gerard.aoc2023.day10;


// OUT     IN  | OUT     OUT | OUT     IN
//      X      |      X      |      X
// OUT     IN  | OUT     IN  | OUT     OUT

import java.util.Objects;
import java.util.Set;

import static be.gerard.aoc2023.day10.Corners.State.IN;
import static be.gerard.aoc2023.day10.Corners.State.OUT;

// IN      OUT | IN      OUT | IN      IN
//      X      |      X      |      X
// IN      OUT | IN      IN  | IN      OUT
record Corners(
        State upperLeft,
        State upperRight,
        State lowerLeft,
        State lowerRight
) {
    static final Corners OUTSIDE = Corners.of(OUT, OUT, OUT, OUT);
    static final Corners INSIDE = Corners.of(IN, IN, IN, IN);

    enum State {
        IN,
        OUT;

        State inverse() {
            return this == IN ? OUT : IN;
        }
    }

    static Corners of(
            final State upperLeft,
            final State upperRight,
            final State lowerLeft,
            final State lowerRight
    ) {
        return new Corners(
                upperLeft,
                upperRight,
                lowerLeft,
                lowerRight
        );
    }

    TileState toTileState() {
        if (Objects.equals(this, INSIDE)) {
            return TileState.INSIDE;
        } else if (Objects.equals(this, OUTSIDE)) {
            return TileState.OUTSIDE;
        } else {
            return TileState.LOOP;
        }
    }

    Corners whenMovingTo(final TileType tileType) {
        final Set<Direction> directions = tileType.directions();

        return new Corners(
                upperRight,
                directions.contains(Direction.NORTH) ? upperRight.inverse() : upperRight,
                lowerRight,
                directions.contains(Direction.SOUTH) ? lowerRight.inverse() : lowerRight
        );
    }

    Corners inverse() {
        return new Corners(
                upperLeft.inverse(),
                upperRight.inverse(),
                lowerLeft.inverse(),
                lowerRight.inverse()
        );
    }

}
