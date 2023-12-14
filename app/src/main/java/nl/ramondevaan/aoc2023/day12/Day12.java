package nl.ramondevaan.aoc2023.day12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.DAMAGED;

public class Day12 {

    private final List<Record> records;

    public Day12(final List<String> lines) {
        final var parser = new RecordParser();
        this.records = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        return records.stream().mapToLong(record -> solveCached(record, new HashMap<>())).sum();
    }

    public long solve2() {
        return records.stream()
                .map(record -> record.unfold(5))
                .mapToLong(record -> solveCached(record, new HashMap<>()))
                .sum();
    }

    private long solveCached(final Record record, final Map<Record, Long> cache) {
        final var cached = cache.get(record);
        if (cached != null) {
            return cached;
        }

        final var total = solve(record, cache);
        cache.put(record, total);
        return total;
    }

    private long solve(final Record record, final Map<Record, Long> cache) {
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
