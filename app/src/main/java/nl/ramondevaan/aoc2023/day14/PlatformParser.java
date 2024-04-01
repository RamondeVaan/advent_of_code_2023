package nl.ramondevaan.aoc2023.day14;

import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.List;

public class PlatformParser implements Parser<List<String>, IntMap> {

    public static final int EMPTY_SPACE = 0;
    public static final int CUBE_SHAPED_ROCK = 1;
    public static final int ROUNDED_ROCK = 2;

    @Override
    public IntMap parse(final List<String> toParse) {
        final var builder = IntMap.builder(toParse.size(), toParse.getFirst().length());

        for (int row = 0; row < builder.rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < builder.columns; column++) {
                switch (chars[column]) {
                    case '.' -> {

                    }
                    case '#' -> builder.set(row, column, CUBE_SHAPED_ROCK);
                    case 'O' -> builder.set(row, column, ROUNDED_ROCK);
                    default -> throw new IllegalArgumentException();
                }
            }
        }

        return builder.build();
    }
}
