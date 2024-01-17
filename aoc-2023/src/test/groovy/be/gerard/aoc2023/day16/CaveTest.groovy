package be.gerard.aoc2023.day16

import be.gerard.aoc.util.geometry.Point
import be.gerard.aoc.util.vector.Ray
import be.gerard.aoc.util.vector.Ray2d
import be.gerard.aoc.util.geometry.Vector
import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class CaveTest extends Specification {

    def "count energized tiles in cave"() {
        given:
        final Ray2d startRay = Ray.of(Point.of(-1, 0), Vector.of(1, 0))

        when:
        final Cave cave = Cave.parse(lines)
        final long numberOfEnergizedTiles = cave.countEnergizedTiles(startRay)

        then:
        numberOfEnergizedTiles == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day16/example_1.txt") | 46             | ""
        fromFile("day16/input.txt")     | 7870           | ""

        fromFile("day16/extra_1.txt")   | 7              | ""
        fromFile("day16/extra_2.txt")   | 7              | ""
        fromFile("day16/extra_3.txt")   | 9              | ""
        fromFile("day16/extra_4.txt")   | 8              | ""
    }

    def "find max possible energized tiles"() {
        when:
        final Cave cave = Cave.parse(lines)
        final long numberOfEnergizedTiles = cave.maxPossibleEnergizedTiles()

        then:
        numberOfEnergizedTiles == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day16/example_1.txt") | 51             | ""
        fromFile("day16/input.txt")     | 8143           | ""
    }
}
