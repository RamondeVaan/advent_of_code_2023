package nl.ramondevaan.aoc2023.day20;

import java.util.Optional;

public record BroadcastModule(String name) implements Module {

    @Override
    public Optional<PulseType> apply(final Pulse pulse, final Memory.Operations operations) {
        return Optional.of(pulse.pulseType());
    }
}
