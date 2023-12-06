package nl.ramondevaan.aoc2023.day05;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public record CategoryMap(String source, String destination, List<RangeMapping> rangeMappings) {

    public long getDestinationId(final long sourceId) {
        return rangeMappings.stream()
                .flatMapToLong(rangeMapping -> rangeMapping.getDestinationId(sourceId).stream())
                .findFirst()
                .orElse(sourceId);
    }

    public Stream<Range> getDestinationIds(final Range range) {
        var toMap = List.of(range);
        var nextToMap = new ArrayList<Range>();
        final var mapped = new ArrayList<Range>();

        for (final var mapping : rangeMappings) {
            for (Range current : toMap) {
                final var overlappingRangeOpt = mapping.getOverlappingRange(current);
                if (overlappingRangeOpt.isPresent()) {
                    final var overlappingRange = overlappingRangeOpt.get();
                    final var remaining = current.withoutRange(overlappingRange.getSource()).toList();
                    nextToMap.addAll(remaining);
                    mapped.add(overlappingRange.getDestination());
                } else {
                    nextToMap.add(current);
                }
            }
            toMap = nextToMap;
            nextToMap = new ArrayList<>();
        }

        mapped.addAll(toMap);

        return mapped.stream();
    }
}
