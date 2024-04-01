package nl.ramondevaan.aoc2023.day16;

import nl.ramondevaan.aoc2023.util.Parser;

import java.util.List;

public class MirrorsParser implements Parser<List<String>, Mirrors> {
    @Override
    public Mirrors parse(final List<String> toParse) {
        final var builder = Mirrors.builder(toParse.size(), toParse.getFirst().length());

        for (int row = 0; row < builder.rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < builder.columns; column++) {
                final var mirror = switch (chars[column]) {
                    case '.' -> Mirror.EMPTY_SPACE;
                    case '/' -> Mirror.NORTH_WEST;
                    case '\\' -> Mirror.NORTH_EAST;
                    case '|' -> Mirror.NORTH_SOUTH;
                    case '-' -> Mirror.WEST_EAST;
                    default -> throw new IllegalArgumentException();
                };
                builder.set(row, column, mirror);
            }
        }

        return builder.build();
    }
}
