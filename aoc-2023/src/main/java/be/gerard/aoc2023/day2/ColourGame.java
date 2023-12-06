package be.gerard.aoc2023.day2;

import be.gerard.aoc.util.Line;
import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Tokens;

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
    static Lines<ColourGame> parse(final Lines<String> lines) {
        return lines.mapLine(Line::new)
                .map(line -> line.split(": "))
                .map(ColourGame::from);
    }

    static ColourGame from(final Tokens<String> tokens) {
        return new ColourGame(
                Integer.parseInt(tokens.first().substring(5)),
                parseDrawings(tokens.last())
        );
    }

    private static List<Drawing> parseDrawings(final String drawings) {
        return new Tokens<>(Tokens.split(drawings, "; "))
                .as(Drawing::parse);
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

    int minRequiredCubesByColourMultiplied() {
        return minRequiredCubesByColour().values()
                .stream()
                .reduce(1, (x, y) -> x * y);
    }
}
