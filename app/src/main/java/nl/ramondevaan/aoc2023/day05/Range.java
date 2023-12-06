package nl.ramondevaan.aoc2023.day05;

import lombok.*;

import java.util.stream.Stream;

@Getter
@ToString
@EqualsAndHashCode(of = {"start", "length"})
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Range {
    private final long start;
    private final long end;
    private final long length;

    private Range(long start, long end) {
        this.start = start;
        this.end = end;
        this.length = end - start;
    }

    public Stream<Range> withoutRange(final Range other) {
        if (start >= other.getStart()) {
            if (end <= other.end) {
                return Stream.empty();
            }
            if (start >= other.end) {
                return Stream.of(this);
            }
            return Stream.of(new Range(other.end, end));
        }
        if (end <= other.getStart()) {
            return Stream.empty();
        }
        if (end <= other.end) {
            return Stream.of(new Range(start, other.start));
        }

        return Stream.of(new Range(start, other.start), new Range(other.end, end));
    }

    public static Range byEnd(final long start, final long end) {
        if (end <= start) {
            throw new IllegalArgumentException();
        }
        return new Range(start, end, end - start);
    }

    public static Range byLength(final long start, final long length) {
        if (length <= 0) {
            throw new IllegalArgumentException();
        }
        return new Range(start, start + length, length);
    }
}
