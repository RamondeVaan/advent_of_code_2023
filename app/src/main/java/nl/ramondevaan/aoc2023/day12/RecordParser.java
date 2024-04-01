package nl.ramondevaan.aoc2023.day12;

import com.google.common.base.CharMatcher;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.*;

public class RecordParser implements Parser<String, Record> {

    @Override
    public Record parse(final String toParse) {
        final var separatorIndex = toParse.indexOf(' ');
        final var numberOfDamagedGroups = CharMatcher.is(',').countIn(toParse.substring(separatorIndex)) + 1;
        final var builder = Record.builder(separatorIndex, numberOfDamagedGroups);

        parseSpringConditions(builder, toParse.substring(0, separatorIndex));
        parseDamagedGroups(builder, toParse.substring(separatorIndex + 1));

        return builder.build();
    }

    private void parseSpringConditions(final Record.Builder builder, final String toParse) {
        final var chars = toParse.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            final var value = switch (chars[i]) {
                case '.' -> OPERATIONAL;
                case '#' -> DAMAGED;
                case '?' -> UNKNOWN;
                default -> throw new IllegalArgumentException();
            };
            builder.set(i, value);
        }
    }

    private void parseDamagedGroups(final Record.Builder builder, final String toParse) {
        final var parser = new StringIteratorParser(toParse);
        var index = 0;
        builder.set(index++, parser.parseInteger());

        do {
            parser.consume(',');
            builder.set(index++, parser.parseInteger());
        } while (parser.hasNext());
    }
}
