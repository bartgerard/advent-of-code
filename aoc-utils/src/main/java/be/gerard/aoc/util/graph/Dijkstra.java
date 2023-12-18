package be.gerard.aoc.util.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.reverse;
import static java.util.Collections.unmodifiableList;
import static java.util.Comparator.comparingInt;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.function.Predicate.not;

public final class Dijkstra {
    private Dijkstra() {
        // no-op
    }

    public static <E> Solution<E> findShortestPath(
            final E source,
            final Predicate<E> endPredicate,
            final Function<E, ? extends Collection<E>> neighbourFunction,
            final BiFunction<E, E, Integer> costFunction
    ) {
        final PriorityQueue<Vertex<E>> nextVertices = new PriorityQueue<>(comparingInt(Vertex::cost));
        nextVertices.add(new Vertex<>(source, 0));

        final Map<E, Path<E>> path = new HashMap<>();
        path.put(source, new Path<>(null, 0));

        while (!nextVertices.isEmpty()) {
            final Vertex<E> currentVertex = nextVertices.poll();
            final E currentEdge = currentVertex.destination();

            if (endPredicate.test(currentEdge)) {
                return new Solution<>(source, currentEdge, path);
            }

            final List<Vertex<E>> newVertices = neighbourFunction.apply(currentEdge)
                    .stream()
                    .filter(not(path::containsKey))
                    .map(edge -> new Vertex<>(
                            edge,
                            currentVertex.cost() + costFunction.apply(currentEdge, edge)
                    ))
                    .toList();

            nextVertices.addAll(newVertices);
            newVertices.forEach(newVertex -> path.put(newVertex.destination(), new Path<>(currentEdge, newVertex.cost())));
        }

        return new Solution<>(source, null, path);
    }

    private record Vertex<E>(
            E destination,
            int cost
    ) {
    }

    private record Path<E>(
            E source,
            int cost
    ) {
    }

    public record Solution<E>(
            E source,
            E destination,
            Map<E, Path<E>> path
    ) {
        public int cost() {
            final Path<E> lastStep = path.get(destination);
            return isNull(lastStep) ? Integer.MAX_VALUE : lastStep.cost();
        }

        public List<E> fullPath() {
            final List<E> result = new ArrayList<>();
            E current = destination;

            while (nonNull(current)) {
                result.add(current);
                current = path.get(current).source();
            }

            reverse(result);

            return unmodifiableList(result);
        }
    }
}
