package be.gerard.aoc2023.day20;

import be.gerard.aoc.util.signal.Pulse;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Frequencies {
    private long lows;
    private long highs;

    public Frequencies(
            final long lows,
            final long highs
    ) {
        this.lows = lows;
        this.highs = highs;
    }

    public static Frequencies of(final Collection<Signal> signals) {
        final Map<Pulse, Long> frequencies = signals.stream()
                .collect(groupingBy(
                        Signal::pulse,
                        counting()
                ));

        return new Frequencies(
                frequencies.getOrDefault(Pulse.LOW, 0L),
                frequencies.getOrDefault(Pulse.HIGH, 0L)
        );
    }

    public static Frequencies merge(final Collection<Frequencies> frequencies) {
        final long lows = frequencies.stream().mapToLong(Frequencies::lows).sum();
        final long highs = frequencies.stream().mapToLong(Frequencies::highs).sum();

        return new Frequencies(lows, highs);
    }

    public static Frequencies repeat(
            final List<Frequencies> frequencies,
            final int repetitions
    ) {
        final int cycle = frequencies.size();

        final Frequencies frequenciesForFullCycles = Frequencies.merge(frequencies).multiply(repetitions / cycle);
        final List<Frequencies> frequenciesForIncompleteCycles = IntStream.range(0, repetitions % cycle)
                .mapToObj(frequencies::get)
                .toList();

        final List<Frequencies> allCycles = Stream.concat(
                        Stream.of(frequenciesForFullCycles),
                        frequenciesForIncompleteCycles.stream()
                )
                .toList();

        return Frequencies.merge(allCycles);
    }

    public long lows() {
        return lows;
    }

    public long highs() {
        return highs;
    }

    public void track(final Signal signal) {
        if (signal.pulse() == Pulse.LOW) {
            lows++;
        } else {
            highs++;
        }
    }

    public long multiplied() {
        return lows * highs;
    }

    public Frequencies multiply(
            final int factor
    ) {
        return new Frequencies(
                lows * factor,
                highs * factor
        );
    }
}
