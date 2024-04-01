package nl.ramondevaan.aoc2023.day20;

import java.util.Collection;

public sealed interface Memory permits MapMemory, NoopMemory, SingleMemory {
    Operations toOperations();

    interface Operations {
        PulseType get();

        PulseType get(final String key);

        Operations set(final PulseType value);

        Operations set(final String key, final PulseType value);

        PulseType toggle();

        PulseType toggle(final String key);

        Collection<PulseType> values();

        Memory build();
    }
}
