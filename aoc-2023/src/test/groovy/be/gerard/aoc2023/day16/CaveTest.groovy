package be.gerard.aoc2023.day16

import be.gerard.aoc.util.Point
import be.gerard.aoc.util.Ray
import be.gerard.aoc.util.Ray2d
import be.gerard.aoc.util.Vector
import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

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
        lines                   | expectedResult | comment
        fromFile("day16/a.txt") | 46             | ""
        fromFile("day16/b.txt") | 7870           | ""

        fromFile("day16/c.txt") | 7              | ""
        fromFile("day16/d.txt") | 7              | ""
        fromFile("day16/e.txt") | 9              | ""
        fromFile("day16/f.txt") | 8              | ""
    }

    def "find max possible energized tiles"() {
        when:
        final Cave cave = Cave.parse(lines)
        final long numberOfEnergizedTiles = cave.maxPossibleEnergizedTiles()

        then:
        numberOfEnergizedTiles == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day16/a.txt") | 51             | ""
        fromFile("day16/b.txt") | 8143           | ""
    }
}
