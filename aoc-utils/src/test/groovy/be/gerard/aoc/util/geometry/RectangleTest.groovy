package be.gerard.aoc.util.geometry


import spock.lang.Specification

class RectangleTest extends Specification {

    def "overlaps"() {
        given:
        final Rectangle rectangle1 = Rectangle.parse(rectangle1AsString)
        final Rectangle rectangle2 = Rectangle.parse(rectangle2AsString)

        when:
        final boolean overlapping = rectangle1.overlaps(rectangle2)

        then:
        overlapping == expectedResult

        where:
        rectangle1AsString | rectangle2AsString | expectedResult | comment
        "0,0"              | "0,0"              | true           | ""
        "0,0"              | "1,1"              | false          | ""
        "1,1"              | "0,0"              | false          | ""

        "0..1,0..1"        | "0,0"              | true           | ""
        "0..1,0..1"        | "1,1"              | true           | ""
        "0..1,0..1"        | "0..1,0..1"        | true           | ""
        "0..1,0..1"        | "1..2,1..2"        | true           | ""
        "0..1,0..1"        | "2,2"              | false          | ""
        "0..1,0..1"        | "2..3,2..3"        | false          | ""

        "0,0"              | "0..1,0..1"        | true           | ""
        "1,1"              | "0..1,0..1"        | true           | ""
        "0..1,0..1"        | "0..1,0..1"        | true           | ""
        "1..2,1..2"        | "0..1,0..1"        | true           | ""
        "2,2"              | "0..1,0..1"        | false          | ""
        "2..3,2..3"        | "0..1,0..1"        | false          | ""
    }

    def "intersect"() {
        given:
        final Rectangle rectangle1 = Rectangle.parse(rectangle1AsString)
        final Rectangle rectangle2 = Rectangle.parse(rectangle2AsString)
        final Rectangle expectedIntersection = Rectangle.parse(expectedIntersectionAsString)

        when:
        final Rectangle intersection = rectangle1.intersect(rectangle2)

        then:
        intersection == expectedIntersection

        where:
        rectangle1AsString | rectangle2AsString | expectedIntersectionAsString | comment
        "0,0"              | "0,0"              | "0,0"                        | ""

        "0..1,0..1"        | "0,0"              | "0,0"                        | ""
        "0..1,0..1"        | "1,1"              | "1,1"                        | ""
        "0..1,0..1"        | "0..1,0..1"        | "0..1,0..1"                  | ""
        "0..1,0..1"        | "1..2,1..2"        | "1,1"                        | ""

        "0,0"              | "0..1,0..1"        | "0,0"                        | ""
        "1,1"              | "0..1,0..1"        | "1,1"                        | ""
        "0..1,0..1"        | "0..1,0..1"        | "0..1,0..1"                  | ""
        "1..2,1..2"        | "0..1,0..1"        | "1,1"                        | ""
    }

}
