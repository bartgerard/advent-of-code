package be.gerard.aoc2023.day7


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class CamelCardsTest extends Specification {

    def "sum all winnings"() {
        when:
        final CamelCards camelCards = CamelCards.parse(lines)
        final long totalWinnings = camelCards.totalWinnings()

        then:
        totalWinnings == expectedTotalWinnings

        where:
        lines                  | expectedTotalWinnings | comment
        fromFile("day7/a.txt") | 6440                  | ""
        fromFile("day7/b.txt") | 250347426             | ""
    }

    def "sum all winnings when playing with jokers"() {
        when:
        final CamelCards camelCards = CamelCards.parseWithJokers(lines)
        final long totalWinnings = camelCards.totalWinnings()

        then:
        totalWinnings == expectedTotalWinnings

        where:
        lines                  | expectedTotalWinnings | comment
        fromFile("day7/a.txt") | 5905                  | ""
        fromFile("day7/b.txt") | 251224870             | ""
    }

}
