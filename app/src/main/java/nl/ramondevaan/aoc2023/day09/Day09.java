package nl.ramondevaan.aoc2023.day09;

import java.util.Arrays;
import java.util.List;

public class Day09 {

    private final Report report;

    public Day09(final List<String> lines) {
        final var parser = new ReportParser();
        this.report = parser.parse(lines);
    }

    public long solve1() {
        return report.getHistories().stream()
                .map(integers -> integers.stream().mapToInt(Integer::intValue).toArray())
                .mapToLong(this::next)
                .sum();
    }

    public long solve2() {
        return report.getHistories().stream()
                .map(List::reversed)
                .map(integers -> integers.stream().mapToInt(Integer::intValue).toArray())
                .mapToLong(this::next)
                .sum();
    }

    private long next(final int[] values) {
        if (values.length == 1) {
            return values[0];
        }
        if (Arrays.stream(values).allMatch(value -> value == 0)) {
            return 0;
        }
        final var valuesLengthMinusOne = values.length - 1;
        final var diff = new int[valuesLengthMinusOne];
        for (int last = 0, next = 1; next < values.length; last = next++) {
            diff[last] = values[next] - values[last];
        }
        return values[valuesLengthMinusOne] + next(diff);
    }
}
