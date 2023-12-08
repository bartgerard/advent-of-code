package be.gerard.aoc2023.day8;

import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Tokens;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

record Network(
        List<Direction> directions,
        Map<String, Node> nodes
) {
    Network(
            final List<Direction> directions,
            final List<Node> nodes
    ) {
        this(
                directions,
                nodes.stream()
                        .collect(toUnmodifiableMap(
                                Node::nodeId,
                                identity()
                        ))
        );
    }

    private static final Pattern PATTERN = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");

    static Network parse(final Lines<String> lines) {
        final Lines<Lines<String>> groups = lines.splitBy(String::isBlank);

        final String path = groups.first().first();
        final List<Direction> directions = path.chars()
                .mapToObj(Direction::parse)
                .toList();

        return groups.last()
                .map(line -> {
                    final List<String> tokens = Tokens.tokenize(line, PATTERN);

                    return new Node(
                            tokens.get(0),
                            tokens.get(1),
                            tokens.get(2)
                    );
                })
                .as(nodes -> new Network(directions, nodes));
    }

    int stepsBetween(
            final String sourceNodeId,
            final String destinationNodeId
    ) {
        final Node sourceNode = nodes.get(sourceNodeId);

        String currentNodeId = sourceNodeId;

        for (int i = 0; ; i++) {
            final Direction direction = directions.get(i % directions.size());

            final Node currentNode = nodes.getOrDefault(currentNodeId, sourceNode);
            currentNodeId = currentNode.go(direction);

            if (Objects.equals(currentNodeId, destinationNodeId)) {
                return i + 1;
            }
        }
    }
}
