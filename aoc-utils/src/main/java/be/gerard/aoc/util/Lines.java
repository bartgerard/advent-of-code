package be.gerard.aoc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static java.util.Objects.requireNonNull;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;
import static org.apache.commons.lang3.Validate.notEmpty;

public record Lines<T>(
        List<T> values
) {
    public Lines {
        notEmpty(values);
    }

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

    public <O> O apply(final Function<List<T>, O> function) {
        return function.apply(values);
    }

    public <O> O as(final Function<List<T>, O> function) {
        return function.apply(values);
    }

    public <O> List<O> asListOf(final BiFunction<Integer, T, O> lineMapper) {
        return IntStream.range(0, values.size())
                .mapToObj(i -> lineMapper.apply(i, values.get(i)))
                .toList();
    }

    public <O> Lines<O> map(final Function<T, O> lineMapper) {
        return values.stream()
                .map(lineMapper)
                .collect(collectingAndThen(
                        toUnmodifiableList(),
                        Lines::new
                ));
    }

    public <O> Lines<O> mapLine(final BiFunction<Integer, T, O> lineMapper) {
        return new Lines<>(asListOf(lineMapper));
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

    public Lines<Lines<T>> splitBy(final Predicate<T> predicate) {
        final int[] lineIndicesToBeSplit = IntStream.range(0, values.size())
                .filter(i -> predicate.test(values.get(i)))
                .toArray();

        final int[] groupBorders = IntStream.concat(
                        IntStream.of(-1, values.size()),
                        Arrays.stream(lineIndicesToBeSplit)
                )
                .sorted()
                .distinct()
                .toArray();

        return IntStream.range(1, groupBorders.length)
                .mapToObj(i -> values.subList(groupBorders[i - 1] + 1, groupBorders[i]))
                .filter(not(Collection::isEmpty))
                .map(Lines::new)
                .collect(collectingAndThen(
                        toUnmodifiableList(),
                        Lines::new
                ));
    }

    public T first() {
        return values.get(0);
    }

    public List<T> nonFirst() {
        return IntStream.range(1, values.size())
                .mapToObj(values::get)
                .toList();
    }

    public T last() {
        return values.get(values.size() - 1);
    }
}
