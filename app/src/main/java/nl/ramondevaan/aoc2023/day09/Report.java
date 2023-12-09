package nl.ramondevaan.aoc2023.day09;

import lombok.Getter;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Getter
public class Report {
    private final List<List<Integer>> histories;

    public Report(final Stream<IntStream> values) {
        this.histories = values.map(stream -> stream.boxed().toList()).toList();
    }
}
