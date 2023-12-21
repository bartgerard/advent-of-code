package be.gerard.aoc.util.signal;

public enum Pulse {
    HIGH,
    LOW;

    public Pulse flip() {
        return switch (this) {
            case HIGH -> LOW;
            case LOW -> HIGH;
        };
    }
}
