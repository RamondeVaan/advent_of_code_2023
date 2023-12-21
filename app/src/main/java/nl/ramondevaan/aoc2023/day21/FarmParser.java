package nl.ramondevaan.aoc2023.day21;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.List;

public class FarmParser implements Parser<List<String>, Farm> {

    public static final int GARDEN_PLOT = 0;
    public static final int ROCK = 1;

    @Override
    public Farm parse(final List<String> toParse) {
        final var builder = IntMap.builder(toParse.size(), toParse.getFirst().length());
        Coordinate start = null;

        for (int row = 0; row < builder.rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < builder.columns; column++) {
                switch (chars[column]) {
                    case '.' -> builder.set(row, column, GARDEN_PLOT);
                    case '#' -> builder.set(row, column, ROCK);
                    case 'S' -> {
                        if (start != null) {
                            throw new IllegalArgumentException();
                        }
                        start = Coordinate.of(row, column);
                        builder.set(row, column, GARDEN_PLOT);
                    }
                    default -> throw new IllegalArgumentException();
                }
            }
        }
        if (start == null) {
            throw new IllegalArgumentException();
        }

        return new Farm(builder.build(), start);
    }
}
