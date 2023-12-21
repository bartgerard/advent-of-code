package be.gerard.aoc2023.day6


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class ToyBoatRaceTest extends Specification {

    def "multiply number of ways you can beat the record for all races"() {
        when:
        final ToyBoatRace boatRace = ToyBoatRace.parse(lines)

        then:
        final long margin = boatRace.marginsMultiplied()
        margin == expectedMargin

        where:
        lines                          | expectedMargin | comment
        fromFile("day6/example_1.txt") | 288            | "part 1"
        fromFile("day6/input_1.txt")   | 3316275        | "part 1"

        fromFile("day6/example_2.txt") | 71503          | "part 2"
        fromFile("day6/input_2.txt")   | 27102791       | "part 2"
    }

}
