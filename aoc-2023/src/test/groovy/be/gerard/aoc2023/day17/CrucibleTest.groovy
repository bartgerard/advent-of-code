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
        lines                           | expectedResult | comment
        fromFile("day17/example_1.txt") | 102            | ""
        fromFile("day17/input.txt")     | 635            | ""

        fromFile("day17/extra_1.txt")   | 4              | ""
        fromFile("day17/extra_2.txt")   | 6              | ""
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
        lines                           | expectedResult | comment
        fromFile("day17/example_1.txt") | 94             | ""
        fromFile("day17/example_2.txt") | 71             | ""
        fromFile("day17/input.txt")     | 734            | ""
    }
}
