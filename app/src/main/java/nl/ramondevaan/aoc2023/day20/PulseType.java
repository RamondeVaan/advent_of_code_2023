package nl.ramondevaan.aoc2023.day20;

public enum PulseType {
    LOW {
        @Override
        public PulseType toggle() {
            return HIGH;
        }
    }, HIGH {
        @Override
        public PulseType toggle() {
            return LOW;
        }
    };

    public abstract PulseType toggle();
}
