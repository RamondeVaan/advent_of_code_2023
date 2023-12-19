package nl.ramondevaan.aoc2023.day19;

public record Range(int fromInclusive, int toExclusive) {

    public Split before(int maxExclusive) {
        if (maxExclusive <= fromInclusive) {
            return new Split(new Range(maxExclusive, maxExclusive), this);
        }
        if (maxExclusive >= toExclusive) {
            return new Split(this, new Range(maxExclusive, maxExclusive));
        }
        return new Split(new Range(fromInclusive, maxExclusive), new Range(maxExclusive, toExclusive));
    }

    public Split after(int minExclusive) {
        final int minExclusivePlusOne = minExclusive + 1;
        if (minExclusive < fromInclusive) {
            return new Split(new Range(minExclusive, minExclusive), this);
        }
        if (minExclusivePlusOne >= toExclusive) {
            return new Split(this, new Range(minExclusive, minExclusive));
        }
        return new Split(new Range(fromInclusive, minExclusivePlusOne), new Range(minExclusivePlusOne, toExclusive));
    }

    public boolean isEmpty() {
        return fromInclusive == toExclusive;
    }

    public int size() {
        return toExclusive - fromInclusive;
    }
}
