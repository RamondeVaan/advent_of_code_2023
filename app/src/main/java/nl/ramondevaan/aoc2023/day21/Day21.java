package nl.ramondevaan.aoc2023.day21;

import nl.ramondevaan.aoc2023.util.Coordinate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.lang.Math.floorMod;

public class Day21 {

    private final Farm farm;

    public Day21(final List<String> lines) {
        final var parser = new FarmParser();
        this.farm = parser.parse(lines);
    }

    public long solve1() {
        return solveClosed(64);
    }

    public long solve2() {
        return solveInfinite(26_501_365);
    }

    private long solveClosed(final int rounds) {
        final var builder = farm.map().toBuilder();
        var count = new Count();
        var current = List.of(farm.start());

        for (int i = 1; i <= rounds; i++) {
            var next = current.stream().flatMap(Coordinate::directNeighbors)
                    .filter(builder::isWithinRange)
                    .filter(coordinate -> builder.get(coordinate) == 0)
                    .peek(coordinate -> builder.set(coordinate, 2))
                    .toList();

            count.count(next.size());
            current = next;
        }

        return count.getLast();
    }

    private long solveInfinite(final int rounds) {
        final var rows = farm.map().rows();
        final var columns = farm.map().columns();
        var visited = new HashSet<Coordinate>();
        var current = List.of(farm.start());
        var count = new Count();

        int[] frontiers = new int[columns];
        int[] difference = new int[columns];
        int[] lastDifference = new int[columns];

        int step = 0;

        for (; step < rounds && (step < 2 * columns || Arrays.stream(lastDifference).anyMatch(i -> i != 0)); step++) {
            var next = current.stream().flatMap(Coordinate::directNeighbors)
                    .filter(coordinate -> farm.map().valueAt(floorMod(coordinate.row(), rows),
                            floorMod(coordinate.column(), columns)) == 0)
                    .filter(visited::add)
                    .toList();

            final var index = step % columns;
            final var diff = next.size() - frontiers[index];
            lastDifference[index] = diff - difference[index];
            difference[index] = diff;
            frontiers[index] = next.size();

            count.count(next.size());
            current = next;
        }

        for (; step < rounds; step++) {
            final var index = step % columns;
            frontiers[index] += difference[index];

            count.count(frontiers[index]);
        }

        return count.getLast();
    }
}
