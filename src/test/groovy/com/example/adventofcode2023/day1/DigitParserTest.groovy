package com.example.adventofcode2023.day1

import com.example.adventofcode2023.FileUtils
import spock.lang.Specification

class DigitParserTest extends Specification {

    def "sum all first and last digits"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final int sum = DigitParser.sumAllFirstAndLastDigits(inputLines)

        then:
        sum == expectedResult

        where:
        input        | expectedResult | comment
        "day1/a.txt" | 142            | ""
        "day1/b.txt" | 55108          | ""
    }

    def "sum all first and last numbers"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final int sum = DigitParser.sumAllFirstAndLastNumbers(inputLines)

        then:
        sum == expectedResult

        where:
        input        | expectedResult | comment
        "day1/a.txt" | 142            | ""
        "day1/b.txt" | 56324          | ""
        "day1/c.txt" | 281            | ""
    }

    def "first and last digit"() {
        when:
        final number = DigitParser.firstAndLastDigit(
                DigitParser.filterNumbers(input)
        )

        then:
        number == expectedResult

        where:
        input              | expectedResult | comment
        "1abc2"            | 12             | ""
        "pqr3stu8vwx"      | 38             | ""
        "a1b2c3d4e5f"      | 15             | ""
        "treb7uchet"       | 77             | ""

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
