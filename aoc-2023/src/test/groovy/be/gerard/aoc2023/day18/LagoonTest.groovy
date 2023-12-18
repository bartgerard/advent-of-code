package be.gerard.aoc2023.day18


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class LagoonTest extends Specification {

    def "how many cubic meters can the lagoon hold"() {
        when:
        final Lagoon lagoon = Lagoon.parse(lines)
        final long volume = lagoon.volume()

        then:
        volume == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day18/a.txt") | -1             | ""
        fromFile("day18/b.txt") | -1             | ""
    }
}
