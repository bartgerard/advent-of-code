package be.gerard.aoc2023.day7;

final class Labels {
    private Labels() {
        // no-op
    }

    static int parse(final int value) {
        return switch ((char) value) {
            case 'A' -> 14;
            case 'K' -> 13;
            case 'Q' -> 12;
            case 'J' -> 11;
            case 'T' -> 10;
            default -> Character.getNumericValue(value);
        };
    }
}
