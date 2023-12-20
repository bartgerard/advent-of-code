package be.gerard.aoc2023.day19;

import be.gerard.aoc.util.input.Tokens;
import be.gerard.aoc.util.math.IntRange;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public interface Condition {
    String LOWER = "<";
    String HIGHER = ">";
    Pattern PATTERN = Pattern.compile("(\\w+)([><])(\\d+)");

    static Condition parse(final String value) {
        if (!value.contains(LOWER) && !value.contains(HIGHER)) {
            return alwaysTrue();
        }

        final List<String> tokens = Tokens.tokenize(value, PATTERN);
        final String category = tokens.getFirst();
        final int threshold = Integer.parseInt(tokens.get(2));
        return switch (tokens.get(1)) {
            case LOWER -> lowerThan(category, threshold);
            case HIGHER -> higherThan(category, threshold);
            default -> throw new IllegalArgumentException("unknown condition");
        };
    }

    static Condition alwaysTrue() {
        return new AlwaysTrue();
    }

    static Condition lowerThan(final String category, final int threshold) {
        return new Lower(category, threshold);
    }

    static Condition higherThan(final String category, final int threshold) {
        return new Higher(category, threshold);
    }

    boolean isApplicable(Part part);

    Map<Boolean, RangedCombination> split(RangedCombination combination);

    record Lower(
            String category,
            int threshold
    ) implements Condition {

        @Override
        public boolean isApplicable(final Part part) {
            return part.ratingFor(category) < threshold;
        }

        @Override
        public Map<Boolean, RangedCombination> split(final RangedCombination combination) {
            final IntRange range = combination.rangeFor(category);

            if (!range.contains(threshold)) {
                return Map.of(range.contains(threshold - 1), combination);
            }

            if (!range.contains(threshold - 1)) {
                return Map.of(false, combination);
            }

            return Map.of(
                    true, combination.with(category, IntRange.of(range.fromInclusive(), threshold - 1)),
                    false, combination.with(category, IntRange.of(threshold, range.toInclusive()))
            );
        }
    }

    record Higher(
            String category,
            int threshold
    ) implements Condition {

        @Override
        public boolean isApplicable(final Part part) {
            return threshold < part.ratingFor(category);
        }

        @Override
        public Map<Boolean, RangedCombination> split(final RangedCombination combination) {
            final IntRange range = combination.rangeFor(category);

            if (!range.contains(threshold)) {
                return Map.of(range.contains(threshold + 1), combination);
            }

            if (!range.contains(threshold + 1)) {
                return Map.of(false, combination);
            }

            return Map.of(
                    false, combination.with(category, IntRange.of(range.fromInclusive(), threshold)),
                    true, combination.with(category, IntRange.of(threshold + 1, range.toInclusive()))
            );
        }
    }

    record AlwaysTrue() implements Condition {

        @Override
        public boolean isApplicable(final Part part) {
            return true;
        }

        @Override
        public Map<Boolean, RangedCombination> split(final RangedCombination combination) {
            return Map.of(true, combination);
        }
    }
}
