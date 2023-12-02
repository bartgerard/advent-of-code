package be.gerard.adventofcode2023.day1;


import java.util.Collection;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Numbers {
    private static final String NUMBER = "\\d|one|two|three|four|five|six|seven|eight|nine";
    public static final String ONE_NUMBER = "[^" + NUMBER + "]*(" + NUMBER + ").*";
    public static final String TWO_NUMBERS = ONE_NUMBER + "(" + NUMBER + ")[^" + NUMBER + "]*";
    private static final Pattern TWO_NUMBERS_PATTERN = Pattern.compile(TWO_NUMBERS);
    private static final Pattern ONE_NUMBER_PATTERN = Pattern.compile(ONE_NUMBER);

    private Numbers() {
        // no-op
    }

    static int sumAllFirstAndLastNumbers(final Collection<String> input) {
        return input.stream()
                .map(Numbers::firstAndLastNumber)
                .flatMapToInt(OptionalInt::stream)
                .sum();
    }

    static OptionalInt firstAndLastNumber(final String s) {
        final Matcher twoNumberMatcher = TWO_NUMBERS_PATTERN.matcher(s);

        if (twoNumberMatcher.find()) {
            return OptionalInt.of(toNumber(twoNumberMatcher.group(1)) * 10 + toNumber(twoNumberMatcher.group(2)));
        }

        final Matcher singleNumberMatcher = ONE_NUMBER_PATTERN.matcher(s);

        if (singleNumberMatcher.find()) {
            return OptionalInt.of(toNumber(singleNumberMatcher.group(1)) * 10 + toNumber(singleNumberMatcher.group(1)));
        }

        return OptionalInt.empty();
    }

    private static int toNumber(final String s) {
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
}
