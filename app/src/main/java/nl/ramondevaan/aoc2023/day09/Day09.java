package nl.ramondevaan.aoc2023.day09;

import nl.ramondevaan.aoc2023.util.ImmutableIntArray;

import java.util.List;

public class Day09 {

    private final Report report;

    public Day09(final List<String> lines) {
        final var parser = new ReportParser();
        this.report = parser.parse(lines);
    }

    public long solve1() {
        return report.getHistories().stream()
                .mapToLong(this::next)
                .sum();
    }

    public long solve2() {
        return report.getHistories().stream()
                .mapToLong(this::previous)
                .sum();
    }

    private long next(final ImmutableIntArray values) {
        if (values.allEqual()) {
            return values.getFirst();
        }
        return values.getLast() + next(values.difference());
    }

    private long previous(final ImmutableIntArray values) {
        if (values.allEqual()) {
            return values.getFirst();
        }
        return values.getFirst() - previous(values.difference());
    }
}
