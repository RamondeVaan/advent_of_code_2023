package nl.ramondevaan.aoc2023.day10;

import nl.ramondevaan.aoc2023.util.Coordinate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class Day10 {

    private final PipeMap pipeMap;
    private final DistanceMap distanceMap;

    public Day10(final List<String> lines) {
        final var pipeMapParser = new PipeMapParser();
        final var distanceMapParser = new DistanceMapParser();
        this.pipeMap = pipeMapParser.parse(lines);
        this.distanceMap = distanceMapParser.parse(pipeMap);
    }

    public long solve1() {
        final var start = pipeMap.getStart();
        final var max = pipeMap.get(start).getDirections().stream()
                .map(dir -> dir.apply(start))
                .mapToInt(distanceMap.getDistances()::valueAt)
                .max()
                .orElseThrow();
        return (max + 1) / 2;
    }

    public long solve2() {
        final BiFunction<Direction, Pipe, Set<Direction>> insideFun = distanceMap.isClockwise() ?
                (direction, pipe) -> pipe.getRightAdjacent(direction) : (direction, pipe) -> pipe.getLeftAdjacent(direction);

        final var insideCoordinates = new HashSet<Coordinate>();
        var fromDirection = distanceMap.getStartFromDirection();
        for (final var coordinate : distanceMap.getPath()) {
            final var pipe = pipeMap.get(coordinate);
            final var direction = pipe.next(fromDirection);
            insideFun.apply(fromDirection, pipe).stream()
                    .map(dir -> dir.apply(coordinate))
                    .filter(c -> distanceMap.getDistances().valueAt(c) == -1)
                    .forEach(insideCoordinates::add);
            fromDirection = direction.opposite();
        }

        return expandAndCount(insideCoordinates);
    }

    private long expandAndCount(final Set<Coordinate> coordinates) {
        final var builder = distanceMap.getDistances().toBuilder();

        long count = coordinates.size();
        var current = coordinates;
        var next = new HashSet<Coordinate>();

        while (!current.isEmpty()) {
            current.forEach(c -> builder.set(c.row(), c.column(), -2));
            for (final Coordinate coordinate : current) {
                coordinate.allNeighbors()
                        .filter(builder::isWithinRange)
                        .filter(c -> builder.get(c.row(), c.column()) == -1)
                        .forEach(next::add);
            }

            count += next.size();
            current = next;
            next = new HashSet<>();
        }

        return count;
    }
}
