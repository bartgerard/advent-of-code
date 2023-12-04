package be.gerard.adventofcode2023.day3;

import be.gerard.adventofcode2023.util.Lines;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toUnmodifiableSet;

record EngineSchematic(
        List<SchematicLine> schematicLines
) {
    static EngineSchematic parse(final Lines<String> lines) {
        return new EngineSchematic(lines.asListOfIndexed(SchematicLine::new));
    }

    private static Set<Integer> symbolAdjacentIndices(final Set<Integer> indices) {
        return indices.stream()
                .flatMap(index -> Stream.of(
                        index - 1,
                        index,
                        index + 1
                ))
                .collect(toUnmodifiableSet());
    }

    List<PartNumber> partNumbers() {
        return schematicLines.stream()
                .flatMap(line -> {
                    final Set<Integer> symbolIndices = allSymbolIndicesApplicableForRow(line.row());
                    final Set<Integer> symbolAdjacentIndices = symbolAdjacentIndices(symbolIndices);

                    return line.candidatePartNumbers()
                            .stream()
                            .filter(candidateNumber -> candidateNumber.columnRange().containsAny(symbolAdjacentIndices));
                })
                .toList();
    }

    private Set<Integer> allSymbolIndicesApplicableForRow(final int row) {
        return Stream.of(
                        symbolIndicesForRow(row - 1),
                        symbolIndicesForRow(row),
                        symbolIndicesForRow(row + 1)
                )
                .flatMap(Set::stream)
                .collect(toUnmodifiableSet());
    }

    private Set<Integer> symbolIndicesForRow(final int row) {
        return 0 <= row && row < schematicLines.size()
                ? schematicLines.get(row).symbolIndices()
                : emptySet();
    }

    List<GridPosition> candidateGearPositions() {
        return schematicLines.stream()
                .map(SchematicLine::candidateGearPositions)
                .flatMap(Collection::stream)
                .toList();
    }

    List<Integer> gearRatios() {
        final List<PartNumber> partNumbers = partNumbers();

        final List<GridPosition> candidateGearPositions = candidateGearPositions();

        return candidateGearPositions.stream()
                .map(candidateGearPosition -> partNumbers.stream()
                        .filter(partNumber -> partNumber.adjacentTo(candidateGearPosition))
                        .toList()
                )
                .filter(candidateGearPartNumbers -> candidateGearPartNumbers.size() == 2)
                .map(gearPartNumbers -> gearPartNumbers.stream()
                        .map(PartNumber::number)
                        .reduce(1, (x, y) -> x * y)
                )
                .toList();
    }
}
