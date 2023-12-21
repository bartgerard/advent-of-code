package be.gerard.aoc2023.day20;

import be.gerard.aoc.util.input.Lines;
import be.gerard.aoc.util.math.Numbers;
import be.gerard.aoc.util.signal.Pulse;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.isNull;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.apache.commons.lang3.Validate.notNull;

public record PulseModules(
        Map<String, PulseModule> modules
) {
    public PulseModules(
            final Map<String, PulseModule> modules
    ) {
        this.modules = modules;
        modules.values().forEach(module -> module.destinations()
                .stream()
                .map(modules::get)
                .filter(Objects::nonNull)
                .forEach(destinationModule -> destinationModule.connect(module))
        );
    }

    static PulseModules parse(final Lines<String> lines) {
        return lines.map(PulseModule::parse)
                .as(modules -> modules.stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toUnmodifiableMap(
                                        PulseModule::moduleName,
                                        identity()
                                ),
                                PulseModules::new
                        ))
                );
    }

    public boolean isInitialState() {
        return modules.values().stream().allMatch(PulseModule::isInitialState);
    }

    Frequencies pulsesAfter(final int numberOfPushes) {
        final List<Frequencies> frequencies = new ArrayList<>();

        for (int i = 0; i < numberOfPushes && (i == 0 || !isInitialState()); i++) {
            final List<Signal> signals = press();

            frequencies.add(Frequencies.of(signals));
        }

        return Frequencies.repeat(frequencies, numberOfPushes);
    }

    private List<Signal> press() {
        final Queue<Signal> queue = new LinkedList<>();
        queue.offer(PulseModule.Button.press());

        final List<Signal> signals = new ArrayList<>();

        while (!queue.isEmpty()) {
            final Signal signal = queue.poll();

            signals.add(signal);

            final PulseModule nextModule = getModule(signal.destination());

            if (isNull(nextModule)) {
                continue;
            }

            final List<Signal> newSignals = nextModule.receive(signal);
            queue.addAll(newSignals);
        }

        return signals;
    }

    private PulseModule getModule(final String moduleName) {
        return modules.get(moduleName);
    }

    long numberOfPushesRequiredForModuleToReceivePulse(
            final String moduleName,
            final Pulse pulse,
            final Map<String, Long> cache
    ) {
        if (cache.containsKey(moduleName)) {
            return cache.get(moduleName);
        }

        final PulseModule module = modules.get(moduleName);
        final List<PulseModule> inputModules = findModulesWithDestination(moduleName);

        return switch (module) {
            case PulseModule.Broadcast broadcast -> 1;
            case PulseModule.Conjunction conjunction -> {
                final long result;
                if (inputModules.stream().allMatch(inputModule -> inputModule instanceof PulseModule.FlipFlop)) {
                    result = inputModules.stream()
                            .mapToLong(inputModule -> numberOfPushesRequiredForModuleToReceivePulse(
                                    inputModule.moduleName(),
                                    pulse,
                                    cache
                            ))
                            .sum();
                } else {
                    final long[] inputs = inputModules.stream()
                            .mapToLong(inputModule -> numberOfPushesRequiredForModuleToReceivePulse(
                                    inputModule.moduleName(),
                                    pulse,
                                    cache
                            ))
                            .toArray();

                    result = Numbers.lcm(inputs);
                }
                cache.put(moduleName, result);
                yield result;
            }
            case PulseModule.FlipFlop flipFlop -> {
                final int multiplier = pulse == Pulse.LOW ? 2 : 1;

                if (inputModules.size() == 1) {
                    yield multiplier * numberOfPushesRequiredForModuleToReceivePulse(
                            inputModules.getFirst().moduleName(),
                            Pulse.LOW,
                            cache
                    );
                } else if (inputModules.size() == 2) {
                    final Map<Boolean, PulseModule> moduleMap = inputModules.stream()
                            .collect(partitioningBy(
                                    inputModule -> inputModule.type() == PulseModule.Type.CONJUNCTION,
                                    reducing(null, (x, y) -> y)
                            ));
                    final PulseModule conjunction = moduleMap.get(true);
                    final PulseModule other = moduleMap.get(false);

                    notNull(conjunction, "aaaah");

                    yield multiplier * numberOfPushesRequiredForModuleToReceivePulse(other.moduleName(), Pulse.LOW, cache);
                } else {
                    throw new IllegalArgumentException();
                }
            }
        };
    }

    Map<String, Integer> distancesFor(final Set<PulseModule.Type> types) {
        final Set<String> modulesWithType = findModulesWithType(types);

        final Map<String, Set<String>> collect = modulesWithType.stream()
                .collect(toUnmodifiableMap(
                        identity(),
                        module -> findAllModulesDirectlyImpacting(module, types)
                ));

        final Set<String> previous = new HashSet<>();

        final Queue<Pair<String, Integer>> queue = new LinkedList<>();
        queue.offer(Pair.of(PulseModule.Button.BROADCASTER, 1));

        final Map<String, Integer> result = new HashMap<>();

        while (!queue.isEmpty()) {
            final Pair<String, Integer> pair = queue.poll();
            final String moduleName = pair.getKey();
            final int distance = pair.getValue();

            if (previous.contains(moduleName)) {
                continue;
            }
            previous.add(moduleName);

            if (modulesWithType.contains(moduleName)) {
                result.put(moduleName, distance);
            }

            final PulseModule nextModule = this.modules.get(moduleName);

            if (isNull(nextModule)) {
                continue;
            }

            final List<Pair<String, Integer>> destinations = nextModule.destinations().stream()
                    .map(destination -> Pair.of(destination, distance + 1))
                    .toList();

            queue.addAll(destinations);
        }

        return unmodifiableMap(result);
    }

    private Set<String> findModulesWithType(final Set<PulseModule.Type> types) {
        return this.modules.values()
                .stream()
                .filter(module -> types.contains(module.type()))
                .map(PulseModule::moduleName)
                .collect(toUnmodifiableSet());
    }

    private Set<PulseModule> findAllModulesWithDestination(final String destination) {
        return this.modules.values()
                .stream()
                .filter(module -> module.destinations().contains(destination))
                .collect(toUnmodifiableSet());
    }

    private Set<PulseModule> findAllModulesImpacting(final String destination) {
        final Queue<PulseModule> queue = new LinkedList<>(findAllModulesWithDestination(destination));

        final Set<PulseModule> result = new HashSet<>();

        while (!queue.isEmpty()) {
            final PulseModule module = queue.poll();

            if (result.contains(module)) {
                continue;
            }
            result.add(module);

            final Set<PulseModule> sources = findAllModulesWithDestination(module.moduleName());
            queue.addAll(sources);

        }

        return result;
    }

    private List<PulseModule> findModulesWithDestination(
            final String destination
    ) {
        return modules.values()
                .stream()
                .filter(module -> module.destinations().contains(destination))
                .toList();
    }

    private Set<String> findAllModulesDirectlyImpacting(
            final String destination,
            final Set<PulseModule.Type> types
    ) {
        final Queue<PulseModule> queue = new LinkedList<>(findAllModulesWithDestination(destination));

        final Set<String> result = new HashSet<>();

        while (!queue.isEmpty()) {
            final PulseModule module = queue.poll();

            if (result.contains(module.moduleName())) {
                continue;
            }

            if (types.contains(module.type())) {
                result.add(module.moduleName());
                continue;
            }

            final Set<PulseModule> sources = findAllModulesWithDestination(module.moduleName());
            queue.addAll(sources);

        }

        return result;
    }
}
