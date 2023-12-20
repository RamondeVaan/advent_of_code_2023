package nl.ramondevaan.aoc2023.day20;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
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
        var memory = initializeMemory();

        for (int i = 0; i < 1000; i++) {
            final var iteration = pressButton(memory, null);
            lowPulses += iteration.lowPulses();
            highPulses += iteration.highPulses();
            memory = iteration.memory();
        }

        return lowPulses * highPulses;
    }

    public long solve2() {
        final var modulesConnectingToRx = configuration.getConnections().entrySet().stream()
                .filter(entry -> entry.getValue().stream().map(Module::name).anyMatch("rx"::equals))
                .map(Map.Entry::getKey).toList();

        if (modulesConnectingToRx.size() != 1 || !(modulesConnectingToRx.getFirst() instanceof ConjunctionModule)) {
            throw new UnsupportedOperationException("Unexpected input");
        }

        final var next = modulesConnectingToRx.getFirst();
        final var modulesConnectingToNext = configuration.getConnections().entrySet().stream()
                .filter(entry -> entry.getValue().contains(next))
                .map(Map.Entry::getKey).toList();

        if (modulesConnectingToNext.stream().anyMatch(module -> !(module instanceof ConjunctionModule))) {
            throw new UnsupportedOperationException("Unexpected input");
        }

        return modulesConnectingToNext.stream().mapToLong(this::lowPulseCycle).reduce(1L, (left, right) -> left * right);
    }

    private long lowPulseCycle(final Module checkLowPulse) {
        long buttonPresses = 0L;
        var pulseDelivered = false;
        var memory = initializeMemory();

        while (!pulseDelivered) {
            final var iteration = pressButton(memory, checkLowPulse);
            buttonPresses++;
            memory = iteration.memory();
            pulseDelivered = iteration.pulseDelivered();
        }

        return buttonPresses;
    }

    private Iteration pressButton(final Map<Module, Memory> memory, final Module checkLowPulse) {
        final var queue = new ArrayDeque<PulseRequest>();
        queue.add(new PulseRequest("button", PulseType.LOW, configuration.getBroadcaster()));

        var pulseDelivered = false;
        var lowPulses = 0;
        var highPulses = 0;
        var operations = toOperations(memory);
        PulseRequest current;

        while ((current = queue.poll()) != null) {
            switch (current.type()) {
                case LOW -> lowPulses++;
                case HIGH -> highPulses++;
            }
            final var dest = current.destination();
            if (dest == checkLowPulse && current.type() == PulseType.LOW) {
                pulseDelivered = true;
            }
            final var pulse = new Pulse(current.from(), current.type());
            final var op = operations.get(dest);

            final var next = current.destination().apply(pulse, op).stream()
                    .flatMap(type -> configuration.getConnections().get(dest).stream()
                            .map(d -> new PulseRequest(dest.name(), type, d)))
                    .toList();
            queue.addAll(next);
        }

        return new Iteration(toMemory(operations), lowPulses, highPulses, pulseDelivered);
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
