package nl.ramondevaan.aoc2023.day03;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.IntMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public record Schematic(Map<Character, List<Coordinate>> symbolLocations, List<Number> numbers, IntMap numberIndexMap) {

    Stream<Coordinate> allSymbolLocations() {
        return symbolLocations.values().stream().flatMap(Collection::stream);
    }

    int numberIndexAt(final Coordinate coordinate) {
        if (!numberIndexMap.contains(coordinate)) {
            return -1;
        }
        return numberIndexMap.valueAt(coordinate);
    }
}
