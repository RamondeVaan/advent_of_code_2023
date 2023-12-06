package nl.ramondevaan.aoc2023.day06;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.text.CharacterIterator;
import java.util.List;

public class ActualRaceParser implements Parser<List<String>, Race> {

    private final static char[] TIME = new char[]{'T', 'i', 'm', 'e', ':'};
    private final static char[] DISTANCE = new char[]{'D', 'i', 's', 't', 'a', 'n', 'c', 'e', ':'};
    private final static char SEPARATOR = ' ';

    @Override
    public Race parse(final List<String> toParse) {
        final var timeParser = new StringIteratorParser(toParse.get(0));
        final var distanceParser = new StringIteratorParser(toParse.get(1));
        timeParser.consume(TIME);
        distanceParser.consume(DISTANCE);

        return new Race(parseLong(timeParser), parseLong(distanceParser));
    }

    private long parseLong(final StringIteratorParser parser) {
        parser.exhaust(SEPARATOR);

        final var builder = new StringBuilder();
        builder.append(parser.current());

        while (parser.hasNext()) {
            parser.consume();
            final var current = parser.current();
            if (Character.isDigit(current)) {
                builder.append(current);
                continue;
            }
            if (current != SEPARATOR && current != CharacterIterator.DONE) {
                throw new IllegalArgumentException();
            }
        }

        return Long.parseLong(builder.toString());
    }
}
