package be.gerard.aoc2023.day12


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class HotSpringsTest extends Specification {

    def "todo"() {
        when:
        final HotSprings hotSprings = HotSprings.parse(lines)
        final long numberOfArrangements = hotSprings.unfold(unfoldFactor).sumOfAllPossibleArrangements()

        then:
        numberOfArrangements == expectedResult

        where:
        lines                   | unfoldFactor | expectedResult | comment
        fromFile("day12/a.txt") | 1            | 6              | ""
        fromFile("day12/b.txt") | 1            | 21             | ""
        fromFile("day12/c.txt") | 1            | 6488           | ""

        fromFile("day12/a.txt") | 5            | 6              | ""
        fromFile("day12/b.txt") | 5            | 525152         | ""
        fromFile("day12/c.txt") | 5            | 815364548481   | ""
    }

    def "count possible arrangements"() {
        when:
        final ConditionRecord record = ConditionRecord.parse(value)
        final long numberOfArrangements = record.countPossibleArrangements()
        final long numberOfArrangementsForReversedRecord = record.reverse().countPossibleArrangements()

        then:
        numberOfArrangements == expectedResult
        numberOfArrangementsForReversedRecord == expectedResult

        where:
        value                       | expectedResult | comment
        "???.### 1,1,3"             | 1              | ""
        ".??..??...?##. 1,1,3"      | 4              | ""
        "?#?#?#?#?#?#?#? 1,3,1,6"   | 1              | ""
        "????.#...#... 4,1,1"       | 1              | ""
        "????.######..#####. 1,6,5" | 4              | ""
        "?###???????? 3,2,1"        | 10             | ""

        "#???#?????.?#?. 2,1,2,1"   | 3              | ""
        "??????? 2,1"               | 10             | ""
        "????????#? 2,1,1"          | 10             | ""
        ".?##???#?##????.? 7,4,1"   | 2              | ""
    }
}