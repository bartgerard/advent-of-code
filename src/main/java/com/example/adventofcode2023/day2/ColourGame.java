package com.example.adventofcode2023.day2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;

record ColourGame(
        int id,
        List<Drawing> drawings
) {
    static List<ColourGame> parse(final Collection<String> gamesAsString) {
        return gamesAsString.stream()
                .map(ColourGame::parse)
                .toList();
    }

    static ColourGame parse(final String gameAsString) {
        final String[] split = gameAsString.split(": ");

        return new ColourGame(
                Integer.parseInt(split[0].substring(5)),
                parseDrawings(split[1])
        );
    }

    private static List<Drawing> parseDrawings(final String drawings) {
        return Arrays.stream(drawings.split("; "))
                .map(Drawing::parse)
                .toList();
    }

    Map<String, Integer> minRequiredCubesByColour() {
        return drawings.stream()
                .map(Drawing::cubesByColour)
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(groupingBy(
                        Map.Entry::getKey,
                        mapping(
                                Map.Entry::getValue,
                                reducing(0, Integer::max)
                        )
                ));
    }

    boolean canBePlayedWith(final Map<String, Integer> maxCubesByColour) {
        final Map<String, Integer> minRequiredCubesByColour = minRequiredCubesByColour();

        return maxCubesByColour.entrySet()
                .stream()
                .allMatch(entry -> minRequiredCubesByColour.get(entry.getKey()) <= entry.getValue());
    }

    Integer minRequiredCubesByColourMultiplied() {
        return minRequiredCubesByColour().values()
                .stream()
                .reduce(1, (x, y) -> x * y);
    }
}
