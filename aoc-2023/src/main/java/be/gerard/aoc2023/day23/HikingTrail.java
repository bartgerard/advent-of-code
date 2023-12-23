package be.gerard.aoc2023.day23;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.GenericMatrix;
import be.gerard.aoc.util.matrix.Matrix;
import be.gerard.aoc.util.point.Point;
import be.gerard.aoc.util.point.Point2d;

public record HikingTrail(
        GenericMatrix<Tile> grid,
        Point2d start,
        Point2d end
) {
    static HikingTrail parse(final Lines<String> lines) {
        final GenericMatrix<Tile> grid = lines.as(Matrix::parse).map(Tile::parse);
        final int startX = grid.values().getFirst().indexOf(Tile.PATH_TILE);
        final int endX = grid.values().getLast().indexOf(Tile.PATH_TILE);
        return new HikingTrail(
                grid,
                Point.of(startX, 0),
                Point.of(endX, grid.height() - 1)
        );
    }

    long findLengthOfLongestHike() {
        return 0;
    }
}
