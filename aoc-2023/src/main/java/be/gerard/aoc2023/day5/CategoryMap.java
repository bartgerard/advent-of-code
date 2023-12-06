package be.gerard.aoc2023.day5;

import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Tokens;

import java.util.List;
import java.util.regex.Pattern;

public record CategoryMap(
        String sourceCategory,
        String destinationCategory,
        List<Transformation> transformations
) {

    public static final Pattern CATEGORY_MAPPING_PATTERN = Pattern.compile("(\\S*)-to-(\\S*)");

    public static CategoryMap parse(final Lines<String> lines) {
        final List<String> categories = Tokens.tokenize(lines.first(), CATEGORY_MAPPING_PATTERN);
        final List<Transformation> transformations = lines.nonFirst()
                .stream()
                .map(Transformation::parse)
                .toList();

        return new CategoryMap(
                categories.get(0),
                categories.get(1),
                transformations
        );
    }

    public long map(final long sourceValue) {
        return transformations.stream()
                .filter(transformation -> transformation.isApplicableFor(sourceValue))
                .map(transformation -> transformation.transform(sourceValue))
                .findFirst()
                .orElse(sourceValue);
    }
}
