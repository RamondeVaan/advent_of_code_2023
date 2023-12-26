package nl.ramondevaan.aoc2023.day25;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.List;

public class ConnectionsParser implements Parser<List<String>, SetMultimap<String, String>> {

    private final static char SEPARATOR = ':';

    @Override
    public SetMultimap<String, String> parse(final List<String> toParse) {
        final var builder = ImmutableSetMultimap.<String, String>builder();

        for (final var line : toParse) {
            final var parser = new StringIteratorParser(line);

            final var from = parser.parseString();
            parser.consume(SEPARATOR);
            while (parser.hasNext()) {
                parser.exhaust(' ');
                final var to = parser.parseString();
                builder.put(from, to);
                builder.put(to, from);
            }
        }

        return builder.build();
    }
}
