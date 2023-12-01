package nl.ramondevaan.aoc2023.util;

@FunctionalInterface
public interface LongBiPredicate {
    boolean test(long left, long right);
}
