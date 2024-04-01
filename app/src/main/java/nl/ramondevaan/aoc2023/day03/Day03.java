package nl.ramondevaan.aoc2023.day03;

import java.util.List;

public class Day03 {

    private final Schematic schematic;

    public Day03(final List<String> lines) {
        final var parser = new SchematicParser();
        this.schematic = parser.parse(lines);
    }

    public long solve1() {
        return schematic.allSymbolLocations()
                .flatMapToInt(coordinate -> coordinate.allNeighbors().mapToInt(schematic::numberIndexAt))
                .filter(i -> i >= 0)
                .distinct()
                .mapToLong(i -> schematic.numbers().get(i).value())
                .sum();
    }

    public long solve2() {
        return schematic.symbolLocations().get('*').stream()
                .map(coordinate -> coordinate.allNeighbors().map(schematic::numberIndexAt)
                        .filter(i -> i >= 0).distinct().toList())
                .filter(indices -> indices.size() == 2)
                .mapToLong(this::gearRatio)
                .sum();
    }

    private long gearRatio(final List<Integer> numberIndices) {
        return numberIndices.stream()
                .mapToLong(index -> schematic.numbers().get(index).value())
                .reduce(1L, (left, right) -> left * right);
    }
}
