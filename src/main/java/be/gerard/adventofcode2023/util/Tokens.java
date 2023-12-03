package be.gerard.adventofcode2023.util;

import java.util.List;
import java.util.function.Function;

public record Tokens<T>(
        List<T> values
) {
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
