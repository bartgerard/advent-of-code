package be.gerard.aoc2023.day13


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class MirrorsTest extends Specification {

    def "summary of reflections"() {
        when:
        final Mirrors mirrors = Mirrors.parse(lines)
        final long summary = mirrors.summarize(numberOfSmudges)

        then:
        summary == expectedResult

        where:
        lines                           | numberOfSmudges | expectedResult | comment
        fromFile("day13/example_1.txt") | 0               | 405            | ""
        fromFile("day13/input.txt")     | 0               | 40006          | ""

        fromFile("day13/example_1.txt") | 1               | 400            | ""
        fromFile("day13/input.txt")     | 1               | 28627          | ""
    }
}
