package nl.ramondevaan.aoc2023.day17;

import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.IntMapParser;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Day17 {
    private final IntMap heatLossMap;
    private final int sourceRow;
    private final int sourceColumn;
    private final int targetRow;
    private final int targetColumn;

    public Day17(final List<String> lines) {
        final var parser = new IntMapParser();
        heatLossMap = parser.parse(lines);
        sourceRow = 0;
        sourceColumn = 0;
        targetRow = heatLossMap.rows() - 1;
        targetColumn = heatLossMap.columns() - 1;
    }

    public long solve1() {
        return solve(0, 3);
    }

    public long solve2() {
        return solve(4, 10);
    }

    private long solve(final int minBlocksInDirection, final int maxBlocksInDirection) {
        final var minBlocksInDirectionMinusOne = Math.max(0, minBlocksInDirection - 1);
        final var maxBlocksInDirectionMinusOne = maxBlocksInDirection - 1;
        final var directions = Direction.values();

        final var queue = new PriorityQueue<Step>();
        final var visited = new int[heatLossMap.rows()][heatLossMap.columns()][directions.length][maxBlocksInDirection];
        initialize(queue, visited, directions, minBlocksInDirectionMinusOne);

        Step current;

        while ((current = queue.poll()) != null) {
            if (current.row() == targetRow && current.column() == targetColumn) {
                return current.heatLoss();
            }
            final var potentialSteps = getPotentialsSteps(current, minBlocksInDirectionMinusOne, maxBlocksInDirectionMinusOne);
            outer:
            for (final var potentialStep : potentialSteps) {
                var newRow = current.row();
                var newColumn = current.column();
                var newHeatLoss = current.heatLoss();

                for (int i = 0; i <= potentialStep.steps(); i++) {
                    newRow += potentialStep.rowOffset();
                    newColumn += potentialStep.columnOffset();

                    if (!heatLossMap.isWithinRange(newRow, newColumn)) {
                        continue outer;
                    }

                    newHeatLoss += heatLossMap.valueAt(newRow, newColumn);
                }
                if (visited[newRow][newColumn][potentialStep.direction()][potentialStep.blocks()] != 0 &&
                        visited[newRow][newColumn][potentialStep.direction()][potentialStep.blocks()] <= newHeatLoss) {
                    continue;
                }
                visited[newRow][newColumn][potentialStep.direction()][potentialStep.blocks()] = newHeatLoss;
                if (potentialStep.blocks() >= minBlocksInDirectionMinusOne) {
                    for (int i = potentialStep.blocks() + 1; i < maxBlocksInDirection; i++) {
                        visited[newRow][newColumn][potentialStep.direction()][i] = newHeatLoss;
                    }
                }
                queue.add(new Step(newRow, newColumn, directions[potentialStep.direction()], potentialStep.blocks(), newHeatLoss));
            }
        }

        throw new IllegalStateException();
    }

    private void initialize(final PriorityQueue<Step> queue, final int[][][][] visited, final Direction[] directions,
            final int minBlocksInDirectionMinusOne) {
        for (final var direction : directions) {
            Arrays.fill(visited[0][0][direction.ordinal()], 0);
            int newRow = sourceRow, newColumn = sourceColumn, newHeatLoss = 0;
            for (int i = 0; i <= minBlocksInDirectionMinusOne; i++) {
                newRow += direction.getRowDiff();
                newColumn += direction.getColumnDiff();

                if (!heatLossMap.isWithinRange(newRow, newColumn)) {
                    break;
                }

                newHeatLoss += heatLossMap.valueAt(newRow, newColumn);
            }
            queue.add(new Step(newRow, newColumn, direction, minBlocksInDirectionMinusOne, newHeatLoss));
        }
    }

    private PotentialStep[] getPotentialsSteps(final Step current,
            final int minBlocksInDirectionMinusOne, final int maxBlocksInDirectionMinusOne) {
        final Direction right = current.direction().right(), left = current.direction().left();
        final var rightStep = new PotentialStep(right.getRowDiff(), right.getColumnDiff(), right.ordinal(), minBlocksInDirectionMinusOne, minBlocksInDirectionMinusOne);
        final var leftStep = new PotentialStep(left.getRowDiff(), left.getColumnDiff(), left.ordinal(), minBlocksInDirectionMinusOne, minBlocksInDirectionMinusOne);
        if (current.blocksInDirection() < maxBlocksInDirectionMinusOne) {
            final var dir = current.direction();
            final var forward = new PotentialStep(dir.getRowDiff(), dir.getColumnDiff(), dir.ordinal(), 0, current.blocksInDirection() + 1);
            return new PotentialStep[]{rightStep, leftStep, forward};
        }

        return new PotentialStep[]{rightStep, leftStep};
    }
}
