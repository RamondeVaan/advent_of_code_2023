package nl.ramondevaan.aoc2023.day12;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.*;

public class RecordParser implements Parser<String, Record> {

    private final char SEPARATOR = ' ';

    @Override
    public Record parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        final var springConditions = parseSpringConditions(parser);
        parser.consume(SEPARATOR);
        final var damagedGroups = parseDamagedGroups(parser);
        parser.verifyIsDone();

        return new Record(springConditions, damagedGroups);
    }

    private List<SpringCondition> parseSpringConditions(final StringIteratorParser parser) {
        final var springConditions = new ArrayList<SpringCondition>();

        char current = parser.current();

        do {
            final var value = switch (current) {
                case '.' -> OPERATIONAL;
                case '#' -> DAMAGED;
                case '?' -> UNKNOWN;
                default -> throw new IllegalArgumentException();
            };
            springConditions.add(value);

            parser.consume();
            current = parser.current();
        } while (current != SEPARATOR);

        return springConditions;
    }

    private List<Integer> parseDamagedGroups(final StringIteratorParser parser) {
        final var damagedGroups = new ArrayList<Integer>();
        damagedGroups.add(parser.parseInteger());

        do {
            parser.consume(',');
            damagedGroups.add(parser.parseInteger());
        } while (parser.hasNext());

        return damagedGroups;
    }
}
