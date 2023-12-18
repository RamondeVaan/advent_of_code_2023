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
        final var dp = new long[record.numberOfDamagedGroups() + 1][record.numberOfSpringConditions() + 1];

        setFirstRow(dp, record);
        tryFillLastGroup(dp, record);

        var sum = record.numberOfSpringConditions();
        for (int i = record.numberOfDamagedGroups() - 1; i >= 0; i--) {
            final var group = record.getDamagedGroup(i);
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
                            dp[i][springIndexMinusOne] += dp[i + 1][springIndex + group];
                        }
                }
            }
        }

        return dp[0][0];
    }

    private void setFirstRow(final long[][] dp, final Record record) {
        final var numberOfDamagedGroups = record.numberOfDamagedGroups();
        dp[numberOfDamagedGroups][record.numberOfSpringConditions()] = 1;

        for (int j = dp[numberOfDamagedGroups].length - 2; j >= 0; j--) {
            if (record.getSpringCondition(j) == DAMAGED) {
                break;
            }
            dp[numberOfDamagedGroups][j] = 1;
        }
    }

    private void tryFillLastGroup(final long[][] dp, final Record record) {
        final var springConditions = record.numberOfSpringConditions();
        final var lastGroupIndex = record.numberOfDamagedGroups() - 1;
        final var from = springConditions - record.getDamagedGroup(lastGroupIndex);
        if (record.getSpringCondition(from) != OPERATIONAL) {
            for (int j = from + 1; j < springConditions; j++) {
                if (record.getSpringCondition(j) == OPERATIONAL) {
                    return;
                }
            }
            dp[lastGroupIndex][from] = dp[record.numberOfDamagedGroups()][springConditions];
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
