package be.gerard.aoc2023.day20;

import be.gerard.aoc.util.signal.Pulse;

public record Signal(
        String source,
        String destination,
        Pulse pulse
) {
    @Override
    public String toString() {
        return "%s -%s-> %s".formatted(source, pulse.name().toLowerCase(), destination);
    }
}
