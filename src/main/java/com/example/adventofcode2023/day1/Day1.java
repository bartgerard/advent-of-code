package com.example.adventofcode2023.day1;


import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.reverseOrder;

public class Day1 {

    private static final Map<String, Integer> NUMBER_MAP = Map.ofEntries(
            Map.entry("one", 1),
            Map.entry("two", 2),
            Map.entry("three", 3),
            Map.entry("four", 4),
            Map.entry("five", 5),
            Map.entry("six", 6),
            Map.entry("seven", 7),
            Map.entry("eight", 8),
            Map.entry("nine", 9)
    );

    static int sumAllFirstAndLastDigits(final Collection<String> input) {
        return input.stream()
                .map(Day1::filterDigits)
                .mapToInt(Day1::firstAndLastDigit)
                .sum();
    }

    static int sumAllFirstAndLastNumbers(final Collection<String> input) {
        return input.stream()
                .map(Day1::filtersNumbers)
                .mapToInt(Day1::firstAndLastDigit)
                .sum();
    }

    static Optional<Integer> firstNumber(final String s) {
        return NUMBER_MAP.entrySet()
                .stream()
                .map(entry -> Pair.of(s.indexOf(entry.getKey()), entry.getValue()))
                .filter(entry -> entry.getLeft() >= 0)
                .min(comparingInt(Pair::getLeft))
                .map(Pair::getRight);
    }

    static Optional<Integer> lastNumber(final String s) {
        return NUMBER_MAP.entrySet()
                .stream()
                .map(entry -> Pair.of(s.lastIndexOf(entry.getKey()), entry.getValue()))
                .filter(entry -> entry.getLeft() >= 0)
                .max(comparingInt(Pair::getLeft))
                .map(Pair::getRight);
    }

    static int[] filterDigits(final String s) {
        return s.chars()
                .filter(Character::isDigit)
                .map(Character::getNumericValue)
                .toArray();
    }

    static int[] filtersNumbers(final String s) {
        return new int[]{
                IntStream.range(0, s.length())
                        .boxed()
                        .filter(i -> Character.isDigit(s.charAt(i)))
                        .findFirst()
                        .map(i -> firstNumber(s.substring(0, i))
                                .orElseGet(() -> Character.getNumericValue(s.charAt(i)))
                        )
                        .or(() -> firstNumber(s))
                        .orElseThrow(),
                IntStream.range(0, s.length())
                        .boxed()
                        .sorted(reverseOrder())
                        .filter(i -> Character.isDigit(s.charAt(i)))
                        .findFirst()
                        .map(i -> lastNumber(s.substring(i))
                                .orElseGet(() -> Character.getNumericValue(s.charAt(i)))
                        )
                        .or(() -> lastNumber(s))
                        .orElseThrow()
        };
    }

    static int firstAndLastDigit(final int[] digits) {
        return digits[0] * 10 + digits[digits.length - 1];
    }

}
