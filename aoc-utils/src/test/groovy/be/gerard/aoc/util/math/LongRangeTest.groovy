package be.gerard.aoc.util.math


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class LongRangeTest extends Specification {

    def "contains value"() {
        when:
        final boolean isContained = range.contains(value)

        then:
        isContained == expectedResult

        where:
        range              | value | expectedResult | comment
        LongRange.of(0)    | 0     | true           | ""
        LongRange.of(0)    | 1     | false          | ""
        LongRange.of(0)    | -1    | false          | ""

        LongRange.of(0, 5) | 0     | true           | ""
        LongRange.of(0, 5) | 1     | true           | ""
        LongRange.of(0, 5) | 5     | true           | ""
        LongRange.of(0, 5) | 6     | false          | ""
        LongRange.of(0, 5) | -1    | false          | ""
    }

    def "contains range"() {
        when:
        final boolean isContained = range1.contains(range2)

        then:
        isContained == expectedResult

        where:
        range1             | range2               | expectedResult | comment
        LongRange.of(0)    | LongRange.of(0)      | true           | ""
        LongRange.of(0)    | LongRange.of(1)      | false          | ""
        LongRange.of(0)    | LongRange.of(-1)     | false          | ""

        LongRange.of(0, 5) | LongRange.of(0)      | true           | ""
        LongRange.of(0, 5) | LongRange.of(1)      | true           | ""
        LongRange.of(0, 5) | LongRange.of(5)      | true           | ""
        LongRange.of(0, 5) | LongRange.of(6)      | false          | ""
        LongRange.of(0, 5) | LongRange.of(-1)     | false          | ""

        LongRange.of(0)    | LongRange.of(0, 5)   | false          | ""
        LongRange.of(1)    | LongRange.of(0, 5)   | false          | ""
        LongRange.of(5)    | LongRange.of(0, 5)   | false          | ""
        LongRange.of(6)    | LongRange.of(0, 5)   | false          | ""
        LongRange.of(-1)   | LongRange.of(0, 5)   | false          | ""

        LongRange.of(0, 5) | LongRange.of(0, 5)   | true           | ""
        LongRange.of(0, 5) | LongRange.of(5, 10)  | false          | ""
        LongRange.of(0, 5) | LongRange.of(6, 10)  | false          | ""
        LongRange.of(0, 5) | LongRange.of(-5, -1) | false          | ""
        LongRange.of(0, 5) | LongRange.of(-5, 0)  | false          | ""
        LongRange.of(0, 5) | LongRange.of(1, 2)   | true           | ""
        LongRange.of(1, 2) | LongRange.of(0, 5)   | false          | ""
    }

    def "overlaps"() {
        when:
        final boolean isContained = range1.overlaps(range2)

        then:
        isContained == expectedResult

        where:
        range1             | range2               | expectedResult | comment
        LongRange.of(0)    | LongRange.of(0)      | true           | ""
        LongRange.of(0)    | LongRange.of(1)      | false          | ""
        LongRange.of(0)    | LongRange.of(-1)     | false          | ""

        LongRange.of(0, 5) | LongRange.of(0)      | true           | ""
        LongRange.of(0, 5) | LongRange.of(1)      | true           | ""
        LongRange.of(0, 5) | LongRange.of(5)      | true           | ""
        LongRange.of(0, 5) | LongRange.of(6)      | false          | ""
        LongRange.of(0, 5) | LongRange.of(-1)     | false          | ""

        LongRange.of(0)    | LongRange.of(0, 5)   | true           | ""
        LongRange.of(1)    | LongRange.of(0, 5)   | true           | ""
        LongRange.of(5)    | LongRange.of(0, 5)   | true           | ""
        LongRange.of(6)    | LongRange.of(0, 5)   | false          | ""
        LongRange.of(-1)   | LongRange.of(0, 5)   | false          | ""

        LongRange.of(0, 5) | LongRange.of(0, 5)   | true           | ""
        LongRange.of(0, 5) | LongRange.of(5, 10)  | true           | ""
        LongRange.of(0, 5) | LongRange.of(6, 10)  | false          | ""
        LongRange.of(0, 5) | LongRange.of(-5, -1) | false          | ""
        LongRange.of(0, 5) | LongRange.of(-5, 0)  | true           | ""
        LongRange.of(0, 5) | LongRange.of(1, 2)   | true           | ""
        LongRange.of(1, 2) | LongRange.of(0, 5)   | true           | ""
    }

    def "intersect"() {
        when:
        final LongRange intersection = range1.intersect(range2)

        then:
        assertThat(intersection).isEqualTo(expectedIntersection)

        where:
        range1             | range2              | expectedIntersection | comment
        LongRange.of(0)    | LongRange.of(0)     | LongRange.of(0)      | ""

        LongRange.of(0, 5) | LongRange.of(0)     | LongRange.of(0)      | ""
        LongRange.of(0, 5) | LongRange.of(1)     | LongRange.of(1)      | ""
        LongRange.of(0, 5) | LongRange.of(5)     | LongRange.of(5)      | ""

        LongRange.of(0)    | LongRange.of(0, 5)  | LongRange.of(0)      | ""
        LongRange.of(1)    | LongRange.of(0, 5)  | LongRange.of(1)      | ""
        LongRange.of(5)    | LongRange.of(0, 5)  | LongRange.of(5)      | ""

        LongRange.of(0, 5) | LongRange.of(0, 5)  | LongRange.of(0, 5)   | ""
        LongRange.of(0, 5) | LongRange.of(5, 10) | LongRange.of(5)      | ""
        LongRange.of(0, 5) | LongRange.of(-5, 0) | LongRange.of(0)      | ""
        LongRange.of(0, 5) | LongRange.of(1, 2)  | LongRange.of(1, 2)   | ""
        LongRange.of(1, 2) | LongRange.of(0, 5)  | LongRange.of(1, 2)   | ""
    }

    def "unroll to list"() {
        when:
        final List<Long> numbers = range.toList()


        then:
        assertThat(numbers).containsExactlyElementsOf(expectedRange.toList())

        where:
        range              | expectedRange | comment
        LongRange.of(0)    | 0L..0L        | ""
        LongRange.of(0, 1) | 0L..1L        | ""
        LongRange.of(0, 5) | 0L..5L        | ""
    }

    def "all intervals"() {
        given:
        final List<LongRange> ranges = LongRange.parse(rangesAsStrings)
        final List<LongRange> expectedIntervals = LongRange.parse(expectedIntervalsAsStrings)

        when:
        final List<LongRange> intervals = LongRange.intervals(ranges)

        then:
        assertThat(intervals).containsAnyElementsOf(expectedIntervals)

        where:
        rangesAsStrings           | expectedIntervalsAsStrings    | comment
        []                        | []                            | ""
        ["0"]                     | ["0"]                         | ""
        ["0", "1"]                | ["0", "1"]                    | ""
        ["0..10", "1"]            | ["0", "1", "2..10"]           | ""
        ["0..10", "1..9"]         | ["0", "1..9", "10"]           | ""
        ["0..10", "1..9", "2..8"] | ["0", "1", "2..8", "9", "10"] | ""
        ["0..10", "1..8", "2..9"] | ["0", "1", "2..8", "9", "10"] | ""
    }

    def "all gaps"() {
        given:
        final List<LongRange> ranges = LongRange.parse(rangesAsStrings)
        final List<LongRange> expectedGaps = LongRange.parse(expectedGapsAsStrings)

        when:
        final List<LongRange> allGaps = LongRange.allGaps(ranges)

        then:
        assertThat(allGaps).containsAnyElementsOf(expectedGaps)

        where:
        rangesAsStrings                     | expectedGapsAsStrings | comment
        []                                  | []                    | ""
        ["0"]                               | []                    | ""
        ["0", "1"]                          | []                    | ""
        ["0..10", "1"]                      | []                    | ""
        ["0..10", "1..9"]                   | []                    | ""
        ["0..10", "1..9", "2..8"]           | []                    | ""
        ["0..10", "1..8", "2..9"]           | []                    | ""

        ["0", "2"]                          | ["1"]                 | ""
        ["0..10", "1..8", "2..9", "12..20"] | ["11"]                | ""
        ["0..10", "1..8", "2..9", "13..20"] | ["11..12"]            | ""
    }

    def "merge"() {
        given:
        final List<LongRange> ranges = LongRange.parse(rangesAsStrings)
        final List<LongRange> expectedMergedRanges = LongRange.parse(expectedMergedRangesAsStrings)

        when:
        final List<LongRange> mergedRanges = LongRange.merge(ranges)

        then:
        assertThat(mergedRanges).containsAnyElementsOf(expectedMergedRanges)

        where:
        rangesAsStrings                     | expectedMergedRangesAsStrings | comment
        []                                  | []                            | ""
        ["0"]                               | ["0"]                         | ""
        ["0", "1"]                          | ["0..1"]                      | ""
        ["0..10", "1"]                      | ["0..10"]                     | ""
        ["0..10", "1..9"]                   | ["0..10"]                     | ""
        ["0..10", "1..9", "2..8"]           | ["0..10"]                     | ""
        ["0..10", "1..8", "2..9"]           | ["0..10"]                     | ""

        ["0", "2"]                          | ["0", "2"]                    | ""
        ["0..10", "1..8", "2..9", "12..20"] | ["0..10", "12..20"]           | ""
    }

    def "subtract1"() {
        given:
        final LongRange minuend = LongRange.parse(minuendAsString)
        final LongRange subtrahend = LongRange.parse(subtrahendAsString)
        final List<LongRange> expectedDifference = LongRange.parse(expectedDifferenceAsStrings)

        when:
        final List<LongRange> difference = minuend.subtract(subtrahend)

        then:
        assertThat(difference).containsExactlyElementsOf(expectedDifference)

        where:
        minuendAsString | subtrahendAsString | expectedDifferenceAsStrings | comment
        "0"             | "0"                | []                          | ""
        "0"             | "1"                | ["0"]                       | ""
        "1"             | "0"                | ["1"]                       | ""

        "0..1"          | "0..1"             | []                          | ""
        "0..1"          | "0"                | ["1"]                       | ""
        "0..1"          | "1"                | ["0"]                       | ""

        "0..2"          | "0..2"             | []                          | ""
        "0..2"          | "0"                | ["1..2"]                    | ""
        "0..2"          | "1"                | ["0", "2"]                  | ""
        "0..2"          | "2"                | ["0..1"]                    | ""
        "0..2"          | "0..1"             | ["2"]                       | ""
        "0..2"          | "1..2"             | ["0"]                       | ""

        "0..4"          | "0..4"             | []                          | ""
        "0..4"          | "0"                | ["1..4"]                    | ""
        "0..4"          | "1"                | ["0", "2..4"]               | ""
        "0..4"          | "2"                | ["0..1", "3..4"]            | ""
        "0..4"          | "3"                | ["0..2", "4"]               | ""
        "0..4"          | "4"                | ["0..3"]                    | ""
        "0..4"          | "0..1"             | ["2..4"]                    | ""
        "0..4"          | "1..2"             | ["0", "3..4"]               | ""
        "0..4"          | "2..3"             | ["0..1", "4"]               | ""
        "0..4"          | "3..4"             | ["0..2"]                    | ""

    }

    def "subtract"() {
        given:
        final LongRange minuend = LongRange.parse(minuendAsString)
        final List<LongRange> subtrahends = LongRange.parse(subtrahendsAsStrings)
        final List<LongRange> expectedDifference = LongRange.parse(expectedDifferenceAsStrings)

        when:
        final List<LongRange> difference = minuend.subtract(subtrahends)

        then:
        assertThat(difference).containsExactlyElementsOf(expectedDifference)

        where:
        minuendAsString | subtrahendsAsStrings | expectedDifferenceAsStrings | comment
        "0"             | ["0"]                | []                          | ""
        "0"             | ["1"]                | ["0"]                       | ""
        "1"             | ["0"]                | ["1"]                       | ""

        "0..1"          | ["0..1"]             | []                          | ""
        "0..1"          | ["0"]                | ["1"]                       | ""
        "0..1"          | ["1"]                | ["0"]                       | ""

        "0..2"          | ["0..2"]             | []                          | ""
        "0..2"          | ["0"]                | ["1..2"]                    | ""
        "0..2"          | ["1"]                | ["0", "2"]                  | ""
        "0..2"          | ["2"]                | ["0..1"]                    | ""
        "0..2"          | ["0..1"]             | ["2"]                       | ""
        "0..2"          | ["1..2"]             | ["0"]                       | ""

        "0..4"          | ["0..4"]             | []                          | ""
        "0..4"          | ["0"]                | ["1..4"]                    | ""
        "0..4"          | ["1"]                | ["0", "2..4"]               | ""
        "0..4"          | ["2"]                | ["0..1", "3..4"]            | ""
        "0..4"          | ["3"]                | ["0..2", "4"]               | ""
        "0..4"          | ["4"]                | ["0..3"]                    | ""
        "0..4"          | ["0..1"]             | ["2..4"]                    | ""
        "0..4"          | ["1..2"]             | ["0", "3..4"]               | ""
        "0..4"          | ["2..3"]             | ["0..1", "4"]               | ""
        "0..4"          | ["3..4"]             | ["0..2"]                    | ""

        "0..10"         | ["3..4", "7..8"]     | ["0..2", "5..6", "9..10"]   | ""
        "0..10"         | ["3..5", "6..8"]     | ["0..2", "9..10"]           | ""
        "0..10"         | ["3..6", "6..8"]     | ["0..2", "9..10"]           | ""
        "0..10"         | ["3..7", "6..8"]     | ["0..2", "9..10"]           | ""

        "0..10"         | ["0..4", "7..8"]     | ["5..6", "9..10"]           | ""
        "0..10"         | ["3..4", "7..20"]    | ["0..2", "5..6"]            | ""
        "0..10"         | ["-20..4", "7..20"]  | ["5..6"]                    | ""
        "0..10"         | ["-20..5", "6..20"]  | []                          | ""

    }
}
