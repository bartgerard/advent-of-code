package be.gerard.adventofcode2023.util;

import java.util.regex.Pattern;

public record Line(
        int id,
        String value
) {

    public Tokens<Line> splitLine(final String regex) {
        return new Tokens<>(Tokens.split(value, regex)
                .stream()
                .map(value -> new Line(id, value))
                .toList()
        );
    }

    public Tokens<String> split(final String regex) {
        return new Tokens<>(Tokens.split(value, regex));
    }

    public Tokens<String> tokenize(final String regex) {
        return tokenize(Pattern.compile(regex));
    }

    public Tokens<String> tokenize(final Pattern pattern) {
        return Tokens.parse(value, pattern);
    }
}
