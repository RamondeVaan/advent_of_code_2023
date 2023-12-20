package nl.ramondevaan.aoc2023.day20;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationParser implements Parser<List<String>, Configuration> {

    private final static char[] ARROW = new char[]{' ', '-', '>', ' '};

    @Override
    public Configuration parse(final List<String> toParse) {
        final var builder = Configuration.builder();

        for (final var line : toParse) {
            final var parser = new StringIteratorParser(line);
            final var module = parseModule(parser);
            parser.consume(ARROW);
            final var names = parseNames(parser);
            builder.add(module);
            builder.add(module.name(), names);
        }

        return builder.build();
    }

    private Module parseModule(final StringIteratorParser parser) {
        final var type = parser.current();

        switch (type) {
            case '%':
                parser.consume();
                return new FlipFlopModule(parser.parseString());
            case '&':
                parser.consume();
                return new ConjunctionModule(parser.parseString());
        }

        final String name = parser.parseString();
        if ("broadcaster".equals(name)) {
            return new BroadcastModule(name);
        }

        throw new IllegalArgumentException();
    }

    private List<String> parseNames(final StringIteratorParser parser) {
        final var names = new ArrayList<String>();

        names.add(parser.parseString());

        while (parser.hasNext()) {
            parser.consume(',');
            parser.consume(' ');
            names.add(parser.parseString());
        }

        return names;
    }
}
