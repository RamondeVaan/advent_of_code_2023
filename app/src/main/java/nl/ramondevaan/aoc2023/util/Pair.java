package nl.ramondevaan.aoc2023.util;

import java.util.stream.IntStream;

public record Pair(int left, int right) {
    public IntStream stream() {
        return IntStream.of(left, right);
    }
}
