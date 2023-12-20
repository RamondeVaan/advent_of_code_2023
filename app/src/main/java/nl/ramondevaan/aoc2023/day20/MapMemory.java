package nl.ramondevaan.aoc2023.day20;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapMemory implements Memory {
    private final Map<String, PulseType> memory;

    @Override
    public Memory.Operations toOperations() {
        return new Operations(new HashMap<>(memory));
    }

    public static Builder builder() {
        return new Builder();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Operations implements Memory.Operations {
        private Map<String, PulseType> memory;

        @Override
        public PulseType get() {
            throw new UnsupportedOperationException();
        }

        public PulseType get(final String key) {
            return memory.get(key);
        }

        @Override
        public Operations set(final PulseType value) {
            throw new UnsupportedOperationException();
        }

        public Operations set(final String key, final PulseType value) {
            memory.computeIfPresent(key, (s, type) -> value);
            return this;
        }

        @Override
        public PulseType toggle() {
            throw new UnsupportedOperationException();
        }

        public PulseType toggle(final String key) {
            return memory.computeIfPresent(key, (s, type) -> type.toggle());
        }

        @Override
        public Collection<PulseType> values() {
            return memory.values();
        }

        public MapMemory build() {
            final var ret = new MapMemory(memory);
            this.memory = null;
            return ret;
        }
    }

    public static class Builder {
        private Map<String, PulseType> memory;

        private Builder() {
            this.memory = new HashMap<>();
        }

        public Builder set(final String key, final PulseType value) {
            memory.put(key, value);
            return this;
        }

        public MapMemory build() {
            final var ret = new MapMemory(memory);
            this.memory = null;
            return ret;
        }
    }
}
