package nl.ramondevaan.aoc2023.day17;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Direction;

public record Step(Coordinate coordinate, Direction direction, int blocksInDirection, long heatLoss) {
}
