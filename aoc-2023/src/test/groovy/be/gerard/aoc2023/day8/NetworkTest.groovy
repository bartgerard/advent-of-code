package be.gerard.aoc2023.day8

import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class NetworkTest extends Specification {

    def "todo"() {
        when:
        final Network camelCards = Network.parse(lines)
        final int requiredSteps = camelCards.stepsBetween("AAA", "ZZZ")

        then:
        requiredSteps == expectedRequiredSteps

        where:
        lines                  | expectedRequiredSteps | comment
        fromFile("day8/a.txt") | 2                     | ""
        fromFile("day8/b.txt") | 6                     | ""
        fromFile("day8/c.txt") | 12361                 | ""
    }

}
