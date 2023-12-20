package be.gerard.aoc2023.day19;

import be.gerard.aoc.util.input.Lines;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

record SortingMachine(
        Map<String, Workflow> workflows,
        List<Part> parts
) {
    private static final String ACCEPT = "A";
    private static final String REJECT = "R";

    private static final Set<String> END_STATES = Set.of(ACCEPT, REJECT);

    static SortingMachine parse(final Lines<String> lines) {
        final Lines<Lines<String>> workflowsAndRatings = lines.splitBy(String::isBlank);

        final Map<String, Workflow> workflows = workflowsAndRatings.getFirst().as(Workflow::parse);
        final List<Part> parts = workflowsAndRatings.getLast().map(Part::parse).values();

        return new SortingMachine(workflows, parts);
    }

    List<Part> filterAcceptedParts() {
        return parts.stream()
                .filter(this::canAccept)
                .toList();
    }

    int sumRatingsOfAcceptedParts() {
        return filterAcceptedParts()
                .stream()
                .mapToInt(Part::sumOfRatings)
                .sum();
    }

    private boolean canAccept(final Part part) {
        String destination = "in";

        while (!END_STATES.contains(destination)) {
            destination = workflows.get(destination).applyOn(part);
        }

        return ACCEPT.equals(destination);
    }

    long countAllAcceptableCombinationsWithRatingsBetween(
            final int minRating,
            final int maxRating
    ) {
        final RangedCombination allCombinations = RangedCombination.between(minRating, maxRating);

        final List<RangedCombination> acceptableCombinations = new ArrayList<>();
        final Queue<WorkflowTask> queue = new LinkedList<>();
        queue.offer(new WorkflowTask("in", allCombinations));

        while (!queue.isEmpty()) {
            final WorkflowTask step = queue.poll();
            final String destination = step.destination();

            if (ACCEPT.equals(destination)) {
                acceptableCombinations.add(step.combinations());
                continue;
            } else if (REJECT.equals(destination)) {
                continue;
            }

            final List<WorkflowTask> nextSteps = workflows.get(destination).split(step.combinations());
            queue.addAll(nextSteps);
        }

        return acceptableCombinations.stream()
                .mapToLong(RangedCombination::countCombinations)
                .sum();
    }
}
