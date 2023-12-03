package be.gerard.adventofcode2023.day3


import spock.lang.Specification

import static be.gerard.adventofcode2023.util.Lines.fromFile

class EngineSchematicTest extends Specification {

    def "sum of all of the part numbers in the engine schematic"() {
        given:
        final EngineSchematic schematic = EngineSchematic.parse(lines)

        when:
        final int sum = schematic.partNumbers()
                .stream()
                .mapToInt { it.number() }
                .sum()

        then:
        sum == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day3/a.txt") | 4361           | ""
        fromFile("day3/b.txt") | 544664         | ""
    }

    def "sum of all ratios of every gear"() {
        given:
        final EngineSchematic schematic = EngineSchematic.parse(lines)

        when:
        final int sum = schematic.gearRatios()
                .stream()
                .mapToInt { it }
                .sum()

        then:
        sum == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day3/a.txt") | 467835         | ""
        fromFile("day3/b.txt") | 84495585       | ""
    }

}
