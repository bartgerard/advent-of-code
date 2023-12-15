package be.gerard.aoc2023.day15;

import java.util.Objects;

record Lens(
        String label,
        int focalLength
) {
    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Lens lens = (Lens) o;
        return Objects.equals(label, lens.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
