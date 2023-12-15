package be.gerard.aoc2023.day15;

final class Hash {
    private Hash() {
        // no-op
    }

    static int hash(final String s) {
        int currentValue = 0;

        for (int i = 0; i < s.length(); i++) {
            final int code = s.charAt(i);
            currentValue = ((currentValue + code) * 17) % 256;
        }

        return currentValue;
    }
}
