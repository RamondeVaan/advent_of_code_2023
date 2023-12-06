package nl.ramondevaan.aoc2023.day05;

import lombok.*;

import java.util.OptionalLong;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RangeMapping {
    private final Range source;
    private final Range destination;
    private final long offset;

    public OptionalLong getDestinationId(final long sourceId) {
        if (source.contains(sourceId)) {
            return OptionalLong.of(sourceId + offset);
        }

        return OptionalLong.empty();
    }

    public static RangeMapping of(long sourceRangeStart, long destinationRangeStart, long length) {
        final var source = Range.byLength(sourceRangeStart, length);
        final var destination = Range.byLength(destinationRangeStart, length);
        return new RangeMapping(source, destination, destinationRangeStart - sourceRangeStart);
    }
}
