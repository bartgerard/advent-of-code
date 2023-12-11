package be.gerard.aoc2023.day11;

import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Point2d;

import java.util.List;
import java.util.Set;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableSet;

record Cosmos(
        List<Point2d> galaxies,
        Point2d max
) {
    static Cosmos parse(final Lines<String> lines) {
        final List<String> rows = lines.as(identity());
        final List<Point2d> galaxies = IntStream.range(0, rows.size())
                .boxed()
                .flatMap(y -> {
                    final String row = rows.get(y);
                    return IntStream.range(0, row.length())
                            .filter(x -> row.charAt(x) == '#')
                            .mapToObj(x -> Point2d.of(x, y));
                })
                .toList();

        return new Cosmos(
                galaxies,
                Point2d.of(rows.get(0).length(), rows.size())
        );
    }

    Cosmos expand(final int expansionFactor) {
        // each empty row/column will be {expansionFactor} times as large

        final int[] xTransformations = toTransformations(Point2d::x, max.x(), expansionFactor);
        final int[] yTransformations = toTransformations(Point2d::y, max.y(), expansionFactor);

        final List<Point2d> expandedGalaxies = galaxies.stream()
                .map(galaxy -> expand(galaxy, xTransformations, yTransformations))
                .toList();

        return new Cosmos(
                expandedGalaxies,
                Point2d.of(
                        max().x() + xTransformations[xTransformations.length - 1],
                        max().y() + yTransformations[yTransformations.length - 1]
                )
        );
    }

    private static Point2d expand(
            final Point2d galaxy,
            final int[] xTransformations,
            final int[] yTransformations
    ) {
        return Point2d.of(
                galaxy.x() + xTransformations[galaxy.x()],
                galaxy.y() + yTransformations[galaxy.y()]
        );
    }

    private int[] toTransformations(
            final ToIntFunction<Point2d> indexMapper,
            final int size,
            final int expansionFactor
    ) {
        final Set<Integer> indicesWithGalaxies = galaxies.stream()
                .mapToInt(indexMapper)
                .distinct()
                .boxed()
                .collect(toUnmodifiableSet());

        final int[] transformations = new int[size];

        int voidSize = 0;

        for (int i = 0; i < transformations.length; i++) {
            if (!indicesWithGalaxies.contains(i)) {
                voidSize += expansionFactor - 1;
            }
            transformations[i] = voidSize;
        }

        return transformations;
    }

    long sumOfShortestPaths() {
        return IntStream.range(0, galaxies.size())
                .mapToLong(i -> IntStream.range(i + 1, galaxies.size())
                        .mapToLong(j -> distanceBetween(galaxies.get(i), galaxies.get(j)))
                        .sum()
                )
                .sum();
    }

    private static long distanceBetween(
            final Point2d p1,
            final Point2d p2
    ) {
        return Math.abs(p2.y() - p1.y()) + Math.abs(p2.x() - p1.x());
    }
}
