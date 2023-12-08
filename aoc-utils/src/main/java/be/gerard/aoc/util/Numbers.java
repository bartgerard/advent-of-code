package be.gerard.aoc.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.requireNonNullElse;
import static java.util.stream.Collectors.toUnmodifiableMap;

public final class Numbers {
    private Numbers() {
        // no-op
    }

    public static int parseInt(final String s) {
        return switch (s) {
            case "one" -> 1;
            case "two" -> 2;
            case "three" -> 3;
            case "four" -> 4;
            case "five" -> 5;
            case "six" -> 6;
            case "seven" -> 7;
            case "eight" -> 8;
            case "nine" -> 9;
            default -> Integer.parseInt(s);
        };
    }

    public static Map<Integer, Integer> toPrimeFactors(final long value) {
        final Map<Integer, Integer> powerByPrimeFactor = new HashMap<>();

        long absNumber = Math.abs(value);

        for (int factor = 2; factor <= absNumber; factor++) {
            while (absNumber % factor == 0) {
                final int power = requireNonNullElse(powerByPrimeFactor.get(factor), 0);
                powerByPrimeFactor.put(factor, power + 1);
                absNumber /= factor;
            }
        }

        return unmodifiableMap(powerByPrimeFactor);
    }

    public static long lcm(final long[] values) {
        final Map<Integer, Integer> maxPowerByPrimeFactor = Arrays.stream(values)
                .mapToObj(Numbers::toPrimeFactors)
                .map(Map::entrySet)
                .flatMap(Set::stream)
                .collect(toUnmodifiableMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::max
                ));

        return maxPowerByPrimeFactor.entrySet()
                .stream()
                .map(entry -> (long) Math.pow(entry.getKey(), entry.getValue()))
                .reduce(1L, (x, y) -> x * y);
    }
}
