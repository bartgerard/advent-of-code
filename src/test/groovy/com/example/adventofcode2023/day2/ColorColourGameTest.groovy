package com.example.adventofcode2023.day2

import com.example.adventofcode2023.FileUtils
import spock.lang.Specification

class ColorColourGameTest extends Specification {

    def "sum games that can be played with max cubes by colour"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final List<ColourGame> games = ColourGame.parse(inputLines)
        final int sum = games.stream()
                .filter { it.canBePlayedWith(maxCubesByColour) }
                .mapToInt { it.id() }
                .sum()

        then:
        sum == expectedResult

        where:
        input        | maxCubesByColour                     | expectedResult | comment
        "day2/a.txt" | ["red": 12, "green": 13, "blue": 14] | 8              | ""
        "day2/b.txt" | ["red": 12, "green": 13, "blue": 14] | 2563           | ""
    }

    def "multiply min required cubes by colour to play game"() {
        given:
        final ColourGame game = ColourGame.parse(gameAsString)

        when:
        final int result = game.minRequiredCubesByColourMultiplied()

        then:
        result == expectedResult

        where:
        gameAsString                                                               | expectedResult | comment
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"                   | 48             | ""
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"         | 12             | ""
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red" | 1560           | ""
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red" | 630            | ""
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"                   | 36             | ""
    }

    def "sum all multiplications of min required cubes by colour to play game for all games"() {
        given:
        final List<String> inputLines = FileUtils.read(input)

        when:
        final List<ColourGame> games = ColourGame.parse(inputLines)
        final int sum = games.stream()
                .mapToInt { it.minRequiredCubesByColourMultiplied() }
                .sum()

        then:
        sum == expectedResult

        where:
        input        | expectedResult | comment
        "day2/a.txt" | 2286           | ""
        "day2/b.txt" | 70768          | ""
    }

}
