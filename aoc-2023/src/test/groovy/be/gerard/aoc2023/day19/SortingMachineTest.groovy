package be.gerard.aoc2023.day19


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class SortingMachineTest extends Specification {

    def "sum of all ratings from accepted parts"() {
        when:
        final SortingMachine sortingMachine = SortingMachine.parse(lines)
        final long sumOfRatings = sortingMachine.sumRatingsOfAcceptedParts()

        then:
        sumOfRatings == expectedResult

        where:
        lines                           | expectedResult | comment
        fromFile("day19/example_1.txt") | 19114          | ""
        fromFile("day19/input.txt")     | 353553         | ""
    }

    def "sum of all acceptable combinations for ratings between min and max"() {
        when:
        final SortingMachine sortingMachine = SortingMachine.parse(lines)
        final long sumOfRatings = sortingMachine.countAllAcceptableCombinationsWithRatingsBetween(minRating, maxRating)

        then:
        sumOfRatings == expectedResult

        where:
        lines                           | minRating | maxRating | expectedResult    | comment
        fromFile("day19/example_1.txt") | 1         | 4000      | 167409079868000   | ""

        fromFile("day19/extra_1.txt")   | 1         | 20        | 20 * 20 * 20 * 20 | ""
        fromFile("day19/extra_2.txt")   | 1         | 20        | 10 * 20 * 20 * 20 | ""
        fromFile("day19/extra_3.txt")   | 1         | 20        | 10 * 20 * 20 * 20 | ""

        fromFile("day19/input.txt")     | 1         | 4000      | 124615747767410   | ""
    }
}
