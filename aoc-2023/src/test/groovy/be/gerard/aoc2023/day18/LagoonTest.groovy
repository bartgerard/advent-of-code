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
        fromFile("day18/a.txt") | 62             | ""
        fromFile("day18/b.txt") | 48652          | ""

        fromFile("day18/c.txt") | 20             | ""
        fromFile("day18/d.txt") | 27             | ""
    }

    def "how many cubic meters can the lagoon hold using the hexadecimal dig plan"() {
        when:
        final Lagoon lagoon = Lagoon.parseHexadecimal(lines)
        final long volume = lagoon.volume()

        then:
        volume == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day18/a.txt") | 952408144115   | ""
        fromFile("day18/b.txt") | 45757884535661 | ""
    }
}
