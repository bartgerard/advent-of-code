package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.matrix.Matrix;
import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static java.lang.Math.floorMod;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notEmpty;

public record OddSizedSquaredRegionMatrix(
        List<List<IntMatrix>> regions,
        int regionWidth,
        int regionHeight,
        int width,
        int height,
        Point2d center
) implements Matrix {
    public static OddSizedSquaredRegionMatrix of(final IntMatrix[][] matrices) {
        notEmpty(matrices);
        final IntMatrix first = matrices[0][0];

        isTrue(IntStream.range(0, matrices.length)
                .allMatch(i -> IntStream.range(0, matrices[i].length)
                        .allMatch(j -> matrices[i][j].width() == first.width() && matrices[i][j].height() == first.height())
                )
        );

        final List<List<IntMatrix>> regions = Arrays.stream(matrices)
                .map(row -> Arrays.stream(row)
                        .map(IntMatrix::copy)
                        .toList()
                )
                .toList();

        return new OddSizedSquaredRegionMatrix(
                regions,
                first.width(),
                first.height(),
                first.width() * regions.getFirst().size(),
                first.height() * regions.size(),
                Point.of(
                        first.width() / 2 + 1,
                        first.height() / 2 + 1
                )
        );
    }

    @Override
    public int width() {
        return regionWidth * regions.getFirst().size();
    }

    @Override
    public int height() {
        return regionHeight * regions.size();
    }

    public int at(final Point2d point) {
        return at(point.y(), point.x());
    }

    public int cyclicAt(final Point2d point) {
        return cyclicAt(point.y(), point.x());
    }

    public int at(
            final int row,
            final int column
    ) {
        return regions.get(row / regionHeight).get(column / regionWidth)
                .at(row % regionHeight, column % regionWidth);
    }

    public int cyclicAt(
            final int row,
            final int column
    ) {
        return at(floorMod(row, height()), floorMod(column, width()));
    }

    public IntMatrix regionFor(final int regionId) {
        final int x = regionId % regions.size();
        final int y = regionId / regions.size();

        return regions.get(y).get(x);
    }

    public int regionIdFor(final Point2d point) {
        final int x = point.x() / regionWidth;
        final int y = point.y() / regionHeight;

        return regionIdFor(x, y);
    }

    public int regionIdFor(
            final int x,
            final int y
    ) {
        return y * regions.size() + x;
    }

    public IntMatrix regionFor(final Point2d point) {
        return regions.get(point.y() / regionHeight).get(point.x() / regionWidth);
    }

    public void set(
            final Point2d point,
            final int value
    ) {
        final IntMatrix region = regionFor(point);
        region.set(region.cycle(point), value);
    }

    public Set<Point2d> findAllPointsWithValue(final int value) {
        return IntStream.range(0, height())
                .boxed()
                .flatMap(y -> IntStream.range(0, width())
                        .filter(x -> at(y, x) == value)
                        .mapToObj(x -> Point.of(x, y))
                )
                .collect(toUnmodifiableSet());
    }

    public boolean contains(final int value) {
        return IntStream.range(0, height())
                .anyMatch(y -> IntStream.range(0, width())
                        .anyMatch(x -> at(y, x) == value)
                );
    }

    public Set<Point2d> cycle(final Collection<Point2d> points) {
        return points.stream()
                .map(this::cycle)
                .collect(toUnmodifiableSet());
    }

    private Point2d cycle(final Point2d point) {
        return Point.of(
                floorMod(point.x(), width()),
                floorMod(point.y(), height())
        );
    }

}
