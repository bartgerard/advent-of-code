package be.gerard.adventofcode2023.day3;

import be.gerard.adventofcode2023.util.Range;

record PartNumber(
        int row,
        Range columnRange,
        int number
) {
    boolean adjacentTo(final GridPosition position) {
        return row - 1 <= position.row()
                && position.row() <= row + 1
                && columnRange().adjacentTo(position.column());
    }
}
