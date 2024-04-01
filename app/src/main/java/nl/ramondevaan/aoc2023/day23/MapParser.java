package nl.ramondevaan.aoc2023.day23;

import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.List;

public class MapParser implements Parser<List<String>, IntMap> {
    public static final int FOREST = -1;
    public static final int SLOPE_NORTH = Direction.NORTH.ordinal();
    public static final int SLOPE_EAST = Direction.EAST.ordinal();
    public static final int SLOPE_SOUTH = Direction.SOUTH.ordinal();
    public static final int SLOPE_WEST = Direction.WEST.ordinal();
    public static final int PATH = Direction.values().length;

    @Override
    public IntMap parse(final List<String> toParse) {
        final var builder = IntMap.builder(toParse.size(), toParse.getFirst().length());

        for (int row = 0; row < builder.rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < builder.columns; column++) {
                final var value = switch (chars[column]) {
                    case '#' -> FOREST;
                    case '^' -> SLOPE_NORTH;
                    case '>' -> SLOPE_EAST;
                    case 'v' -> SLOPE_SOUTH;
                    case '<' -> SLOPE_WEST;
                    case '.' -> PATH;
                    default -> throw new IllegalArgumentException();
                };
                builder.set(row, column, value);
            }
        }

        return builder.build();
    }
}
