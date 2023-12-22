package nl.ramondevaan.aoc2023.day22;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.Arrays;
import java.util.List;

public class BrickParser implements Parser<List<String>, List<Brick>> {

    @Override
    public List<Brick> parse(final List<String> toParse) {
        final var bricks = new Brick[toParse.size()];

        for (int index = 0, id = 1; index < toParse.size(); index = id++) {
            bricks[index] = parse(id, new StringIteratorParser(toParse.get(index)));
        }

        return Arrays.asList(bricks);
    }

    private Brick parse(int id, final StringIteratorParser parser) {
        final var from = parsePosition(parser);
        parser.consume('~');
        final var to = parsePosition(parser);

        return new Brick(id, from, to);
    }

    private Position3d parsePosition(final StringIteratorParser parser) {
        final int x = parser.parseInteger();
        parser.consume(',');
        final int y = parser.parseInteger();
        parser.consume(',');
        final int z = parser.parseInteger();

        return new Position3d(x, y, z);
    }
}
