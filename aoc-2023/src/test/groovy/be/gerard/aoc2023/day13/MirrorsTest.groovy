package be.gerard.aoc2023.day13


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class MirrorsTest extends Specification {

    def "summary of reflections"() {
        when:
        final Mirrors mirrors = Mirrors.parse(lines)
        final long summary = mirrors.summarize(numberOfSmudges)

        then:
        summary == expectedResult

        where:
        lines                   | numberOfSmudges | expectedResult | comment
        fromFile("day13/a.txt") | 0               | 405            | ""
        fromFile("day13/b.txt") | 0               | 40006          | ""

        fromFile("day13/a.txt") | 1               | 400            | ""
        fromFile("day13/b.txt") | 1               | 28627          | ""
    }
}
