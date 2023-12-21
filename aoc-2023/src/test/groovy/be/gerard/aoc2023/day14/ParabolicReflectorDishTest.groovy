package be.gerard.aoc2023.day14

import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class ParabolicReflectorDishTest extends Specification {

    def "total load on the dish after tilting north"() {
        when:
        final ParabolicReflectorDish dish = ParabolicReflectorDish.parse(lines)
        final long totalLoad = dish.tiltNorth().totalLoad()

        then:
        totalLoad == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day14/example_1.txt") | 136            | ""
        fromFile("day14/example_2.txt") | 136            | ""
        fromFile("day14/input.txt")     | 109345         | ""
    }

    def "total load on the dish after spinning"() {
        when:
        final ParabolicReflectorDish dish = ParabolicReflectorDish.parse(lines)
        final long totalLoad = dish.spin(cycles).totalLoad()

        then:
        totalLoad == expectedResult

        where:
        lines                           | cycles     | expectedResult | comment
        fromFile("day14/example_1.txt") | 1000000000 | 64             | ""
        fromFile("day14/example_2.txt") | 1000000000 | 64             | ""

        fromFile("day14/extra_1.txt")   | 1          | 4              | ""
        fromFile("day14/extra_1.txt")   | 2          | 2              | ""
        fromFile("day14/extra_1.txt")   | 3          | 3              | ""
        fromFile("day14/extra_1.txt")   | 4          | 4              | ""
        fromFile("day14/extra_1.txt")   | 5          | 2              | ""
        fromFile("day14/extra_1.txt")   | 6          | 3              | ""
        fromFile("day14/input.txt")     | 1000000000 | 112452         | ""
    }
}
