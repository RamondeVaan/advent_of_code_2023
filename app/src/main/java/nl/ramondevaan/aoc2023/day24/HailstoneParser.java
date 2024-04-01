package nl.ramondevaan.aoc2023.day24;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.text.CharacterIterator;

public class HailstoneParser implements Parser<String, Hailstone> {

    private final static char[] SEPARATOR = new char[] {',', ' '};
    private final static char[] TUPLE_SEPARATOR = new char[] {' ', '@', ' '};

    @Override
    public Hailstone parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        long x = parseLong(parser, ',');
        parser.consume(SEPARATOR);
        parser.exhaust(' ');
        long y = parseLong(parser, ',');
        parser.consume(SEPARATOR);
        parser.exhaust(' ');
        long z = parseLong(parser, ' ');
        parser.consume(TUPLE_SEPARATOR);
        parser.exhaust(' ');
        long dx = parseLong(parser, ',');
        parser.consume(SEPARATOR);
        parser.exhaust(' ');
        long dy = parseLong(parser, ',');
        parser.consume(SEPARATOR);
        parser.exhaust(' ');
        long dz = parseLong(parser, CharacterIterator.DONE);
        parser.verifyIsDone();

        return new Hailstone(x, y, z, dx, dy, dz);
    }

    private long parseLong(final StringIteratorParser parser, char parseUntil) {
        final StringBuilder sb = new StringBuilder();

        while (parser.hasNext() && parser.current() != parseUntil) {
            sb.append(parser.current());
            parser.consume();
        }

        return Long.parseLong(sb.toString());
    }
}
