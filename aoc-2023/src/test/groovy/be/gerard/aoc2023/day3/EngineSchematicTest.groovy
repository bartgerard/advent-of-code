package be.gerard.aoc2023.day3


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class EngineSchematicTest extends Specification {

    def "sum of all of the part numbers in the engine schematic"() {
        given:
        final EngineSchematic schematic = lines.mapLine(SchematicLine::new).as(EngineSchematic::new)

        when:
        final int sum = schematic.partNumbers()
                .stream()
                .mapToInt { it.number() }
                .sum()

        then:
        sum == expectedResult

        where:
        lines                          | expectedResult | comment
        fromFile("day3/example_1.txt") | 4361           | ""
        fromFile("day3/input.txt")     | 544664         | ""
    }

    def "sum of all ratios of every gear"() {
        given:
        final EngineSchematic schematic = lines.mapLine(SchematicLine::new).as(EngineSchematic::new)

        when:
        final int sum = schematic.gearRatios()
                .stream()
                .mapToInt { it }
                .sum()

        then:
        sum == expectedResult

        where:
        lines                          | expectedResult | comment
        fromFile("day3/example_1.txt") | 467835         | ""
        fromFile("day3/input.txt")     | 84495585       | ""
    }

}
