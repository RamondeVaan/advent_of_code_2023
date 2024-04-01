package nl.ramondevaan.aoc2023.day20;

import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day20 {

    private final Configuration configuration;

    public Day20(final List<String> lines) {
        final var parser = new ConfigurationParser();
        this.configuration = parser.parse(lines);
    }

    public long solve1() {
        var lowPulses = 0L;
        var highPulses = 0L;
        var memory = toOperations(initializeMemory());

        for (int i = 0; i < 1000; i++) {
            final var iteration = pressButton(memory);
            lowPulses += iteration.lowPulses();
            highPulses += iteration.highPulses();
            memory = iteration.memory();
        }

        return lowPulses * highPulses;
    }

    public long solve2() {
        return configuration.getConnections().get(configuration.getBroadcaster()).stream()
                .mapToLong(this::toInteger)
                .reduce(ArithmeticUtils::lcm)
                .orElseThrow();
    }

    private int toInteger(final Module module) {
        final var list = new LinkedList<Boolean>();
        final var visited = new HashSet<Module>();

        Module knownConj = null;

        var current = module;

        while (true) {
            final var children = configuration.getConnections().get(current);
            if (visited.contains(current) || children.isEmpty() || children.size() > 2) {
                throw new UnsupportedOperationException("Unexpected setup");
            }
            visited.add(current);
            if (children.size() == 2) {
                final var conj = children.stream().filter(i -> i instanceof ConjunctionModule).findFirst().orElseThrow();
                list.addFirst(true);
                knownConj = compareConj(knownConj, conj);
                current = children.stream().filter(i -> i instanceof FlipFlopModule).findFirst().orElseThrow();
            } else {
                final var conj = children.stream().filter(i -> i instanceof ConjunctionModule).findFirst().orElse(null);
                list.addFirst(conj != null);
                if (conj != null) {
                    compareConj(knownConj, conj);
                    break;
                }
                current = children.stream().filter(i -> i instanceof FlipFlopModule).findFirst().orElseThrow();
            }
        }

        return toInteger(list);
    }

    private Module compareConj(Module known, Module other) {
        if (known == null) {
            return other;
        }
        if (other != known) {
            throw new UnsupportedOperationException("Unexpected setup");
        }
        return known;
    }

    private static int toInteger(final List<Boolean> booleans) {
        int i = 0;
        for (final boolean bool : booleans) {
            i <<= 1;
            if (bool) {
                i |= 1;
            }
        }

        return i;
    }

    private Iteration pressButton(final Map<Module, Memory.Operations> operations) {
        final var queue = new ArrayDeque<PulseRequest>();
        queue.add(new PulseRequest("button", PulseType.LOW, configuration.getBroadcaster()));

        var lowPulses = 0;
        var highPulses = 0;
        PulseRequest current;

        while ((current = queue.poll()) != null) {
            switch (current.type()) {
                case LOW -> lowPulses++;
                case HIGH -> highPulses++;
            }
            final var dest = current.destination();
            final var pulse = new Pulse(current.from(), current.type());
            final var op = operations.get(dest);

            final var next = current.destination().apply(pulse, op).stream()
                    .flatMap(type -> configuration.getConnections().get(dest).stream()
                            .map(d -> new PulseRequest(dest.name(), type, d)))
                    .toList();
            queue.addAll(next);
        }

        return new Iteration(operations, lowPulses, highPulses);
    }

    private Map<Module, Memory.Operations> toOperations(final Map<Module, Memory> memory) {
        return memory.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> entry.getValue().toOperations()));
    }

    private Map<Module, Memory> toMemory(final Map<Module, Memory.Operations> operations) {
        return operations.entrySet().stream()
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> entry.getValue().build()));
    }

    private Map<Module, Memory> initializeMemory() {
        return configuration.getModules().stream()
                .collect(Collectors.toUnmodifiableMap(Function.identity(), this::initializeMemory));
    }

    private Memory initializeMemory(final Module module) {
        return switch (module) {
            case NoopModule ignored -> new NoopMemory();
            case BroadcastModule ignored -> new NoopMemory();
            case FlipFlopModule ignored -> new SingleMemory(PulseType.LOW);
            case ConjunctionModule cm -> {
                final var builder = MapMemory.builder();
                configuration.getConnections().entrySet().stream()
                        .filter(entry -> entry.getValue().contains(cm))
                        .map(entry -> entry.getKey().name())
                        .forEach(name -> builder.set(name, PulseType.LOW));
                yield  builder.build();
            }
            default -> throw new IllegalStateException("Unexpected value: " + module);
        };
    }
}
