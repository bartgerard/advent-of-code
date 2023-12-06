package be.gerard.aoc2023.day3;

record PartNumber(
        int row,
        IntRange columnRange,
        int number
) {
    boolean adjacentTo(final GridPosition position) {
        return row - 1 <= position.row()
                && position.row() <= row + 1
                && columnRange().adjacentTo(position.column());
    }
}
