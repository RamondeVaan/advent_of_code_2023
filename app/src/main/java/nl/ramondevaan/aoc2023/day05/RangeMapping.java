package nl.ramondevaan.aoc2023.day05;

import lombok.*;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RangeMapping {
    private final Range source;
    private final Range destination;

    public OptionalLong getDestinationId(final long sourceId) {
        long diff = sourceId - source.getStart();

        if (diff >= 0 && diff < source.getLength()) {
            return OptionalLong.of(destination.getStart() + diff);
        }

        return OptionalLong.empty();
    }

    public Optional<RangeMapping> getOverlappingRange(final Range other) {
        final var maxStart = Math.max(source.getStart(), other.getStart());
        final var minEnd = Math.min(source.getEnd(), other.getEnd());

        if (maxStart >= minEnd) {
            return Optional.empty();
        }

        final var newSource = Range.byEnd(maxStart, minEnd);
        final var newDestination = Range.byLength(destination.getStart() + maxStart - source.getStart(), newSource.getLength());

        return Optional.of(new RangeMapping(newSource, newDestination));
    }

    public Stream<RangeMapping> withoutRange(final Range other) {
        return source.withoutRange(other)
                .map(newSource -> {
                    final var diff = newSource.getStart() - source.getStart();
                    final var newDestinationStart = destination.getStart() + diff;
                    final var newDestination = Range.byLength(newDestinationStart, newSource.getLength());

                    return new RangeMapping(newSource, newDestination);
                });
    }

    public static RangeMapping of(long sourceRangeStart, long destinationRangeStart, long length) {
        final var source = Range.byLength(sourceRangeStart, length);
        final var destination = Range.byLength(destinationRangeStart, length);
        return new RangeMapping(source, destination);
    }
}
