package be.gerard.aoc.util.matrix


import spock.lang.Specification

import static org.assertj.core.api.Assertions.assertThat

class GenericMatrixTest extends Specification {

    def "transpose"() {
        when:
        final GenericMatrix matrix = GenericMatrix.of(values)

        then:
        assertThat(matrix.transpose()).isEqualTo(GenericMatrix.of(expectedResult))

        where:
        values           | expectedResult   | comment
        [[1, 2], [3, 4]] | [[1, 3], [2, 4]] | ""
    }

    def "flip horizontal"() {
        when:
        final GenericMatrix matrix = GenericMatrix.of(values)

        then:
        assertThat(matrix.flipHorizontal()).isEqualTo(GenericMatrix.of(expectedResult))

        where:
        values                 | expectedResult         | comment
        [[1, 2], [3, 4]]       | [[2, 1], [4, 3]]       | ""
        [[1, 2, 3], [4, 5, 6]] | [[3, 2, 1], [6, 5, 4]] | ""
    }

    def "flip vertical"() {
        when:
        final GenericMatrix matrix = GenericMatrix.of(values)

        then:
        assertThat(matrix.flipVertical()).isEqualTo(GenericMatrix.of(expectedResult))

        where:
        values           | expectedResult   | comment
        [[1, 2], [3, 4]] | [[3, 4], [1, 2]] | ""
    }

    def "flip diagonal"() {
        when:
        final GenericMatrix matrix = GenericMatrix.of(values)

        then:
        assertThat(matrix.flipDiagonal()).isEqualTo(GenericMatrix.of(expectedResult))

        where:
        values                 | expectedResult         | comment
        [[1, 2], [3, 4]]       | [[4, 3], [2, 1]]       | ""
        [[1, 2, 3], [4, 5, 6]] | [[6, 5, 4], [3, 2, 1]] | ""
    }

    def "rotate left"() {
        when:
        final GenericMatrix matrix = GenericMatrix.of(values)

        then:
        assertThat(matrix.rotateLeft()).isEqualTo(GenericMatrix.of(expectedResult))

        where:
        values                 | expectedResult           | comment
        [[1, 2], [3, 4]]       | [[2, 4], [1, 3]]         | ""
        [[1, 2, 3], [4, 5, 6]] | [[3, 6], [2, 5], [1, 4]] | ""
    }

    def "rotate right"() {
        when:
        final GenericMatrix matrix = GenericMatrix.of(values)

        then:
        assertThat(matrix.rotateRight()).isEqualTo(GenericMatrix.of(expectedResult))

        where:
        values                 | expectedResult           | comment
        [[1, 2], [3, 4]]       | [[3, 1], [4, 2]]         | ""
        [[1, 2, 3], [4, 5, 6]] | [[4, 1], [5, 2], [6, 3]] | ""
    }
}
