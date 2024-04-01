package nl.ramondevaan.aoc2023.day19;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class CompareRule implements Rule {

    protected final Category category;
    protected final int compareTo;
    protected final Result result;


    @Override
    public final Result apply(final PartRating partRating) {
        if(applies(partRating.get(category), this.compareTo)) {
            return result;
        }

        return new NotApplicableResult();
    }

    @Override
    public final List<RangeResult> apply(final PartRatingRange range) {
        final var map = applies(range.get(category), compareTo);
        return List.of(new RangeResult(range.with(category, map.get(false)), new NotApplicableResult()),
                new RangeResult(range.with(category, map.get(true)), result));
    }

    protected abstract boolean applies(final int value, final int compareTo);

    protected abstract Map<Boolean, Range> applies(final Range range, final int compareTo);
}
