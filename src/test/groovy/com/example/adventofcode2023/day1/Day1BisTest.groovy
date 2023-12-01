package com.example.adventofcode2023.day1

import com.example.adventofcode2023.FileUtils
import spock.lang.Specification

class Day1BisTest extends Specification {

    def "sumAllFirstAndLastNumbers"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final int sum = Day1Bis.sumAllFirstAndLastNumbers(inputLines)

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
        final int number = Day1Bis.firstAndLastNumber(input).orElseThrow()

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
