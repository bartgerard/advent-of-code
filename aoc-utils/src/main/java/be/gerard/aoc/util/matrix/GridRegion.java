package be.gerard.aoc.util.matrix;

import be.gerard.aoc.util.point.Point2d;

public record GridRegion(
        int minX,
        int maxX,
        int minY,
        int maxY
) {
    public boolean contains(final Point2d point) {
        return minX <= point.x()
                && point.x() <= maxX
                && minY <= point.y()
                && point.y() <= maxY;
    }
}
