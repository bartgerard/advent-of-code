package be.gerard.adventofcode2023.day1


import spock.lang.Specification

import static be.gerard.Lines.fromFile

class NumbersTest extends Specification {

    def "sum all first and last numbers"() {
        when:
        final int sum = Numbers.sumAllFirstAndLastNumbers(lines)

        then:
        sum == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day1/a.txt") | 142            | ""
        fromFile("day1/b.txt") | 56324          | ""
        fromFile("day1/c.txt") | 281            | ""
    }

    def "first and last digit"() {
        when:
        final int number = Numbers.firstAndLastNumber(line).orElseThrow()

        then:
        number == expectedResult

        where:
        line               | expectedResult | comment
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
