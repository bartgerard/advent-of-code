package be.gerard.aoc.util;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Tokens<T>(
        List<T> values
) {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+");

    public static Tokens<String> parse(
            final String value,
            final Pattern pattern
    ) {
        return new Tokens<>(tokenize(value, pattern));
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

    public static List<Long> asNumbers(final String value) {
        return parse(value, NUMBER_PATTERN).as(Long::parseLong);
    }

    public static List<Integer> asIntegers(final String value) {
        return parse(value, NUMBER_PATTERN).as(Integer::parseInt);
    }

    public static List<String> split(
            final String value,
            final String regex
    ) {
        return Arrays.stream(value.split(regex))
                .toList();
    }

    public T first() {
        return values.get(0);
    }

    public T last() {
        return values.get(values.size() - 1);
    }

    public Tokens<T> firstAndLast() {
        return new Tokens<>(List.of(
                first(),
                last()
        ));
    }

    public <O> List<O> as(final Function<T, O> converter) {
        return values.stream()
                .map(converter)
                .toList();
    }

    public <O> Tokens<O> map(final Function<T, O> converter) {
        return new Tokens<>(as(converter));
    }
}
