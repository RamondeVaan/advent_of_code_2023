package nl.ramondevaan.aoc2023.day19;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class CompareRule implements Rule {

    protected final int compareTo;
    protected final Result result;


    @Override
    public final Result apply(final PartRating partRating) {
        if(applies(getValue(partRating), this.compareTo)) {
            return result;
        }

        return new NotApplicableResult();
    }

    @Override
    public final List<RangeResult> apply(final PartRatingRange range) {
        final var map = applies(getRange(range), compareTo);
        return List.of(new RangeResult(withRange(range, map.get(false)), new NotApplicableResult()),
                new RangeResult(withRange(range, map.get(true)), result));
    }

    protected abstract int getValue(final PartRating partRating);
    protected abstract Range getRange(final PartRatingRange partRatingRange);
    protected abstract PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range);

    protected abstract boolean applies(final int value, final int compareTo);

    protected abstract Map<Boolean, Range> applies(final Range range, final int compareTo);
}
