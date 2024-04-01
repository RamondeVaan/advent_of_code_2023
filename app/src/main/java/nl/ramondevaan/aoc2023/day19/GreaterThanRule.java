package nl.ramondevaan.aoc2023.day19;

import java.util.Map;

public class GreaterThanRule extends CompareRule {

    public GreaterThanRule(final Category category, final int compareTo, final Result result) {
        super(category, compareTo, result);
    }

    @Override
    protected final boolean applies(final int value, final int compareTo) {
        return value > compareTo;
    }

    @Override
    protected final Map<Boolean, Range> applies(final Range range, final int compareTo) {
        final var split = range.after(compareTo);
        return Map.of(true, split.after(), false, split.before());
    }
}
