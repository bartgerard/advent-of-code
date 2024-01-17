package be.gerard.aoc2023.day22


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class SandSlabsTest extends Specification {

    def "how many bricks could be safely chosen as the one to get disintegrated"() {
        when:
        final SandSlabs slabs = SandSlabs.parse(lines).gravity()

        final long numberOfBricks = slabs.countBricksThatCanBeDisintegratedWithoutOtherBricksFallingDown()

        then:
        numberOfBricks == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day22/example_1.txt") | 5              | ""
        fromFile("day22/input.txt")     | 468            | ""

        fromFile("day22/extra_1.txt")   | 3              | ""
        fromFile("day22/extra_2.txt")   | 5              | ""
        fromFile("day22/extra_3.txt")   | 1              | ""
        fromFile("day22/extra_4.txt")   | 7              | ""
    }

    def "sum of the number of other bricks that would fall when disintegrating each brick"() {
        when:
        final SandSlabs slabs = SandSlabs.parse(lines).gravity()

        final int numberOfBricks = slabs.sumOfOtherBricksThatWouldFallWhenDisintegratingEachBrick()

        then:
        numberOfBricks == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day22/example_1.txt") | 7              | ""
        fromFile("day22/input.txt")     | 75358          | ""

        // DDD
        // B C
        // AAA
        fromFile("day22/extra_1.txt")   | 3              | ""

        // EEE
        // A DDD
        // A B C
        fromFile("day22/extra_2.txt")   | 0              | ""

        // C
        // B
        // A
        fromFile("day22/extra_3.txt")   | 3              | ""

        //   III   // <-- this one will not fall when either A or E are removed
        // DDD HHH
        // B C F G
        // AAA EEE
        fromFile("day22/extra_4.txt")   | 6              | ""
    }


}
