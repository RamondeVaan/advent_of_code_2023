package nl.ramondevaan.aoc2023.day12;

import java.util.Arrays;
import java.util.List;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.DAMAGED;

public class Day12 {

    private final List<Record> records;

    public Day12(final List<String> lines) {
        final var parser = new RecordParser();
        this.records = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        return records.stream().mapToLong(record -> solveCached(record, initializeCache(record))).sum();
    }

    public long solve2() {
        return records.stream()
                .map(record -> record.unfold(5))
                .mapToLong(record -> solveCached(record, initializeCache(record)))
                .sum();
    }

    private long[][] initializeCache(final Record record) {
        final var cache = new long[record.springConditions().size() + 1][record.damagedGroups().size() + 1];

        for (final long[] longs : cache) {
            Arrays.fill(longs, -1);
        }

        return cache;
    }

    private long solveCached(final Record record, final long[][] cache) {
        final var cached = cache[record.springConditions().size()][record.damagedGroups().size()];
        if (cached >= 0) {
            return cached;
        }

        final var total = solve(record, cache);
        cache[record.springConditions().size()][record.damagedGroups().size()] = total;
        return total;
    }

    private long solve(final Record record, final long[][] cache) {
        if (record.damagedGroups().isEmpty()) {
            return record.hasDamagedSpring() ? 0 : 1;
        }
        final var groupSize = record.damagedGroups().getFirst();
        if (groupSize > record.springConditions().size()) {
            return 0;
        }

        var total = record.firstSpringCondition() != DAMAGED ? solveCached(record.skipOneSpringCondition(), cache) : 0;

        final var containsOperational = record.hasOperational(groupSize);
        boolean nextIsDamaged = false;
        int nextIndex = groupSize;

        if (groupSize < record.springConditions().size()) {
            nextIsDamaged = record.springConditions().get(groupSize) == DAMAGED;
            nextIndex += 1;
        }

        if (containsOperational || nextIsDamaged) {
            return total;
        }

        return total + solveCached(record.next(nextIndex), cache);
    }
}
