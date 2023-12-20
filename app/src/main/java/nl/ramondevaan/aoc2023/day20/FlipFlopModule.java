package nl.ramondevaan.aoc2023.day20;

import java.util.Optional;

public record FlipFlopModule(String name) implements Module {
    @Override
    public Optional<PulseType> apply(final Pulse pulse, final Memory.Operations operations) {
        if (pulse.pulseType() == PulseType.HIGH) {
            return Optional.empty();
        }
        return Optional.of(operations.toggle());
    }
}
