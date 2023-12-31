package be.gerard.aoc2023.day6;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.input.Tokens;

import java.util.List;
import java.util.stream.IntStream;

record ToyBoatRace(
        List<Lap> laps
) {
    static ToyBoatRace parse(final Lines<String> lines) {
        final List<Long> times = Tokens.asNumbers(lines.getFirst());
        final List<Long> distances = Tokens.asNumbers(lines.getLast());

        return new ToyBoatRace(IntStream.range(0, times.size())
                .mapToObj(i -> new Lap(
                        times.get(i),
                        distances.get(i)
                ))
                .toList()
        );
    }

    long marginsMultiplied() {
        return laps.stream()
                .mapToLong(Lap::margin)
                .reduce(1, (x, y) -> x * y);
    }
}
