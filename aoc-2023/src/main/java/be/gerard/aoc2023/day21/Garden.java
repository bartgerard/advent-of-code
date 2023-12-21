package be.gerard.aoc2023.day21;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.matrix.Matrix;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.vector.Vector;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

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
                {other, other, other},
                {other, center, other},
                {other, other, other}
        };
    }


    long numberOfPlotsReachableAfter(final int steps) {
        return move(steps).get(steps - 1);
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
        final List<Long> reachableSpotsByStep = move(2 * 131);
        final long b0 = reachableSpotsByStep.get(65);
        final long b1 = reachableSpotsByStep.get(65 + 131);
        final long b2 = reachableSpotsByStep.get(65 + 131 * 2);

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
        final RealVector vector = MatrixUtils.createRealVector(new double[]{1, 2, 3});
        final RealVector operate = matrix.operate(vector);
        System.out.println(operate);

        //SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
        //DecompositionSolver ds=svd.getSolver();
        //double[] b = {0.0, 0.20273255, 0.5815754, 0.7520387, 0.96885669, 1.09861229};
        //ds.solve(b)[0];

        return 0;
    }
}
