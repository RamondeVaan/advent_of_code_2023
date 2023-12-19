package nl.ramondevaan.aoc2023.day19;

import java.util.List;

public interface Rule {

    Result apply(final PartRating partRating);

    List<RangeResult> apply(final PartRatingRange range);
}
