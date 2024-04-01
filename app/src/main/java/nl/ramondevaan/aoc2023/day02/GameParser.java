package nl.ramondevaan.aoc2023.day02;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

public class GameParser implements Parser<String, Game> {
    private final static char[] GAME = new char[]{'G', 'a', 'm', 'e', ' '};
    private final static char[] GAME_SEPARATOR = new char[]{':', ' '};
    private final static char[] CUBE_SEPARATOR = new char[]{',', ' '};
    private final static char[] CUBES_SEPARATOR = new char[]{';', ' '};
    private final static char[] BLUE = new char[]{'b', 'l', 'u', 'e'};
    private final static char[] RED = new char[]{'r', 'e', 'd'};
    private final static char[] GREEN = new char[]{'g', 'r', 'e', 'e', 'n'};

    @Override
    public Game parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        final long id = parseId(parser);
        final var cubes = new ArrayList<Cubes>();

        do {
            cubes.add(parseCubes(parser));
        } while (parser.tryConsume(CUBES_SEPARATOR));

        return new Game(id, List.copyOf(cubes));
    }

    private long parseId(final StringIteratorParser parser) {
        parser.consume(GAME);
        final int id = parser.parseInteger();
        parser.consume(GAME_SEPARATOR);
        return id;
    }

    private Cubes parseCubes(final StringIteratorParser parser) {
        int red = 0;
        int green = 0;
        int blue = 0;

        do {
            final int value = parser.parseInteger();
            parser.consume(' ');
            switch (parser.current()) {
                case 'r': {
                    parser.consume(RED);
                    red = value;
                    break;
                }
                case 'g': {
                    parser.consume(GREEN);
                    green = value;
                    break;
                }
                case 'b': {
                    parser.consume(BLUE);
                    blue = value;
                    break;
                }
                default: throw new IllegalStateException("Unexpected token: " + parser.current());
            }
        } while (parser.tryConsume(CUBE_SEPARATOR));

        return new Cubes(red, green, blue);
    }
}
