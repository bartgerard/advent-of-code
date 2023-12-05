package be.gerard.adventofcode2023.day5


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class TransformationTest extends Specification {

    def "parsing"() {
        given:
        final Transformation transformation = Transformation.parse(transformationsAsString)

        when:
        final String input = transformation.toInput()

        then:
        assertThat(input).isEqualTo(transformationsAsString)

        where:
        transformationsAsString | comment
        "0 0 1"                 | ""
        "1 1 1"                 | ""
        "1 1 100"               | ""

        "0 1 1"                 | ""
        "0 1 100"               | ""

        "1 0 1"                 | ""
        "1 0 100"               | ""

        "0 1 100"               | ""

        "0 69 1"                | ""
        "0 69 1"                | ""
        "0 69 1"                | ""
        "1 0 69"                | ""
        "0 69 1"                | ""
    }

    def "combine transformations"() {
        given:
        final List<Transformation> previousTransformations = Transformation.parse(previousTransformationsAsStrings)
        final List<Transformation> nextTransformations = Transformation.parse(nextTransformationsAsStrings)
        final List<Transformation> expectedCombinedTransformations = Transformation.parse(expectedCombinedTransformationsAsStrings)

        when:
        final List<Transformation> combinedTransformations = Transformation.combine(previousTransformations, nextTransformations)

        then:
        assertThat(combinedTransformations).containsExactlyElementsOf(expectedCombinedTransformations)

        where:
        previousTransformationsAsStrings | nextTransformationsAsStrings | expectedCombinedTransformationsAsStrings                | comment
        ["0 0 1"]                        | ["0 0 1"]                    | []                                                      | ""
        ["1 1 1"]                        | ["1 1 1"]                    | []                                                      | ""
        ["1 1 100"]                      | ["1 1 100"]                  | []                                                      | ""

        ["0 1 1"]                        | ["1 0 1"]                    | []                                                      | ""
        ["0 1 100"]                      | ["1 0 100"]                  | []                                                      | ""

        ["1 0 1"]                        | ["2 1 1"]                    | ["2 0 1"]                                               | ""
        ["1 0 100"]                      | ["2 1 100"]                  | ["2 0 100"]                                             | ""

        ["0 1 100"]                      | ["1 0 50"]                   | ["50 51 50"]                                            | ""

        ["0 69 1"]                       | ["60 56 37"]                 | ["0 69 1", "60 56 37"]                                  | ""
        ["0 69 1"]                       | ["56 93 4"]                  | ["0 69 1", "56 93 4"]                                   | ""
        ["0 69 1"]                       | ["60 56 37", "56 93 4"]      | ["0 69 1", "60 56 37", "56 93 4"]                       | ""
        ["1 0 69"]                       | ["60 56 37", "56 93 4"]      | ["1 0 55", "60 55 14", "74 70 23", "56 93 4"]           | ""
        ["0 69 1", "1 0 69"]             | ["60 56 37", "56 93 4"]      | ["0 69 1", "1 0 55", "60 55 14", "74 70 23", "56 93 4"] | ""
    }

    def "combine consecutive transformations"() {
        given:
        final List<List<Transformation>> consecutiveTransformations = consecutiveTransformationsAsStrings.stream()
                .map { Transformation.parse(it) }
                .toList()
        final List<Transformation> expectedCombinedTransformations = Transformation.parse(expectedCombinedTransformationsAsStrings)

        when:
        final List<Transformation> combinedTransformations = Transformation.combine(consecutiveTransformations)

        then:
        assertThat(combinedTransformations).containsExactlyElementsOf(expectedCombinedTransformations)

        where:
        consecutiveTransformationsAsStrings             | expectedCombinedTransformationsAsStrings                | comment
        [["0 0 1"], ["0 0 1"]]                          | []                                                      | ""
        [["1 1 1"], ["1 1 1"]]                          | []                                                      | ""
        [["1 1 100"], ["1 1 100"]]                      | []                                                      | ""

        [["0 1 1"], ["1 0 1"]]                          | []                                                      | ""
        [["0 1 100"], ["1 0 100"]]                      | []                                                      | ""

        [["1 0 1"], ["2 1 1"]]                          | ["2 0 1"]                                               | ""
        [["1 0 100"], ["2 1 100"]]                      | ["2 0 100"]                                             | ""

        [["0 1 100"], ["1 0 50"]]                       | ["50 51 50"]                                            | ""

        [["0 69 1"], ["60 56 37"]]                      | ["60 56 37", "0 69 1"]                                  | ""
        [["0 69 1"], ["56 93 4"]]                       | ["0 69 1", "56 93 4"]                                   | ""
        [["0 69 1"], ["60 56 37", "56 93 4"]]           | ["60 56 37", "0 69 1", "56 93 4"]                       | ""
        [["1 0 69"], ["60 56 37", "56 93 4"]]           | ["1 0 55", "60 55 14", "74 70 23", "56 93 4"]           | ""
        [["0 69 1", "1 0 69"], ["60 56 37", "56 93 4"]] | ["1 0 55", "60 55 14", "0 69 1", "74 70 23", "56 93 4"] | ""
    }

}
