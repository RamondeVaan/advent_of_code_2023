package nl.ramondevaan.aoc2023.day19;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PartRatingRange {
    private final Range x;
    private final Range m;
    private final Range a;
    private final Range s;

    public PartRatingRange(final int min, final int max) {
        x = new Range(min, max + 1);
        m = new Range(min, max + 1);
        a = new Range(min, max + 1);
        s = new Range(min, max + 1);
    }

    public PartRatingRange withX(final Range range) {
        return new PartRatingRange(range, m, a, s);
    }

    public PartRatingRange withM(final Range range) {
        return new PartRatingRange(x, range, a, s);
    }

    public PartRatingRange withA(final Range range) {
        return new PartRatingRange(x, m, range, s);
    }

    public PartRatingRange withS(final Range range) {
        return new PartRatingRange(x, m, a, range);
    }

    public boolean isEmpty() {
        return x.isEmpty() || m.isEmpty() || a.isEmpty() || s.isEmpty();
    }

    public long size() {
        return (long) x.size() * m.size() * a.size() * s.size();
    }
}
