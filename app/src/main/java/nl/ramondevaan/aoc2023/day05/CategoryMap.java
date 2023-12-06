package nl.ramondevaan.aoc2023.day05;

import java.util.ArrayList;
import java.util.LinkedList;
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
        final var toMap = new LinkedList<Range>();
        toMap.add(range);
        final var mapped = new ArrayList<Range>();

        for (final var mapping : rangeMappings) {
            final var iterator = toMap.listIterator();
            while (iterator.hasNext()) {
                final var current = iterator.next();
                final var overlapOpt = mapping.getSource().getOverlap(current);
                if (overlapOpt.isPresent()) {
                    final var overlap = overlapOpt.get();
                    iterator.remove();
                    mapped.add(overlap.offset(mapping.getOffset()));
                    current.withoutRange(overlap).forEach(iterator::add);
                    break;
                }
            }
        }

        mapped.addAll(toMap);

        return mapped.stream();
    }
}
