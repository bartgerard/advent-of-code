package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.matrix.Matrix;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.vector.Vector;
import org.apache.commons.math3.linear.DecompositionSolver;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularValueDecomposition;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

record Garden(
        IntMatrix grid
) {
    static final char START = 'S';
    static final char GARDEN_PLOT = '.';
    static final char ROCK = '#';

    static Garden parse(final Lines<String> lines) {
        return new Garden(
                lines.as(Matrix::parse)
                        .mapToInt(value -> switch (value) {
                            case START -> 0;
                            case GARDEN_PLOT -> -1;
                            case ROCK -> Integer.MAX_VALUE;
                            default -> throw new IllegalStateException("Unexpected value: " + value);
                        })
        );
    }

    static List<Long> reachablePlotsByStep(
            final int steps,
            final IntMatrix grid,
            final Predicate<Set<Point2d>> endPredicate
    ) {
        final List<Long> numberOfReachablePlotsByStep = new ArrayList<>();

        for (int step = 1; step <= steps; step++) {
            long numberOfReachablePlots = 0;

            final Set<Point2d> points = grid.findAllPointsWithValue(step - 1);

            final Set<Point2d> newPoints = points.stream()
                    .flatMap(point -> Vector.ORTHOGONAL_DIRECTIONS.stream()
                            .map(point::add)
                    )
                    .filter(grid::isValid)
                    .collect(toUnmodifiableSet());

            for (final Point2d newPoint : newPoints) {
                if (grid.at(newPoint) < step) {
                    grid.set(newPoint, step);
                    numberOfReachablePlots++;
                }
            }

            numberOfReachablePlotsByStep.add(numberOfReachablePlots);

            if (endPredicate.test(newPoints)) {
                break;
            }
        }

        return unmodifiableList(numberOfReachablePlotsByStep);
    }

    static IntMatrix[][] expand(final IntMatrix grid) {
        final IntMatrix center = grid.copy();

        final IntMatrix other = grid.copy();
        final Set<Point2d> startPoints = other.findAllPointsWithValue(0);
        startPoints.forEach(startPoint -> other.set(startPoint, -1));

        return new IntMatrix[][]{
                {other, other, other, other, other},
                {other, other, other, other, other},
                {other, other, center, other, other},
                {other, other, other, other, other},
                {other, other, other, other, other}
        };
    }

    public static long special(
            final int b0,
            final int b1,
            final int b2,
            final int n
    ) {
        // Vandermonde matrix + Cramer's rule
        // https://nl.wikipedia.org/wiki/Vandermonde-matrix
        final double[][] _vandermonde = {
                {1, 0, 0},
                {1, 1, 1},
                {1, 2, 4}
        };
        final double[][] _b = {
                {b0, b1, b2}
        };
        final Jama.Matrix vandermonde = new Jama.Matrix(_vandermonde);
        final Jama.Matrix b = new Jama.Matrix(_b);

        final Jama.Matrix matrix1 = vandermonde.solveTranspose(b);


        final RealMatrix matrix = MatrixUtils.createRealMatrix(_vandermonde);
        final RealVector vector = MatrixUtils.createRealVector(new double[]{b0, b1, b2});
        final RealVector operate = matrix.operate(vector);
        System.out.println(operate);

        final SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        final DecompositionSolver ds = svd.getSolver();
        final RealVector solve = ds.solve(vector);

        final RealMatrix U = svd.getU();
        final double[] s = svd.getSingularValues();
        final RealMatrix V = svd.getV();

        final RealMatrix inverse = ds.getInverse();
        final RealVector a = inverse.operate(vector);

        return extrapolate(a.toArray(), n);
    }

    public static long extrapolate(
            final double[] vector,
            final int n
    ) {
        // a x² + b x + c

        final long a = (long) vector[2];
        final long b = (long) vector[1];
        final long c = (long) vector[0];

        return a * n * n + b * n + c;
    }

    long numberOfPlotsReachableAfter(final int steps) {
        if (steps <= grid.width() / 2) {
            return move(steps).get(steps - 1);
        }

        return infinite().numberOfPlotsReachableAfter(steps);
    }

    List<Long> move(final int steps) {
        final IntMatrix grid = this.grid.copy();
        return reachablePlotsByStep(steps, grid, newPoints -> false);
    }

    InfiniteGarden infinite() {
        return new InfiniteGarden(grid);
    }

    Garden expand() {
        return new Garden(IntMatrix.of(expand(grid)));
    }

    long special() {
        final Garden expandedGarden = expand();
        final List<Long> reachablePlotsByStep = reachablePlotsByStep(Integer.MAX_VALUE, expandedGarden.grid(), newPoints -> newPoints.stream().anyMatch(point -> point.x() == 0));

        final int first = grid().width() / 2 + 1;
        final int second = first * 2 + 1;
        final int third = second * 2;
        final long b0 = reachablePlotsByStep.get(first);
        final long b1 = reachablePlotsByStep.get(second);
        final long b2 = reachablePlotsByStep.get(third);

        return 0;
    }
}
