package nl.ramondevaan.aoc2023.day19;

import java.util.EnumMap;

import static nl.ramondevaan.aoc2023.day19.Category.*;

public class PartRatingRange {

    private final EnumMap<Category, Range> valuesByCategory;

    public PartRatingRange(final int min, final int max) {
        valuesByCategory = new EnumMap<>(Category.class);
        valuesByCategory.put(X, new Range(min, max + 1));
        valuesByCategory.put(M, new Range(min, max + 1));
        valuesByCategory.put(A, new Range(min, max + 1));
        valuesByCategory.put(S, new Range(min, max + 1));
    }

    private PartRatingRange(final EnumMap<Category, Range> valuesByCategory) {
        this.valuesByCategory = valuesByCategory;
    }

    public PartRatingRange with(final Category category, final Range range) {
        final var newValuesByCategory = new EnumMap<>(valuesByCategory);
        newValuesByCategory.put(category, range);
        return new PartRatingRange(newValuesByCategory);
    }

    public Range get(final Category category) {
        return valuesByCategory.get(category);
    }

    public boolean isEmpty() {
        return valuesByCategory.values().stream().anyMatch(Range::isEmpty);
    }

    public long size() {
        return valuesByCategory.values().stream()
                .mapToLong(Range::size)
                .reduce(1L, (left, right) -> left * right);
    }
}
