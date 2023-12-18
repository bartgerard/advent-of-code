package be.gerard.aoc2023.day17


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class CrucibleTest extends Specification {

    def "minimal heat loss when moving normal crucibles"() {
        when:
        final Crucible crucible = Crucible.parse(lines)
        final long minimalHeatLoss = crucible.minimalHeatLossBetween(
                crucible.grid().start(),
                crucible.grid().end()
        )

        then:
        minimalHeatLoss == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day17/a.txt") | 102            | ""
        fromFile("day17/b.txt") | 635            | ""

        fromFile("day17/c.txt") | 4              | ""
        fromFile("day17/d.txt") | 6              | ""
    }

    def "minimal heat loss when moving ultra crucibles"() {
        when:
        final Crucible crucible = Crucible.parse(lines)
        final long minimalHeatLoss = crucible.minimalHeatLossUsingUltraCruciblesBetween(
                crucible.grid().start(),
                crucible.grid().end()
        )

        then:
        minimalHeatLoss == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day17/a.txt") | 94             | ""
        fromFile("day17/b.txt") | 734            | ""
        fromFile("day17/e.txt") | 71             | ""
    }
}
