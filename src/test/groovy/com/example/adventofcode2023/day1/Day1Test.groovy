package com.example.adventofcode2023.day1

import com.example.adventofcode2023.FileUtils
import spock.lang.Specification

class Day1Test extends Specification {

    def "sumAllFirstAndLastDigits"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final int sum = Day1.sumAllFirstAndLastDigits(inputLines)

        then:
        sum == expectedResult

        where:
        input        | expectedResult | comment
        "day1/a.txt" | 142            | ""
        "day1/b.txt" | 55108          | ""
    }

    def "sumAllFirstAndLastNumbers"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final int sum = Day1.sumAllFirstAndLastNumbers(inputLines)

        then:
        sum == expectedResult

        where:
        input        | expectedResult | comment
        "day1/a.txt" | 142            | ""
        "day1/b.txt" | 56324          | ""
        "day1/c.txt" | 281            | ""
    }

    def "firstAndLastDigit"() {
        when:
        final number = Day1.firstAndLastDigit(
                Day1.filterNumbers(input)
        )

        then:
        number == expectedResult

        where:
        input              | expectedResult | comment
        "two1nine"         | 29             | ""
        "eightwothree"     | 83             | ""
        "abcone2threexyz"  | 13             | ""
        "xtwone3four"      | 24             | ""
        "4nineeightseven2" | 42             | ""
        "zoneight234"      | 14             | ""
        "7pqrstsixteen"    | 76             | ""

        "oneight3"         | 13             | ""
        "two1oneight"      | 28             | ""
        "1oneight"         | 18             | ""
        "twoneight"        | 28             | ""
    }
}
