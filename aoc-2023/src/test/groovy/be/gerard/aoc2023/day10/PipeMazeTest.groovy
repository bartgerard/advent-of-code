package be.gerard.aoc2023.day10


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class PipeMazeTest extends Specification {

    def "number of steps along the loop does it takes to get from the starting position to the point farthest from the starting position"() {
        when:
        final PipeMaze maze = PipeMaze.parse(lines)
        final int numberOfSteps = maze.numberOfStepsToFarthestPointFromStartingPosition()

        then:
        numberOfSteps == expectedNumberOfSteps

        where:
        lines                   | expectedNumberOfSteps | comment
        fromFile("day10/a.txt") | 4                     | ""
        fromFile("day10/b.txt") | 4                     | ""
        fromFile("day10/c.txt") | 8                     | ""
        fromFile("day10/d.txt") | 8                     | ""
        fromFile("day10/e.txt") | 6875                  | ""
    }
}
