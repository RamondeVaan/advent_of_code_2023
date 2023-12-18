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
        final var dp = new long[record.damagedGroups().size() + 1][record.numberOfSpringConditions() + 1];

        setFirstRow(dp, record);

        final var damagedGroupIterator = record.damagedGroups().reversed().listIterator();

        tryFillLastGroup(dp, record);

        var sum = record.numberOfSpringConditions();
        for (int i = 1; i < dp.length; i++) {
            final var group = damagedGroupIterator.next();
            sum -= group;
            for (int springIndex = sum, springIndexMinusOne = sum - 1; springIndex > 0; springIndex = springIndexMinusOne--) {
                switch (record.getSpringCondition(springIndexMinusOne)) {
                    case OPERATIONAL:
                        dp[i][springIndexMinusOne] = dp[i][springIndex];
                        break;
                    case UNKNOWN:
                        dp[i][springIndexMinusOne] = dp[i][springIndex];
                    case DAMAGED:
                        if (canMatch(record, springIndex, springIndexMinusOne + group)) {
                            dp[i][springIndexMinusOne] += dp[i - 1][springIndex + group];
                        }
                }
            }
        }

        return dp[record.damagedGroups().size()][0];
    }

    private void setFirstRow(final long[][] dp, final Record record) {
        dp[0][record.numberOfSpringConditions()] = 1;

        for (int j = dp[0].length - 2; j >= 0; j--) {
            if (record.getSpringCondition(j) == DAMAGED) {
                break;
            }
            dp[0][j] = 1;
        }
    }

    private void tryFillLastGroup(final long[][] dp, final Record record) {
        final var springConditions = record.numberOfSpringConditions();
        final var lastDamagedGroup = record.damagedGroups().getLast();
        final var from = springConditions - lastDamagedGroup;
        if (record.getSpringCondition(from) != OPERATIONAL) {
            for (int j = from + 1; j < springConditions; j++) {
                if (record.getSpringCondition(j) == OPERATIONAL) {
                    return;
                }
            }
            dp[1][from] = dp[0][springConditions];
        }
    }

    private boolean canMatch(final Record record, final int from ,final int to) {
        for (int i = from; i < to; i++) {
            if (record.getSpringCondition(i) == OPERATIONAL) {
                return false;
            }
        }

        return record.getSpringCondition(to) != DAMAGED;
    }
}
