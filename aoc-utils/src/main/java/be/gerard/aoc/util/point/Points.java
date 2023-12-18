package be.gerard.aoc.util.point;

import java.util.List;
import java.util.stream.IntStream;

public final class Points {
    private Points() {
        // no-op
    }

    public static List<Point2d> between(
            final Point2d source,
            final Point2d destination
    ) {
        if (source.x() == destination.x()) {
            final IntStream verticalGenerator = source.y() < destination.y()
                    ? IntStream.range(source.y() + 1, destination.y())
                    : IntStream.range(destination.y() + 1, source.y());
            return verticalGenerator
                    .mapToObj(y -> Point.of(source.x(), y))
                    .toList();
        } else if (source.y() == destination.y()) {
            final IntStream horizontalGenerator = source.x() < destination.x()
                    ? IntStream.range(source.x() + 1, destination.x())
                    : IntStream.range(destination.x() + 1, source.x());
            return horizontalGenerator
                    .mapToObj(x -> Point.of(x, source.y()))
                    .toList();
        } else {
            throw new IllegalArgumentException("points not on same line");
        }
    }
}
