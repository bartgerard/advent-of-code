package be.gerard.adventofcode2023.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;

public record Lines<T>(
        List<T> values
) {
    public static Lines<String> fromFile(final String uri) {
        try (final InputStream is = ClassLoader.getSystemResourceAsStream(uri)) {
            return toLines(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("uri is invalid", e);
        }
    }

    public static Lines<String> fromUrl(final String url) {
        try (final InputStream is = new URL(url).openStream()) {
            return toLines(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("url is invalid", e);
        }
    }

    private static Lines<String> toLines(final InputStream is) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(requireNonNull(is)));
        return new Lines<>(reader.lines().toList());
    }

    public <O> List<O> as(final Function<T, O> lineMapper) {
        return values.stream()
                .map(lineMapper)
                .toList();
    }

    public <O> List<O> as(final BiFunction<Integer, T, O> lineMapper) {
        return IntStream.range(0, values.size())
                .mapToObj(i -> lineMapper.apply(i, values.get(i)))
                .toList();
    }

    public <O> Lines<O> map(final Function<T, O> lineMapper) {
        return new Lines<>(as(lineMapper));
    }

    public <O> Lines<O> map(final BiFunction<Integer, T, O> lineMapper) {
        return new Lines<>(as(lineMapper));
    }

    public Lines<T> filter(final Predicate<T> predicate) {
        return new Lines<>(values.stream()
                .filter(predicate)
                .toList()
        );
    }

    public int sum(final Function<T, Integer> numberConverter) {
        return values.stream()
                .mapToInt(numberConverter::apply)
                .sum();
    }
}
