package be.gerard.aoc2023.day19;

import be.gerard.aoc.util.input.Tokens;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Ratings
x: Extremely cool looking
m: Musical (it makes a noise when you hit it)
a: Aerodynamic
s: Shiny
 */
public record Part(
        Map<String, Integer> ratings
) {
    static Part parse(final String value) {
        final Map<String, Integer> ratings = Tokens.split(value.substring(1, value.length() - 1), ",")
                .stream()
                .map(rating -> Tokens.split(rating, "="))
                .collect(Collectors.toUnmodifiableMap(
                        List::getFirst,
                        rating -> Integer.parseInt(rating.getLast()),
                        (x, y) -> x
                ));

        return new Part(ratings);
    }

    int ratingFor(final String category) {
        return ratings.get(category);
    }

    int sumOfRatings() {
        return ratings.values()
                .stream()
                .mapToInt(i -> i)
                .sum();
    }
}
