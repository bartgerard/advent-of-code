package be.gerard.aoc2023.day16;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.GenericMatrix;
import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.vector.Ray;
import be.gerard.aoc.util.vector.Ray2d;
import be.gerard.aoc.util.vector.Vector;
import be.gerard.aoc.util.vector.Vector2d;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

record Cave(
        GenericMatrix<Character> layout
) {
    private static final char EMPTY_SPACE = '.';
    private static final char MIRROR_UP = '/';
    private static final char MIRROR_DOWN = '\\';
    private static final char SPLITTER_VERTICAL = '|';
    private static final char SPLITTER_HORIZONTAL = '-';

    static Cave parse(final Lines<String> lines) {
        return new Cave(lines.as(GenericMatrix::parse));
    }


    long countEnergizedTiles(final Ray2d startRay) {
        final int[][] tiles = new int[layout.height()][layout.width()];
        final Set<Ray2d> rays = raysStartingFrom(startRay);

        for (final Ray2d ray : rays) {
            Point2d point = ray.point();
            final Point2d end = ray.end();
            final Vector2d unit = unit(ray.direction());

            for (int i = 0; ; i++) {
                final Point2d newPoint = point.add(unit);

                if (!isWithinGrid(newPoint)) {
                    break;
                }

                point = newPoint;
                tiles[point.y()][point.x()] = 1;

                if (Objects.equals(newPoint, end)) {
                    break;
                }
            }
        }

        return Arrays.stream(tiles)
                .mapToLong(row -> Arrays.stream(row)
                        .filter(column -> column == 1)
                        .count()
                )
                .sum();
    }

    private Vector2d unit(final Vector2d vector) {
        return Vector.of(
                Integer.compare(vector.x(), 0),
                Integer.compare(vector.y(), 0)
        );
    }

    private Set<Ray2d> raysStartingFrom(final Ray2d startRay) {
        final Set<Ray2d> rays = new HashSet<>();
        final Set<Ray2d> newRays = new HashSet<>();
        newRays.add(startRay);

        while (!rays.containsAll(newRays)) {
            final Set<Ray2d> currentRays = Set.copyOf(newRays);
            newRays.clear();

            for (final Ray2d ray : currentRays) {
                final Ray2d extendedRay = extend(ray);
                final Point2d end = extendedRay.end();

                if (!rays.contains(extendedRay) && isWithinGrid(end)) {
                    final char tile = layout.at(end);

                    if (tile == MIRROR_UP) {
                        newRays.add(Ray.of(end, Vector.of(-ray.direction().y(), -ray.direction().x())));
                    } else if (tile == MIRROR_DOWN) {
                        newRays.add(Ray.of(end, Vector.of(ray.direction().y(), ray.direction().x())));
                    } else if (tile == SPLITTER_HORIZONTAL && ray.direction().y() != 0) {
                        newRays.add(Ray.of(end, Vector.of(-1, 0)));
                        newRays.add(Ray.of(end, Vector.of(1, 0)));
                    } else if (tile == SPLITTER_VERTICAL && ray.direction().x() != 0) {
                        newRays.add(Ray.of(end, Vector.of(0, -1)));
                        newRays.add(Ray.of(end, Vector.of(0, 1)));
                    }

                    rays.add(extendedRay);
                }
            }
        }

        return rays;
    }

    Ray2d extend(final Ray2d ray) {
        Point2d point = ray.point();

        for (int i = 1; ; i++) {
            final Point2d newPoint = ray.point().add(ray.direction().dot(i));

            if (!isWithinGrid(newPoint)) {
                break;
            }

            point = newPoint;

            final char tile = layout.at(point);

            if (tile == MIRROR_UP || tile == MIRROR_DOWN
                    || (tile == SPLITTER_HORIZONTAL && ray.direction().y() != 0)
                    || (tile == SPLITTER_VERTICAL && ray.direction().x() != 0)
            ) {
                break;
            }
        }

        if (Objects.equals(point, ray.point())) {
            return ray;
        }

        return Ray.of(
                ray.point(),
                ray.point().towards(point)
        );
    }

    boolean isWithinGrid(final Point2d point) {
        return 0 <= point.x() && point.x() < layout.width()
                && 0 <= point.y() && point.y() < layout.height();
    }

    long maxPossibleEnergizedTiles() {
        final int rows = layout.height();
        final int columns = layout.width();

        final List<Ray2d> rays = Stream.of(
                        IntStream.range(0, rows).mapToObj(i -> Ray.of(Point.of(-1, i), Vector.RIGHT)),
                        IntStream.range(0, rows).mapToObj(i -> Ray.of(Point.of(columns, i), Vector.LEFT)),
                        IntStream.range(0, columns).mapToObj(i -> Ray.of(Point.of(i, -1), Vector.DOWN)),
                        IntStream.range(0, columns).mapToObj(i -> Ray.of(Point.of(i, columns), Vector.UP))
                )
                .flatMap(identity())
                .toList();

        return rays.stream()
                .mapToLong(this::countEnergizedTiles)
                .max()
                .orElse(0);
    }
}
