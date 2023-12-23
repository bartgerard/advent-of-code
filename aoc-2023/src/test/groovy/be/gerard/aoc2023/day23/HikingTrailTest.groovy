package be.gerard.aoc2023.day23


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class HikingTrailTest extends Specification {

    def "find longest hike"() {
        when:
        final HikingTrail trail = HikingTrail.parse(lines)
        final long length = trail.findLengthOfLongestHike()

        then:
        length == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day23/example_1.txt") | 94             | ""
        fromFile("day23/input.txt")     | 0              | ""
    }
}
