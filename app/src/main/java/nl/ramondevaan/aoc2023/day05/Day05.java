package nl.ramondevaan.aoc2023.day05;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day05 {

    private final Almanac almanac;

    public Day05(final List<String> lines) {
        final var parser = new AlmanacParser();
        this.almanac = parser.parse(lines);
    }

    public long solve1() {
        return almanac.seeds().stream()
                .mapToLong(this::map)
                .min()
                .orElseThrow();
    }

    private long map(final long seedId) {
        long current = seedId;

        for (final var categoryMap : almanac.categoryMaps()) {
            current = categoryMap.getDestinationId(current);
        }

        return current;
    }

    public long solve2() {
        var current = seedRanges().toList();

        for (final var categoryMap : almanac.categoryMaps()) {
            current = current.stream().flatMap(categoryMap::getDestinationIds).toList();
        }

        return current.stream().mapToLong(Range::getStart).min().orElseThrow();
    }

    private Stream<Range> seedRanges() {
        final int length = almanac.seeds().size();
        return IntStream.iterate(0, i -> i < length, i -> i + 2)
                .mapToObj(i -> Range.byLength(almanac.seeds().get(i), almanac.seeds().get(i + 1)));
    }
}
