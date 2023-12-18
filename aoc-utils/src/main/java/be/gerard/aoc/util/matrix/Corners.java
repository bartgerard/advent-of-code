package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.spatial.Direction;

import java.util.Collection;
import java.util.Objects;

import static be.gerard.aoc.util.matrix.Corners.State.IN;
import static be.gerard.aoc.util.matrix.Corners.State.OUT;


// OUT     IN  | OUT     OUT | OUT     IN
//      X      |      X      |      X
// OUT     IN  | OUT     IN  | OUT     OUT
//
// IN      OUT | IN      OUT | IN      IN
//      X      |      X      |      X
// IN      OUT | IN      IN  | IN      OUT
record Corners(
        State topLeft,
        State topRight,
        State bottomLeft,
        State bottomRight
) {
    static final Corners OUTSIDE = new Corners(OUT, OUT, OUT, OUT);
    static final Corners INSIDE = new Corners(IN, IN, IN, IN);

    boolean isOutside() {
        return Objects.equals(this, OUTSIDE);
    }

    Corners whenCrossing(final Collection<Direction> lineDirections) {
        return new Corners(
                topRight,
                lineDirections.contains(Direction.UP) ? topRight.inverse() : topRight,
                bottomRight,
                lineDirections.contains(Direction.DOWN) ? bottomRight.inverse() : bottomRight
        );
    }

    Corners shiftLeft() {
        return new Corners(
                topRight,
                topRight,
                bottomRight,
                bottomRight
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
