package be.gerard.aoc2023.day9


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class OasisAndSandInstabilitySensorTest extends Specification {

    def "sum of all extrapolated values"() {
        when:
        final OasisAndSandInstabilitySensor sensor = OasisAndSandInstabilitySensor.parse(lines)
        final long sumOfExtrapolations = sensor.sumOfExtrapolations()

        then:
        sumOfExtrapolations == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day9/a.txt") | 114            | ""
        fromFile("day9/b.txt") | 1762065988     | ""
        fromFile("day9/c.txt") | 2              | ""
        fromFile("day9/d.txt") | 2              | ""
        fromFile("day9/e.txt") | 2              | ""
        fromFile("day9/f.txt") | 0              | ""
    }

    def "sum of all backwards extrapolated values"() {
        when:
        final OasisAndSandInstabilitySensor sensor = OasisAndSandInstabilitySensor.parse(lines)
        final long sumOfBackwardsExtrapolations = sensor.sumOfBackwardsExtrapolations()

        then:
        sumOfBackwardsExtrapolations == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day9/a.txt") | 2              | ""
        fromFile("day9/b.txt") | 1066           | ""
        fromFile("day9/c.txt") | 2              | ""
        fromFile("day9/d.txt") | 2              | ""
        fromFile("day9/e.txt") | 2              | ""
        fromFile("day9/f.txt") | -8             | ""
    }
}
