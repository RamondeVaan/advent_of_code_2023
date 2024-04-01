package nl.ramondevaan.aoc2023.day20;

import java.util.Optional;

public record ConjunctionModule(String name) implements Module {

    @Override
    public Optional<PulseType> apply(final Pulse pulse, final Memory.Operations operations) {
        operations.set(pulse.from(), pulse.pulseType());

        final var value = operations.values().stream().anyMatch(pulseType -> pulseType == PulseType.LOW) ?
                PulseType.HIGH : PulseType.LOW;

        return Optional.of(value);
    }
}
