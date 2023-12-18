package be.gerard.aoc2023.day10;

import be.gerard.aoc.util.spatial.CardinalDirection;

import java.util.Objects;
import java.util.Set;

import static be.gerard.aoc2023.day10.Corners.State.IN;
import static be.gerard.aoc2023.day10.Corners.State.OUT;

// OUT     IN  | OUT     OUT | OUT     IN
//      X      |      X      |      X
// OUT     IN  | OUT     IN  | OUT     OUT

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
            return TileState.EDGE;
        }
    }

    Corners whenCrossing(final Set<CardinalDirection> lineDirections) {
        return new Corners(
                upperRight,
                lineDirections.contains(CardinalDirection.NORTH) ? upperRight.inverse() : upperRight,
                lowerRight,
                lineDirections.contains(CardinalDirection.SOUTH) ? lowerRight.inverse() : lowerRight
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

    enum State {
        IN,
        OUT;

        State inverse() {
            return this == IN ? OUT : IN;
        }
    }

}
