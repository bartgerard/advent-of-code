package be.gerard.aoc2023.day21


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class GardenTest extends Specification {

    def "how many garden plots could the Elf reach in exactly #steps steps"() {
        when:
        final Garden counter = Garden.parse(lines)
        final long numberOfPlots = counter.numberOfPlotsReachableAfter(steps)

        then:
        numberOfPlots == expectedResult

        where:
        lines                           | steps | expectedResult | comment
        fromFile("day21/example_1.txt") | 6     | 16             | ""
        fromFile("day21/input.txt")     | 64    | 3768           | ""
    }

    def "how many garden plots could the Elf reach in exactly #steps steps in an infinite garden"() {
        when:
        final Garden counter = Garden.parse(lines)
        final long numberOfPlots = counter.infinite().numberOfPlotsReachableAfter(steps)

        then:
        numberOfPlots == expectedResult

        where:
        lines                           | steps | expectedResult | comment
        fromFile("day21/example_1.txt") | 6     | 16             | ""
        fromFile("day21/example_1.txt") | 10    | 50             | ""
        fromFile("day21/example_1.txt") | 50    | 1594           | ""
        fromFile("day21/example_1.txt") | 100   | 6536           | ""
        fromFile("day21/example_1.txt") | 500   | 167004         | ""
        fromFile("day21/example_1.txt") | 1000  | 668697         | ""
        fromFile("day21/example_1.txt") | 5000  | 16733044       | ""

        fromFile("day21/input.txt")     | 64    | 3768           | ""
    }

}
