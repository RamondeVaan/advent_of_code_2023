package nl.ramondevaan.aoc2023.day13;

import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.List;

public class PatternsParser implements Parser<List<String>, IntMap> {
    @Override
    public IntMap parse(final List<String> toParse) {
        final var rows = toParse.size();
        final var columns = toParse.stream().mapToInt(String::length).max().orElseThrow();
        final var builder = IntMap.builder(rows, columns);

        for (int row = 0; row < rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < chars.length; column++) {
                switch (chars[column]) {
                    case '.' -> builder.set(row, column, 1);
                    case '#' -> builder.set(row, column, 2);
                    default -> throw new IllegalArgumentException();
                }
            }
        }

        return builder.build();
    }
}
