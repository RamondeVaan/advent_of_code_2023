package nl.ramondevaan.aoc2023.day19;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

public class PartRatingParser implements Parser<String, PartRating> {
    @Override
    public PartRating parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        parser.consume('{');
        final var x = parse(parser, 'x');
        parser.consume(',');
        final var m = parse(parser, 'm');
        parser.consume(',');
        final var a = parse(parser, 'a');
        parser.consume(',');
        final var s = parse(parser, 's');
        parser.consume('}');
        parser.verifyIsDone();

        return new PartRating(x, m, a, s);
    }

    private int parse(final StringIteratorParser parser, final char c) {
        parser.consume(c);
        parser.consume('=');
        return parser.parseInteger();
    }
}
