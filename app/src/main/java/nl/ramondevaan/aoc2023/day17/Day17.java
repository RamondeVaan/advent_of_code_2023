package nl.ramondevaan.aoc2023.day17;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.IntMapParser;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.*;

public class Day17 {
    private final IntMap heatLossMap;
    private final Coordinate source;
    private final Coordinate target;

    public Day17(final List<String> lines) {
        final var parser = new IntMapParser();
        heatLossMap = parser.parse(lines);
        source = Coordinate.of(0, 0);
        target = Coordinate.of(heatLossMap.rows() - 1, heatLossMap.columns() - 1);
    }

    public long solve1() {
        return solve(0, 3);
    }

    public long solve2() {
        return solve(4, 10);
    }

    private long solve(final int minBlocksInDirection, final int maxBlocksInDirection) {
        final var minBlocksInDirectionMinusOne = minBlocksInDirection - 1;
        final var maxBlocksInDirectionMinusOne = maxBlocksInDirection - 1;
        final var numberOfDirections = Direction.values().length;

        final var visited = new boolean[heatLossMap.rows()][heatLossMap.columns()][numberOfDirections][maxBlocksInDirection];
        for (int direction = 0; direction < numberOfDirections; direction++) {
            Arrays.fill(visited[0][0][direction], true);
        }

        final var queue = new PriorityQueue<>(Comparator.comparingLong(Step::heatLoss));
        Step current = new Step(source, Direction.EAST, -1, 0);

        do {
            for (final var potentialStep : getPotentialsSteps(current, minBlocksInDirectionMinusOne, maxBlocksInDirectionMinusOne)) {
                final var newCoordinate = potentialStep.left.apply(current.coordinate());
                if (!heatLossMap.isWithinRange(newCoordinate)) {
                    continue;
                }
                final var newHeatLoss = current.heatLoss() + heatLossMap.valueAt(newCoordinate);
                if (newCoordinate.equals(target)) {
                    return newHeatLoss;
                }
                if (visited[newCoordinate.row()][newCoordinate.column()][potentialStep.left.ordinal()][potentialStep.right]) {
                    continue;
                }
                visited[newCoordinate.row()][newCoordinate.column()][potentialStep.left.ordinal()][potentialStep.right] = true;
                queue.add(new Step(newCoordinate, potentialStep.left, potentialStep.right, newHeatLoss));
            }
        } while ((current = queue.poll()) != null);

        throw new IllegalStateException();
    }

    private List<ImmutablePair<Direction, Integer>> getPotentialsSteps(final Step current,
            final int minBlocksInDirectionMinusOne, final int maxBlocksInDirectionMinusOne) {
        final var potentialSteps = new ArrayList<ImmutablePair<Direction, Integer>>();
        if (current.blocksInDirection() >= minBlocksInDirectionMinusOne) {
            potentialSteps.add(ImmutablePair.of(current.direction().right(), 0));
            potentialSteps.add(ImmutablePair.of(current.direction().left(), 0));
        }
        if (current.blocksInDirection() < maxBlocksInDirectionMinusOne) {
            potentialSteps.add(ImmutablePair.of(current.direction(), current.blocksInDirection() + 1));
        }

        return potentialSteps;
    }
}
