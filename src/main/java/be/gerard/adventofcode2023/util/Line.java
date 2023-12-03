package be.gerard.adventofcode2023.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Line(
        int id,
        String value
) {

    public static List<String> split(
            final String value,
            final String regex
    ) {
        return Arrays.stream(value.split(regex))
                .toList();
    }

    public static List<String> tokenize(
            final String value,
            final Pattern pattern
    ) {
        return pattern.matcher(value)
                .results()
                .flatMap(matchResult -> matchResult.groupCount() == 0
                        ? Stream.of(matchResult.group())
                        : IntStream.rangeClosed(1, matchResult.groupCount())
                        .mapToObj(matchResult::group)
                )
                .toList();
    }

    public Tokens<String> split(final String regex) {
        return new Tokens<>(split(value, regex));
    }

    public Tokens<String> tokenize(final String regex) {
        return tokenize(Pattern.compile(regex));
    }

    public Tokens<String> tokenize(final Pattern pattern) {
        return new Tokens<>(tokenize(value, pattern));
    }
}
