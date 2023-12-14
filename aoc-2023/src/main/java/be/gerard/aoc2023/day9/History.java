package be.gerard.aoc2023.day9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableList;

record History(
        List<Long> values
) {
    static boolean containsOnlyZeros(final List<Long> values) {
        return values.stream().allMatch(i -> i == 0);
    }

    static List<Long> differences(final List<Long> values) {
        return IntStream.range(1, values.size())
                .mapToObj(i -> values.get(i) - values.get(i - 1))
                .toList();
    }

    long extrapolate() {
        return allDifferences().stream()
                .mapToLong(differences -> differences.get(differences.size() - 1))
                .sum();
    }

    long extrapolateBackwards() {
        final List<List<Long>> allDifferences = new ArrayList<>(allDifferences());
        Collections.reverse(allDifferences);

        return allDifferences.stream()
                .mapToLong(differences -> differences.get(0))
                .reduce(0, (x, y) -> y - x);
    }

    private List<List<Long>> allDifferences() {
        final List<List<Long>> differences = new ArrayList<>();
        differences.add(values);

        for (int i = 1; i < values.size(); i++) {
            final List<Long> current = differences(differences.get(differences.size() - 1));
            differences.add(current);

            if (containsOnlyZeros(current)) {
                break;
            }
        }

        return unmodifiableList(differences);
    }
}
