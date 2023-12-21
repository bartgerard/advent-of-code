package be.gerard.aoc2023.day20;

import be.gerard.aoc.util.input.Tokens;
import be.gerard.aoc.util.signal.Pulse;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.Validate.notEmpty;

public sealed interface PulseModule permits PulseModule.Broadcast, PulseModule.FlipFlop, PulseModule.Conjunction {
    Pattern PATTERN = Pattern.compile("([&%]?)(\\w+) -> (.+)");

    static PulseModule parse(final String value) {
        final List<String> tokens = Tokens.tokenize(value, PATTERN);
        final String moduleName = tokens.get(1);
        final List<String> destinations = Tokens.split(tokens.getLast(), ", ");

        return switch (tokens.getFirst()) {
            case "" -> new Broadcast(moduleName, destinations);
            case "%" -> new FlipFlop(moduleName, destinations);
            case "&" -> new Conjunction(moduleName, destinations);
            default -> throw new IllegalArgumentException("invalid module [%s]".formatted(tokens.getFirst()));
        };
    }

    String moduleName();

    List<String> destinations();

    Type type();

    List<Signal> receive(Signal signal);

    default void connect(PulseModule source) {
        // no-op
    }

    boolean isInitialState();

    enum Type {
        BROADCASTER,
        FLIP_FLOP,
        CONJUNCTION
    }

    final class Button {
        public static final String BUTTON = "button";
        public static final String BROADCASTER = "broadcaster";
        private static final Signal SIGNAL = new Signal(BUTTON, BROADCASTER, Pulse.LOW);

        private Button() {
            // no-op
        }

        public static Signal press() {
            return SIGNAL;
        }
    }

    record Broadcast(
            String moduleName,
            List<String> destinations
    ) implements PulseModule {
        public Broadcast {
            notEmpty(destinations);
        }

        @Override
        public Type type() {
            return Type.BROADCASTER;
        }

        @Override
        public List<Signal> receive(final Signal signal) {
            return destinations.stream()
                    .map(destination -> new Signal(moduleName, destination, signal.pulse()))
                    .toList();
        }

        @Override
        public boolean isInitialState() {
            return true;
        }
    }

    final class FlipFlop implements PulseModule {
        private final String moduleName;
        private final List<String> destinations;
        private boolean on;

        FlipFlop(
                final String moduleName,
                final List<String> destinations
        ) {
            notEmpty(destinations);

            this.moduleName = moduleName;
            this.destinations = destinations;
        }

        @Override
        public String moduleName() {
            return moduleName;
        }

        @Override
        public List<String> destinations() {
            return destinations;
        }

        @Override
        public Type type() {
            return Type.FLIP_FLOP;
        }

        public boolean isOn() {
            return on;
        }

        public void flip() {
            this.on = !this.on;
        }

        @Override
        public List<Signal> receive(final Signal signal) {
            if (signal.pulse() == Pulse.HIGH) {
                return Collections.emptyList();
            }

            flip();
            final Pulse newPulse = isOn() ? Pulse.HIGH : Pulse.LOW;

            return destinations.stream()
                    .map(destination -> new Signal(moduleName, destination, newPulse))
                    .toList();
        }

        @Override
        public boolean isInitialState() {
            return !isOn();
        }
    }

    final class Conjunction implements PulseModule {
        private final String moduleName;
        private final List<String> destinations;
        private final Map<String, Pulse> inputs = new HashMap<>();

        public Conjunction(
                final String moduleName,
                final List<String> destinations
        ) {
            notEmpty(destinations);

            this.moduleName = moduleName;
            this.destinations = destinations;
        }

        @Override
        public String moduleName() {
            return moduleName;
        }

        @Override
        public List<String> destinations() {
            return destinations;
        }

        @Override
        public Type type() {
            return Type.CONJUNCTION;
        }

        @Override
        public List<Signal> receive(final Signal signal) {
            remember(signal.source(), signal.pulse());

            final Pulse newPulse = allInputsAre(Pulse.HIGH)
                    ? Pulse.LOW
                    : Pulse.HIGH;

            return destinations.stream()
                    .map(destination -> new Signal(moduleName, destination, newPulse))
                    .toList();
        }

        private void remember(
                final String moduleName,
                final Pulse pulse
        ) {
            this.inputs.put(moduleName, pulse);
        }

        private boolean allInputsAre(final Pulse pulse) {
            return this.inputs.values().stream().allMatch(pulse::equals);
        }

        @Override
        public void connect(final PulseModule source) {
            remember(source.moduleName(), Pulse.LOW);
        }

        @Override
        public boolean isInitialState() {
            return allInputsAre(Pulse.LOW);
        }
    }
}
