package be.gerard.adventofcode2023.day1

import be.gerard.adventofcode2023.util.Line
import be.gerard.adventofcode2023.util.Numbers
import spock.lang.Specification

import static be.gerard.adventofcode2023.util.Lines.fromFile

class CalibrationValueTest extends Specification {

    def "sum of all of the calibration values"() {
        when:
        final int sum = CalibrationValue.calibrationValues(lines)
                .sum { it.value() }

        then:
        sum == expectedResult

        where:
        lines                  | expectedResult | comment
        fromFile("day1/a.txt") | 142            | ""
        fromFile("day1/b.txt") | 55108          | ""
    }

    def "sum of all of the calibration values lenient"() {
        when:
        final int sum = CalibrationValue.lenientCalibrationValues(lines)
                .sum { it.value() }

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
        final int number = CalibrationValue.from(new Line(0, line)
                .tokenize(CalibrationValue.LENIENT_NUMBER_REGEX)
                .map { Numbers.parseInt(it) }
        )
                .value()

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
