package be.gerard.aoc2023.day5;

import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.LongRange;
import be.gerard.aoc.util.Tokens;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toUnmodifiableList;

public record Almanac(
        Set<LongRange> seeds,
        List<CategoryMap> mappings
) {
    static List<LongRange> parseSeeds(final String header) {
        return Tokens.asNumbers(header)
                .stream()
                .map(LongRange::of)
                .toList();
    }

    static List<LongRange> parseRangedSeeds(final String header) {
        final List<Long> numbers = Tokens.asNumbers(header);

        return IntStream.range(0, numbers.size() / 2)
                .map(i -> i * 2)
                .mapToObj(i -> {
                    final long rangeStart = numbers.get(i);
                    final long rangeLength = numbers.get(i + 1);

                    return LongRange.withLength(rangeStart, rangeLength);
                })
                .toList();
    }

    static Almanac parse(
            final Lines<String> lines,
            final Function<String, List<LongRange>> seedFunction
    ) {
        final Lines<Lines<String>> lineGroups = lines.splitBy(String::isBlank);
        final List<LongRange> seeds = seedFunction.apply(lineGroups.first().first());
        final List<CategoryMap> mapping = lineGroups.others()
                .stream()
                .map(CategoryMap::parse)
                .toList();

        return new Almanac(
                Set.copyOf(seeds),
                mapping
        );
    }

    List<CategoryMap> findMappingsBetween(
            final String sourceCategory,
            final String destinationCategory
    ) {
        final List<CategoryMap> possibleRoutes = mappings.stream()
                .filter(mapping -> Objects.equals(mapping.sourceCategory(), sourceCategory))
                .toList();

        final List<String> destinationCategories = possibleRoutes.stream()
                .map(CategoryMap::destinationCategory)
                .toList();

        if (destinationCategories.contains(destinationCategory)) {
            return possibleRoutes.stream()
                    .filter(mapping -> Objects.equals(mapping.destinationCategory(), destinationCategory))
                    .toList();
        } else {
            return possibleRoutes.stream()
                    .flatMap(route -> {
                        final List<CategoryMap> foundRoutes = findMappingsBetween(route.destinationCategory(), destinationCategory);

                        if (foundRoutes.isEmpty()) {
                            return Stream.empty();
                        } else {
                            final List<CategoryMap> path = Stream.concat(
                                            Stream.of(route),
                                            foundRoutes.stream()
                                    )
                                    .toList();

                            return Stream.of(path);
                        }
                    })
                    .findFirst()
                    .orElseGet(Collections::emptyList);
        }
    }

    long lowestLocation() {
        final List<CategoryMap> mappings = findMappingsBetween("seed", "location");

        final List<Transformation> combinedTransformations = mappings.stream()
                .map(CategoryMap::transformations)
                .collect(collectingAndThen(
                        toUnmodifiableList(),
                        Transformation::combine
                ));

        final List<LongRange> combinedSourceRanges = combinedTransformations.stream()
                .map(Transformation::sourceRange)
                .toList();

        final List<LongRange> usedIntersections = LongRange.usedIntersections(
                Stream.concat(seeds.stream(), combinedSourceRanges.stream()).toList(),
                seeds
        );

        final List<Long> lowestLocationCandidates = usedIntersections.stream().map(LongRange::fromInclusive).toList();

        return lowestLocationCandidates.stream()
                .mapToLong(seed -> Transformation.apply(combinedTransformations, seed))
                .min()
                .orElse(0);
    }

}
