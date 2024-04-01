package nl.ramondevaan.aoc2023.day23;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Direction;

public record PosDir(Coordinate splitPoint, int row, int column, int length, Direction direction) {
}
