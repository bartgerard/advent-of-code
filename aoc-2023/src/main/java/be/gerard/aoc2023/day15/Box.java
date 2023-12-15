package be.gerard.aoc2023.day15;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

record Box(
        int number,
        List<Lens> slots
) {
    static Box withNumber(final int number) {
        return new Box(number, new ArrayList<>());
    }

    void add(final Lens lens) {
        final int index = slots.indexOf(lens);

        if (index < 0) {
            slots.add(lens);
        } else {
            slots.set(index, lens);
        }
    }

    void remove(final Lens lens) {
        slots.remove(lens);
    }

    int focussingPower() {
        return IntStream.range(0, slots.size())
                .map(i -> (number + 1) * (i + 1) * slots.get(i).focalLength())
                .sum();
    }
}
