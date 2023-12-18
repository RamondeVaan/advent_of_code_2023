package nl.ramondevaan.aoc2023.day12;

import java.util.List;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.DAMAGED;
import static nl.ramondevaan.aoc2023.day12.SpringCondition.OPERATIONAL;

public class Day12 {

    private final List<Record> records;

    public Day12(final List<String> lines) {
        final var parser = new RecordParser();
        this.records = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        var sum = 0L;

        for (final Record record : records) {
            sum += solve(record);
        }

        return sum;
    }

    public long solve2() {
        var sum = 0L;

        for (final Record record : records) {
            final var unfolded = record.unfold(5);
            sum += solve(unfolded);
        }

        return sum;
    }

    private long solve(final Record record) {
        final var cache = new long[record.damagedGroups().size() + 1][record.springConditions().size() + 1];
        final var reversed = record.springConditions().reversed().toArray(SpringCondition[]::new);

        cache[0][0] = 1;

        var springIndex = 1;
        for (final var springCondition : record.springConditions().reversed()) {
            if (springCondition == DAMAGED) {
                break;
            }
            cache[0][springIndex++] = 1;
        }

        final var damagedGroupIterator = record.damagedGroups().reversed().listIterator();

        final var lastDamagedGroup = record.damagedGroups().getLast();
        outer: if (reversed[lastDamagedGroup - 1] != OPERATIONAL) {
            for (int j = 0; j < lastDamagedGroup; j++) {
                if (reversed[j] == OPERATIONAL) {
                    break outer;
                }
            }
            cache[1][lastDamagedGroup] = cache[0][0];
        }

        var sum = 0;
        for (int i = 1; i < cache.length; i++) {
            final var group = damagedGroupIterator.next();
            sum += group;
            for (int j = sum, jp1 = sum + 1; jp1 < cache[i].length; j = jp1++) {
                switch (reversed[j]) {
                    case OPERATIONAL:
                        cache[i][jp1] = cache[i][j];
                        break;
                    case UNKNOWN:
                        cache[i][jp1] = cache[i][j];
                    case DAMAGED:
                        if (canMatch(reversed, jp1 - group, jp1)) {
                            cache[i][jp1] += cache[i - 1][j - group];
                        }
                }
            }
        }

        return cache[record.damagedGroups().size()][record.springConditions().size()];
    }

    private boolean canMatch(final SpringCondition[] conditions, final int from ,final int to) {
        for (int i = from; i < to; i++) {
            if (conditions[i] == OPERATIONAL) {
                return false;
            }
        }

        return conditions[from - 1] != DAMAGED;
    }
}
