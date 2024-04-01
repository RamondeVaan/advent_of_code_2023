package nl.ramondevaan.aoc2023.day20;

import java.util.Collection;

public final class NoopMemory implements Memory {
    @Override
    public Operations toOperations() {
        return new Operations();
    }

    public final static class Operations implements Memory.Operations {

        @Override
        public PulseType get() {
            throw new UnsupportedOperationException();
        }

        @Override
        public PulseType get(final String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Memory.Operations set(final PulseType value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Memory.Operations set(final String key, final PulseType value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PulseType toggle() {
            throw new UnsupportedOperationException();
        }

        @Override
        public PulseType toggle(final String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<PulseType> values() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Memory build() {
            return new NoopMemory();
        }
    }
}
