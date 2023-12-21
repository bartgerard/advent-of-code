package be.gerard.aoc2023.day9


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class OasisAndSandInstabilitySensorTest extends Specification {

    def "sum of all extrapolated values"() {
        when:
        final OasisAndSandInstabilitySensor sensor = OasisAndSandInstabilitySensor.parse(lines)
        final long sumOfExtrapolations = sensor.sumOfExtrapolations()

        then:
        sumOfExtrapolations == expectedResult

        where:
        lines                          | expectedResult | comment
        fromFile("day9/example_1.txt") | 114            | ""
        fromFile("day9/input.txt")     | 1762065988     | ""
        fromFile("day9/extra_1.txt")   | 2              | ""
        fromFile("day9/extra_2.txt")   | 2              | ""
        fromFile("day9/extra_3.txt")   | 2              | ""
        fromFile("day9/extra_4.txt")   | 0              | ""
    }

    def "sum of all backwards extrapolated values"() {
        when:
        final OasisAndSandInstabilitySensor sensor = OasisAndSandInstabilitySensor.parse(lines)
        final long sumOfBackwardsExtrapolations = sensor.sumOfBackwardsExtrapolations()

        then:
        sumOfBackwardsExtrapolations == expectedResult

        where:
        lines                          | expectedResult | comment
        fromFile("day9/example_1.txt") | 2              | ""
        fromFile("day9/input.txt")     | 1066           | ""
        fromFile("day9/extra_1.txt")   | 2              | ""
        fromFile("day9/extra_2.txt")   | 2              | ""
        fromFile("day9/extra_3.txt")   | 2              | ""
        fromFile("day9/extra_4.txt")   | -8             | ""
    }
}
