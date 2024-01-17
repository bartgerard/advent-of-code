package be.gerard.aoc2023.day22;

import be.gerard.aoc.util.geometry.BoundedBox;
import be.gerard.aoc.util.geometry.Vector;
import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.input.Tokens;
import be.gerard.aoc.util.math.LongRange;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingLong;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

record SandSlabs(
        List<Pair<Integer, BoundedBox>> bricks
) {

    public static final Comparator<Map.Entry<Integer, BoundedBox>> BRICK_COMPARATOR = comparingByValue(comparing(BoundedBox::z, comparingLong(LongRange::fromInclusive).thenComparingLong(LongRange::toInclusive)));

    SandSlabs(final List<Pair<Integer, BoundedBox>> bricks) {
        this.bricks = bricks.stream()
                .sorted(BRICK_COMPARATOR)
                .toList();
    }

    static SandSlabs parse(final Lines<String> lines) {
        return lines.mapLine((i, value) -> {
                    final List<Long> points = Tokens.asNumbers(value);
                    return Pair.of(
                            i,
                            new BoundedBox(
                                    LongRange.between(points.getFirst(), points.get(3)),
                                    LongRange.between(points.get(1), points.get(4)),
                                    LongRange.between(points.get(2), points.get(5))
                            )
                    );
                })
                .as(SandSlabs::new);
    }

    public static int numberOfImpactedBricksAfterRemovingBrick(
            final Map<Integer, Set<Integer>> supportingBricksBySupportedBrick,
            final int brickToBeRemoved
    ) {
        final Set<Integer> impactedBricks = new HashSet<>();
        impactedBricks.add(brickToBeRemoved);

        final Map<Integer, Set<Integer>> remainingBricks = new HashMap<>(supportingBricksBySupportedBrick);
        remainingBricks.remove(brickToBeRemoved);

        int previousSize = 0;

        while (previousSize < impactedBricks.size()) {
            previousSize = impactedBricks.size();

            final Set<Integer> newImpactedBricks = remainingBricks.entrySet()
                    .stream()
                    .filter(entry -> !entry.getValue().isEmpty() && impactedBricks.containsAll(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(toUnmodifiableSet());

            impactedBricks.addAll(newImpactedBricks);
            newImpactedBricks.forEach(remainingBricks::remove);
        }

        return impactedBricks.size() - 1;
    }

    SandSlabs gravity() {
        final List<Pair<Integer, BoundedBox>> settledBricks = new ArrayList<>();

        for (final Pair<Integer, BoundedBox> brickWithLabel : bricks) {
            final int label = brickWithLabel.getLeft();
            final BoundedBox brick = brickWithLabel.getRight();
            final long height = heightOfSupportingStructure(settledBricks, brick);

            final BoundedBox settledBrick = brick.translate(Vector.of(0, 0, height - brick.z().fromInclusive() + 1));
            settledBricks.add(Pair.of(label, settledBrick));
        }

        return new SandSlabs(settledBricks);
    }

    long heightOfSupportingStructure(
            final List<Pair<Integer, BoundedBox>> settledBricks,
            final BoundedBox brick
    ) {
        return settledBricks.stream()
                .map(Pair::getValue)
                .filter(brick::isAbove)
                .map(BoundedBox::z)
                .mapToLong(LongRange::toInclusive)
                .max()
                .orElse(0);
    }

    long countBricksThatCanBeDisintegratedWithoutOtherBricksFallingDown() {
        final Map<Integer, Set<Integer>> supportingBricksBySupportedBrick = supportingBricksBySupportedBrick();

        final Set<Integer> requiredBricks = supportingBricksBySupportedBrick.values()
                .stream()
                .filter(supportingBricks -> supportingBricks.size() == 1)
                .flatMap(Set::stream)
                .collect(toUnmodifiableSet());

        final Set<Integer> allBricks = bricks.stream()
                .map(Pair::getKey)
                .collect(toUnmodifiableSet());

        return allBricks.stream()
                .filter(Predicate.not(requiredBricks::contains))
                .count();
    }

    private Map<Integer, Set<Integer>> supportingBricksBySupportedBrick() {
        return bricks.stream()
                .collect(toUnmodifiableMap(
                        Pair::getKey,
                        brick -> findSupportingBricksFor(brick.getValue())
                                .stream()
                                .map(Pair::getKey)
                                .collect(toUnmodifiableSet())
                ));
    }

    Set<Pair<Integer, BoundedBox>> findSupportingBricksFor(final BoundedBox supportedBrick) {
        return bricks.stream()
                .filter(brick -> supportedBrick.isDirectlyAbove(brick.getValue()))
                .collect(toUnmodifiableSet());
    }

    int sumOfOtherBricksThatWouldFallWhenDisintegratingEachBrick() {
        final Map<Integer, Set<Integer>> supportingBricksBySupportedBrick = supportingBricksBySupportedBrick();

        final Set<Integer> requiredBricks = supportingBricksBySupportedBrick.values()
                .stream()
                .filter(supportingBricks -> supportingBricks.size() == 1)
                .flatMap(Set::stream)
                .collect(toUnmodifiableSet());

        return requiredBricks.stream()
                .mapToInt(brick -> numberOfImpactedBricksAfterRemovingBrick(supportingBricksBySupportedBrick, brick))
                .sum();
    }

}
