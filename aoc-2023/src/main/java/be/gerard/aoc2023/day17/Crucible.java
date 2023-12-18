package be.gerard.aoc2023.day17;

import be.gerard.aoc.util.graph.Dijkstra;
import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.matrix.GenericMatrix;
import be.gerard.aoc.util.matrix.IntMatrix;
import be.gerard.aoc.util.point.Point2d;
import be.gerard.aoc.util.spatial.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

record Crucible(
        IntMatrix grid
) {

    static Crucible parse(final Lines<String> lines) {
        return new Crucible(lines.as(GenericMatrix::parse).mapToInt(Character::getNumericValue));
    }

    private List<Step> nextCrucibleSteps(final Step step) {
        final List<Step> nextSteps = new ArrayList<>();

        if (step.steps() < 3) {
            nextSteps.add(new Step(step.point().go(step.direction()), step.direction(), step.steps() + 1));
        }

        nextSteps.add(new Step(step.point().go(step.direction().turnLeft()), step.direction().turnLeft(), 1));
        nextSteps.add(new Step(step.point().go(step.direction().turnRight()), step.direction().turnRight(), 1));

        return nextSteps.stream()
                .filter(nextStep -> grid.isValid(nextStep.point()))
                .toList();
    }

    private List<Step> nextUltraCrucibleSteps(final Step step) {
        final List<Step> nextSteps = new ArrayList<>();

        if (step.steps() < 10) {
            nextSteps.add(new Step(step.point().go(step.direction()), step.direction(), step.steps() + 1));
        }

        if (step.steps() >= 4 || step.steps() == 0) {
            nextSteps.add(new Step(step.point().go(step.direction().turnLeft()), step.direction().turnLeft(), 1));
            nextSteps.add(new Step(step.point().go(step.direction().turnRight()), step.direction().turnRight(), 1));
        }

        return nextSteps.stream()
                .filter(nextStep -> grid.isValid(nextStep.point()))
                .toList();
    }

    long minimalHeatLossBetween(
            final Point2d start,
            final Point2d end
    ) {
        final Step firstStep = new Step(start, Direction.RIGHT, 0);

        final Dijkstra.Solution<Step> solution = Dijkstra.findShortestPath(
                firstStep,
                step -> Objects.equals(step.point(), end),
                (Function<Step, List<Step>>) this::nextCrucibleSteps,
                (source, destination) -> this.grid().at(destination.point())
        );

        return solution.cost();
    }

    long minimalHeatLossUsingUltraCruciblesBetween(
            final Point2d start,
            final Point2d end
    ) {
        final Step firstStep = new Step(start, Direction.RIGHT, 0);

        final Dijkstra.Solution<Step> solution = Dijkstra.findShortestPath(
                firstStep,
                step -> Objects.equals(step.point(), end) && step.steps() >= 4,
                (Function<Step, List<Step>>) this::nextUltraCrucibleSteps,
                (source, destination) -> this.grid().at(destination.point())
        );

        return solution.cost();
    }

    public record Step(
            Point2d point,
            Direction direction,
            int steps
    ) {
    }
}
