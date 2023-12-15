package nl.ramondevaan.aoc2023.day15;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.SequencedMap;
import java.util.stream.Stream;

public class Day15 {

    private final List<String> strings;
    private final List<Step> steps;

    public Day15(final List<String> lines) {
        this.strings = Arrays.asList(String.join("", lines).split(","));
        final var parser = new StepsParser();
        this.steps = strings.stream().map(parser::parse).toList();
    }

    public long solve1() {
        return strings.stream()
                .mapToLong(this::hash)
                .sum();
    }

    public long solve2() {
        final var boxes = Stream.generate(() -> new LinkedHashMap<String, Long>()).limit(256).toList();

        for (final var step : steps) {
            final var box = boxes.get(hash(step.label()));
            switch (step) {
                case AddStep(String label, long focalLength) -> box.put(label, focalLength);
                case RemoveStep(String label) -> box.remove(label);
                default -> throw new UnsupportedOperationException();
            }
        }

        return focusingPower(boxes);
    }

    private long focusingPower(final List<? extends SequencedMap<String, Long>> boxes) {
        var focusingPower = 0L;
        var boxId = 1;

        for (final var box : boxes) {
            var lensId = 1;
            for (final var focalLength : box.values()) {
                focusingPower += boxId * lensId++ * focalLength;
            }
            boxId++;
        }

        return focusingPower;
    }

    private int hash(final String step) {
        var hash = 0;

        for (final char c : step.toCharArray()) {
            hash += c;
            hash *= 17;
            hash %= 256;
        }

        return hash;
    }
}
