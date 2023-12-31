package be.gerard.aoc2023.day10


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile
import static be.gerard.aoc2023.day10.Corners.State.IN
import static be.gerard.aoc2023.day10.Corners.State.OUT
import static org.assertj.core.api.Assertions.assertThat

class PipeMazeTest extends Specification {

    def "number of steps along the loop does it takes to get from the starting position to the point farthest from the starting position"() {
        when:
        final PipeMaze maze = PipeMaze.parse(lines)
        final int numberOfSteps = maze.numberOfStepsToFarthestPointFromStartingPosition()

        then:
        numberOfSteps == expectedNumberOfSteps

        where:
        lines                           | expectedNumberOfSteps | comment
        fromFile("day10/example_1.txt") | 4                     | ""
        fromFile("day10/example_2.txt") | 4                     | ""
        fromFile("day10/example_3.txt") | 8                     | ""
        fromFile("day10/example_4.txt") | 8                     | ""
        fromFile("day10/input.txt")     | 6875                  | ""
    }

    def "number of tiles that are enclosed by the loop"() {
        when:
        final PipeMaze maze = PipeMaze.parse(lines)
        final long numberOfEnclosedTiles = maze.numberOfEnclosedTiles()

        then:
        numberOfEnclosedTiles == expectedNumberOfEnclosedTiles

        where:
        lines                           | expectedNumberOfEnclosedTiles | comment
        fromFile("day10/example_1.txt") | 1                             | ""
        fromFile("day10/example_2.txt") | 1                             | ""
        fromFile("day10/example_3.txt") | 1                             | ""
        fromFile("day10/example_4.txt") | 1                             | ""

        fromFile("day10/example_5.txt") | 4                             | ""
        fromFile("day10/example_6.txt") | 8                             | ""
        fromFile("day10/example_7.txt") | 10                            | ""
        fromFile("day10/input.txt")     | 471                           | ""
    }

    def "change of corners when moving to the next tile"() {
        when:
        final Corners current = previous.whenCrossing(tileType.directions())

        then:
        assertThat(current).isEqualTo(expectedCorners)

        where:
        previous                     | tileType                 | expectedCorners               | comment
        Corners.OUTSIDE              | TileType.GROUND          | Corners.OUTSIDE               | ""
        Corners.INSIDE               | TileType.GROUND          | Corners.INSIDE                | ""

        Corners.OUTSIDE              | TileType.VERTICAL_PIPE   | Corners.of(OUT, IN, OUT, IN)  | ""
        Corners.INSIDE               | TileType.VERTICAL_PIPE   | Corners.of(IN, OUT, IN, OUT)  | ""

        Corners.OUTSIDE              | TileType.NORTH_EAST_BEND | Corners.of(OUT, IN, OUT, OUT) | ""
        Corners.INSIDE               | TileType.NORTH_EAST_BEND | Corners.of(IN, OUT, IN, IN)   | ""

        Corners.OUTSIDE              | TileType.SOUTH_EAST_BEND | Corners.of(OUT, OUT, OUT, IN) | ""
        Corners.INSIDE               | TileType.SOUTH_EAST_BEND | Corners.of(IN, IN, IN, OUT)   | ""

        Corners.OUTSIDE              | TileType.SOUTH_EAST_BEND | Corners.of(OUT, OUT, OUT, IN) | ""
        Corners.INSIDE               | TileType.SOUTH_EAST_BEND | Corners.of(IN, IN, IN, OUT)   | ""

        Corners.of(OUT, OUT, IN, IN) | TileType.HORIZONTAL_PIPE | Corners.of(OUT, OUT, IN, IN)  | ""
        Corners.of(IN, IN, OUT, OUT) | TileType.HORIZONTAL_PIPE | Corners.of(IN, IN, OUT, OUT)  | ""

        Corners.of(OUT, OUT, IN, IN) | TileType.NORTH_WEST_BEND | Corners.of(OUT, IN, IN, IN)   | ""
        Corners.of(IN, IN, OUT, OUT) | TileType.NORTH_WEST_BEND | Corners.of(IN, OUT, OUT, OUT) | ""

        Corners.of(OUT, OUT, IN, IN) | TileType.SOUTH_WEST_BEND | Corners.of(OUT, OUT, IN, OUT) | ""
        Corners.of(IN, IN, OUT, OUT) | TileType.SOUTH_WEST_BEND | Corners.of(IN, IN, OUT, IN)   | ""
    }
}
