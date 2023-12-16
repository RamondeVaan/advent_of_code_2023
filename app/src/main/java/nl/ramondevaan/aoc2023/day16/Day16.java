package nl.ramondevaan.aoc2023.day16;

import lombok.SneakyThrows;
import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.IntMap;

import java.util.ArrayDeque;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day16 {

    private final Mirrors mirrors;

    public Day16(final List<String> lines) {
        final var parser = new MirrorsParser();
        this.mirrors = parser.parse(lines);
    }

    public long solve1() {
        return solve(new Beam(Coordinate.of(0, 0), Direction.EAST));
    }

    @SneakyThrows
    public long solve2() {
        try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            final var byRow = IntStream.range(0, mirrors.rows).boxed().flatMap(row -> Stream.of(
                    new Beam(Coordinate.of(row, 0), Direction.EAST),
                    new Beam(Coordinate.of(row, mirrors.columns - 1), Direction.WEST)));
            final var byColumn = IntStream.range(0, mirrors.columns).boxed().flatMap(column -> Stream.of(
                    new Beam(Coordinate.of(0, column), Direction.SOUTH),
                    new Beam(Coordinate.of(mirrors.rows - 1, column), Direction.NORTH)));
            return Stream.concat(byRow, byColumn).map(beam -> CompletableFuture.supplyAsync(() -> solve(beam), executor))
                    .reduce((f1, f2) -> f1.thenCombine(f2, Math::max))
                    .orElseThrow()
                    .get();
        }
    }

    private long solve(final Beam initial) {

        final var builder = IntMap.builder(mirrors.rows, mirrors.columns);

        final var beams = new ArrayDeque<Beam>();
        beams.add(initial);
        builder.flag(initial.position().row(), initial.position().column(), initial.direction().getFlag());
        long energizedFields = 1L;

        Beam beam;
        while ((beam = beams.pollFirst()) != null) {
            final var mirror = mirrors.get(beam.position().row(), beam.position().column());
            for (final var direction : mirror.next(beam.direction())) {
                final var newPosition = direction.apply(beam.position());
                if (!builder.isWithinRange(newPosition)) {
                    continue;
                }
                final var value = builder.get(newPosition.row(), newPosition.column());
                if (value == 0) {
                    energizedFields++;
                }
                if ((value & direction.getFlag()) != direction.getFlag()) {
                    beams.add(new Beam(newPosition, direction));
                    builder.flag(newPosition.row(), newPosition.column(), direction.getFlag());
                }
            }
        }

        return energizedFields;
    }
}
