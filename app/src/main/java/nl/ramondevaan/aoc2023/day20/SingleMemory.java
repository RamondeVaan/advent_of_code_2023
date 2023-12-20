package nl.ramondevaan.aoc2023.day20;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public final class SingleMemory implements Memory {

    private final PulseType pulseType;

    @Override
    public Operations toOperations() {
        return new Operations(pulseType);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Operations implements Memory.Operations {

        PulseType pulseType;

        @Override
        public PulseType get() {
            return pulseType;
        }

        public PulseType get(final String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Operations set(final PulseType value) {
            pulseType = value;
            return this;
        }

        public Operations set(final String key, final PulseType value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public PulseType toggle() {
            return pulseType = pulseType.toggle();
        }

        public PulseType toggle(final String key) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<PulseType> values() {
            return List.of(pulseType);
        }

        public SingleMemory build() {
            return new SingleMemory(pulseType);
        }
    }
}
