package be.gerard.aoc2023.day7;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

record Hand(
        String labels,
        long[] frequencies,
        int[] strengths,
        long bid
) {
    public static Hand parse(final String value) {
        final String[] values = value.split(" ");

        final String labels = values[0];
        final long bid = Long.parseLong(values[1]);

        return new Hand(
                labels,
                toFrequencies(labels),
                toStrengths(labels),
                bid
        );
    }

    private static int[] toStrengths(final String labels) {
        return labels.chars()
                .map(Labels::parse)
                .toArray();
    }

    static List<LabelFrequency> toDistribution(final String labels) {
        final Map<Integer, Long> countByLabel = labels.chars()
                .map(Labels::parse)
                .boxed()
                .collect(groupingBy(
                        identity(),
                        counting()
                ));

        return countByLabel.entrySet()
                .stream()
                .map(entry -> new LabelFrequency(
                        entry.getValue(),
                        entry.getKey()
                ))
                .sorted()
                .toList();
    }

    static long[] toFrequencies(final String labels) {
        final Map<Integer, Long> countByLabel = labels.chars()
                .map(Labels::parse)
                .boxed()
                .collect(groupingBy(
                        identity(),
                        counting()
                ));

        final long[] frequencies = toDistribution(labels)
                .stream()
                .filter(frequency -> frequency.strength() != 0)
                .mapToLong(LabelFrequency::frequency)
                .toArray();

        if (frequencies.length == 0) {
            return new long[]{labels.length()};
        }

        if (countByLabel.containsKey(0)) {
            final Long numberOfJokers = countByLabel.get(0);

            if (numberOfJokers < labels.length()) {
                frequencies[0] += numberOfJokers;
            }
        }

        return frequencies;
    }

}
