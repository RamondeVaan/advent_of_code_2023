package nl.ramondevaan.aoc2023.day25;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.SetMultimap;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.HashMap;
import java.util.List;

public class ConnectionsParser implements Parser<List<String>, SetMultimap<Key, Key>> {

    private final static char SEPARATOR = ':';

    @Override
    public SetMultimap<Key, Key> parse(final List<String> toParse) {
        var id = 0;
        final var map = new HashMap<String, Key>();
        final var builder = ImmutableSetMultimap.<Key, Key>builder();

        for (final var line : toParse) {
            final var parser = new StringIteratorParser(line);

            final var from = parser.parseString();
            var fromId = map.get(from);
            if (fromId == null) {
                map.put(from, fromId = new Key(id++, 1));
            }
            parser.consume(SEPARATOR);
            while (parser.hasNext()) {
                parser.exhaust(' ');
                final var to = parser.parseString();
                var toId = map.get(to);
                if (toId == null) {
                    map.put(to, toId = new Key(id++, 1));
                }
                builder.put(fromId, toId);
                builder.put(toId, fromId);
            }
        }

        return builder.build();
    }
}
