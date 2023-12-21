package be.gerard.aoc2023.day5

import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class AlmanacTest extends Specification {

    def "lowest location number that corresponds to any of the initial seeds"() {
        when:
        final Almanac almanac = Almanac.parse(lines, Almanac::parseSeeds)

        then:
        final long lowestLocation = almanac.lowestLocation()
        lowestLocation == expectedResult

        where:
        lines                          | expectedResult | comment
        fromFile("day5/example_1.txt") | 35             | ""
        fromFile("day5/input.txt")     | 51752125       | ""
    }

    def "lowest location number that corresponds to any of the initial ranged seeds"() {
        when:
        final Almanac almanac = Almanac.parse(lines, Almanac::parseRangedSeeds)

        then:
        final long lowestLocation = almanac.lowestLocation()
        lowestLocation == expectedResult

        where:
        lines                          | expectedResult | comment
        fromFile("day5/example_1.txt") | 46             | ""
        fromFile("day5/input.txt")     | 12634632       | ""
    }

}
