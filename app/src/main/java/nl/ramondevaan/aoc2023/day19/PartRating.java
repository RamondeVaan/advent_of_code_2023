package nl.ramondevaan.aoc2023.day19;

import java.util.EnumMap;

import static nl.ramondevaan.aoc2023.day19.Category.*;

public class PartRating {

    private final EnumMap<Category, Integer> valuesByCategory;

    public PartRating(final int x, final int m, final int a, final int s) {
        valuesByCategory = new EnumMap<>(Category.class);
        valuesByCategory.put(X, x);
        valuesByCategory.put(M, m);
        valuesByCategory.put(A, a);
        valuesByCategory.put(S, s);
    }

    public int sum() {
        return valuesByCategory.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int get(final Category c) {
        return valuesByCategory.get(c);
    }
}
