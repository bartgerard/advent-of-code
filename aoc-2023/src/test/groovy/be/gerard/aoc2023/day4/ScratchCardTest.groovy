package be.gerard.aoc2023.day4


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class ScratchCardTest extends Specification {

    def "total worth in points of all scratchcards"() {
        when:
        final int sum = ScratchCard.parse(lines)
                .sum { it.points() }

        then:
        sum == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day4/a.txt") | 13             | ""
        fromFile("day4/b.txt") | 21138          | ""
    }

    def "total number of scratchcards won"() {
        when:
        final int sum = ScratchCard.parse(lines)
                .apply { ScratchCard.totalNumberOfScratchCardsWon(it) }

        then:
        sum == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day4/a.txt") | 30             | ""
        fromFile("day4/b.txt") | 7185540        | ""
    }

}
