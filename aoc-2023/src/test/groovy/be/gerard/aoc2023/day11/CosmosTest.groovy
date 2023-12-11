package be.gerard.aoc2023.day11


import spock.lang.Specification

import static be.gerard.aoc.util.Lines.fromFile

class CosmosTest extends Specification {

    def "sum of all shortest paths between galaxies taking into account the expansion of the void"() {
        when:
        final Cosmos cosmos = Cosmos.parse(lines)
        final long sumOfShortestPaths = cosmos.expand(expansionFactor).sumOfShortestPaths()

        then:
        sumOfShortestPaths == expectedSumOfShortestPaths

        where:
        lines                   | expansionFactor | expectedSumOfShortestPaths | comment
        fromFile("day11/a.txt") | 2               | 374                        | ""
        fromFile("day11/b.txt") | 2               | 10_231_178                 | ""

        fromFile("day11/a.txt") | 10              | 1030                       | ""
        fromFile("day11/a.txt") | 100             | 8410                       | ""

        fromFile("day11/b.txt") | 1_000_000       | 622120986954               | ""
    }
}
