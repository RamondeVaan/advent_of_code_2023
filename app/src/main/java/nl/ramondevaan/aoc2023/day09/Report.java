package nl.ramondevaan.aoc2023.day09;

import lombok.Getter;
import nl.ramondevaan.aoc2023.util.ImmutableIntArray;

import java.util.List;
import java.util.stream.Stream;

@Getter
public class Report {
    private final List<ImmutableIntArray> histories;

    public Report(final Stream<ImmutableIntArray> values) {
        this.histories = values.toList();
    }
}
