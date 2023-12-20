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
        lines                   | expectedResult | comment
        fromFile("day19/a.txt") | 19114          | ""
        fromFile("day19/b.txt") | 353553         | ""
    }

    def "sum of all acceptable combinations for ratings between min and max"() {
        when:
        final SortingMachine sortingMachine = SortingMachine.parse(lines)
        final long sumOfRatings = sortingMachine.countAllAcceptableCombinationsWithRatingsBetween(minRating, maxRating)

        then:
        sumOfRatings == expectedResult

        where:
        lines                   | minRating | maxRating | expectedResult    | comment
        fromFile("day19/a.txt") | 1         | 4000      | 167409079868000   | ""
        fromFile("day19/b.txt") | 1         | 4000      | 124615747767410   | ""

        fromFile("day19/c.txt") | 1         | 20        | 20 * 20 * 20 * 20 | ""
        fromFile("day19/d.txt") | 1         | 20        | 10 * 20 * 20 * 20 | ""
        fromFile("day19/e.txt") | 1         | 20        | 10 * 20 * 20 * 20 | ""
    }
}
