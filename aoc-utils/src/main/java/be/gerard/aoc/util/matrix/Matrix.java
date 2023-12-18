package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point2d;

public interface Matrix {

    int width();

    int height();

    default boolean isValid(
            final Point2d point
    ) {
        return point.x() >= 0
                && point.y() >= 0
                && point.y() < height()
                && point.x() < width();
    }

}
