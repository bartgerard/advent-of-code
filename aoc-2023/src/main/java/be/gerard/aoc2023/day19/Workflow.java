package be.gerard.aoc2023.day19;

import be.gerard.aoc.util.input.Tokens;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

record Workflow(
        List<Rule> rules
) {
    static Map<String, Workflow> parse(final List<String> values) {
        return values.stream()
                //"hdj{m>838:A,pv}"
                .map(value -> Tokens.split(value, "[{}]"))
                .collect(Collectors.toUnmodifiableMap(
                        List::getFirst,
                        value -> Workflow.parse(value.getLast()),
                        (x, y) -> x
                ));
    }

    private static Workflow parse(final String value) {
        // m>838:A,pv
        final List<Rule> rules = Tokens.split(value, ",")
                .stream()
                .map(Rule::parse)
                .toList();
        return new Workflow(rules);
    }

    public String applyOn(final Part part) {
        return rules.stream()
                .filter(rule -> rule.isApplicable(part))
                .findFirst()
                .map(Rule::destination)
                .orElseThrow();
    }

    public List<WorkflowTask> split(final RangedCombination combination) {
        final List<WorkflowTask> nextSteps = new ArrayList<>();
        RangedCombination previousCombination = combination;

        for (final Rule rule : rules) {
            final Map<Boolean, RangedCombination> split = rule.condition().split(previousCombination);

            if (split.containsKey(true)) {
                nextSteps.add(new WorkflowTask(rule.destination(), split.get(true)));
            }

            if (!split.containsKey(false)) {
                break;
            }

            previousCombination = split.get(false);
        }

        return unmodifiableList(nextSteps);
    }
}
