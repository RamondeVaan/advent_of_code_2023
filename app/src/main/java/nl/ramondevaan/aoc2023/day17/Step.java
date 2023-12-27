package nl.ramondevaan.aoc2023.day17;

import nl.ramondevaan.aoc2023.util.Direction;

public record Step(int row, int column, Direction direction, int blocksInDirection, int heatLoss) implements Comparable<Step> {
    @Override
    public int compareTo(final Step o) {
        return heatLoss - o.heatLoss;
    }
}
