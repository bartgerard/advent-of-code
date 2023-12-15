package be.gerard.aoc2023.day15


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class LensLibraryTest extends Specification {

    def "sum of hashes"() {
        when:
        final int sumOfHashes = LensLibrary.sumOfHashes(lines)

        then:
        sumOfHashes == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day15/a.txt") | 1320           | ""
        fromFile("day15/b.txt") | 503154         | ""
    }

    def "hash"() {
        when:
        final int hash = Hash.hash(value)

        then:
        hash == expectedResult

        where:
        value  | expectedResult | comment
        "rn=1" | 30             | ""
        "cm-"  | 253            | ""
        "qp=3" | 97             | ""
        "cm=2" | 47             | ""
        "qp-"  | 14             | ""
        "pc=4" | 180            | ""
        "ot=9" | 9              | ""
        "ab=5" | 197            | ""
        "pc-"  | 48             | ""
        "pc-"  | 48             | ""
        "pc=6" | 214            | ""
        "ot=7" | 231            | ""

        "rn"   | 0              | ""
        "cm"   | 0              | ""
        "qp"   | 1              | ""
    }

    def "focussing power of the resulting lens configuration"() {
        when:
        final LensLibrary library = LensLibrary.parse(lines)
        final int focussingPower = library.focussingPower();

        then:
        focussingPower == expectedResult

        where:
        lines                   | expectedResult | comment
        fromFile("day15/a.txt") | 145            | ""
        fromFile("day15/b.txt") | 251353         | ""
    }
}
