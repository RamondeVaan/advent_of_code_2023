package nl.ramondevaan.aoc2023.day19;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ResultRule implements Rule {

    private final Result result;

    @Override
    public Result apply(final PartRating partRating) {
        return result;
    }

    @Override
    public List<RangeResult> apply(final PartRatingRange range) {
        return List.of(new RangeResult(range, result));
    }
}
