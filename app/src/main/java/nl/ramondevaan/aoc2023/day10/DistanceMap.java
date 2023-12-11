package nl.ramondevaan.aoc2023.day10;

import lombok.Getter;
import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.IntMap;

import java.util.List;

@Getter
public class DistanceMap {
    private final IntMap distances;
    private final List<Coordinate> path;
    private final Direction startFromDirection;
    private final boolean clockwise;

    public DistanceMap(final IntMap distances,
                       final List<Coordinate> path,
                       final Direction startFromDirection,
                       final boolean clockwise) {
        this.distances = distances;
        this.path = List.copyOf(path);
        this.startFromDirection = startFromDirection;
        this.clockwise = clockwise;
    }
}
