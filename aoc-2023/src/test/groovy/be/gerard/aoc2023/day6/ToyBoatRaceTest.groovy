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
        lines                  | expectedMargin | comment
        fromFile("day6/a.txt") | 288            | ""
        fromFile("day6/b.txt") | 3316275        | ""
        fromFile("day6/c.txt") | 71503          | ""
        fromFile("day6/d.txt") | 27102791       | ""
    }

}
