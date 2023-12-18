package be.gerard.aoc2023.day8

import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class NetworkTest extends Specification {

    def "how many steps are required to reach ZZZ"() {
        when:
        final Network camelCards = Network.parse(lines)
        final long requiredSteps = camelCards.stepsBetween("AAA", "ZZZ")

        then:
        requiredSteps == expectedRequiredSteps

        where:
        lines                  | expectedRequiredSteps | comment
        fromFile("day8/a.txt") | 2                     | ""
        fromFile("day8/b.txt") | 6                     | ""
        fromFile("day8/c.txt") | 12361                 | ""
    }

    def "how many steps does it take before you're only on nodes that end with Z"() {
        when:
        final Network camelCards = Network.parse(lines)
        final long requiredSteps = camelCards.multiverseStepsBetween(".*A", ".*Z")

        then:
        requiredSteps == expectedRequiredSteps

        where:
        lines                  | expectedRequiredSteps | comment
        fromFile("day8/a.txt") | 2                     | ""
        fromFile("day8/b.txt") | 6                     | ""
        fromFile("day8/c.txt") | 18215611419223        | ""
        fromFile("day8/d.txt") | 6                     | ""
    }

}
