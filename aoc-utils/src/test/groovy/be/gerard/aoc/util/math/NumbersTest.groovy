package be.gerard.aoc.util.math

import be.gerard.aoc.util.math.Numbers
import spock.lang.Specification

class NumbersTest extends Specification {

    def "triangular number for n"() {
        when:
        final long triangularNumber = Numbers.triangular(value)

        then:
        triangularNumber == expectedResult

        where:
        value | expectedResult | comment
        1     | 1              | ""
        2     | 3              | ""
        3     | 6              | ""
        4     | 10             | ""
        5     | 15             | ""
        6     | 21             | ""
    }

    def "combinations"() {
        when:
        final long combinations = Numbers.combinations(k, n)

        then:
        combinations == expectedResult

        where:
        k | n | expectedResult | comment
        0 | 1 | 1              | ""
        1 | 1 | 1              | ""

        0 | 2 | 1              | ""
        1 | 2 | 2              | ""
        2 | 2 | 1              | ""

        0 | 3 | 1              | ""
        1 | 3 | 3              | ""
        2 | 3 | 3              | ""
        3 | 3 | 1              | ""

        0 | 4 | 1              | ""
        1 | 4 | 4              | ""
        2 | 4 | 6              | ""
        3 | 4 | 4              | ""
        4 | 4 | 1              | ""

        0 | 5 | 1              | ""
        1 | 5 | 5              | ""
        2 | 5 | 10             | ""
        3 | 5 | 10             | ""
        4 | 5 | 5              | ""
        5 | 5 | 1              | ""

        0 | 6 | 1              | ""
        1 | 6 | 6              | ""
        2 | 6 | 15             | ""
        3 | 6 | 20             | ""
        4 | 6 | 15             | ""
        5 | 6 | 6              | ""
        6 | 6 | 1              | ""

        0 | 7 | 1              | ""
        1 | 7 | 7              | ""
        2 | 7 | 21             | ""
        3 | 7 | 35             | ""
        4 | 7 | 35             | ""
        5 | 7 | 21             | ""
        6 | 7 | 7              | ""
        7 | 7 | 1              | ""
    }

    def "simplex"() {
        when:
        final long simplex = Numbers.simplex(k, n)

        then:
        simplex == expectedResult

        where:
        k | n | expectedResult | comment
        0 | 1 | 1              | ""
        0 | 1 | 1              | ""
        0 | 2 | 1              | ""
        0 | 3 | 1              | ""
        0 | 4 | 1              | ""
        0 | 5 | 1              | ""

        1 | 0 | 1              | ""
        1 | 1 | 2              | ""
        1 | 2 | 3              | ""
        1 | 3 | 4              | ""
        1 | 4 | 5              | ""
        1 | 5 | 6              | ""

        2 | 0 | 1              | ""
        2 | 1 | 3              | ""
        2 | 2 | 6              | ""
        2 | 3 | 10             | ""
        2 | 4 | 15             | ""
        2 | 5 | 21             | ""
        2 | 6 | 28             | ""

        3 | 0 | 1              | ""
        3 | 1 | 4              | ""
        3 | 2 | 10             | ""
        3 | 3 | 20             | ""
        3 | 4 | 35             | ""
        3 | 5 | 56             | ""
        3 | 6 | 84             | ""

        4 | 0 | 1              | ""
        4 | 1 | 5              | ""
        4 | 2 | 15             | ""
        4 | 3 | 35             | ""
        4 | 4 | 70             | ""
        4 | 5 | 126            | ""
        4 | 6 | 210            | ""

        5 | 0 | 1              | ""
        5 | 1 | 6              | ""
        5 | 2 | 21             | ""
        5 | 3 | 56             | ""
        5 | 4 | 126            | ""
        5 | 5 | 252            | ""
        5 | 6 | 462            | ""
    }
}
