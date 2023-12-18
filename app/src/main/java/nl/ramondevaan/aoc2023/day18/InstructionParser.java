package nl.ramondevaan.aoc2023.day18;

import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

public class InstructionParser implements Parser<String, Instruction> {

    private final static char SEPARATOR = ' ';
    private final static char[] COLOR_START = new char[]{'(', '#'};
    private final static char COLOR_END = ')';

    @Override
    public Instruction parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        final var direction = parseDirection(parser);
        parser.consume(SEPARATOR);
        final var meters = parser.parseInteger();
        parser.consume(SEPARATOR);
        final var color = parseColor(parser);
        parser.verifyIsDone();

        return new Instruction(direction, meters, color);
    }

    private Direction parseDirection(final StringIteratorParser parser) {
        final var direction = switch (parser.current()) {
            case 'U' -> Direction.NORTH;
            case 'R' -> Direction.EAST;
            case 'D' -> Direction.SOUTH;
            case 'L' -> Direction.WEST;
            default -> throw new IllegalArgumentException();
        };
        parser.consume();
        return direction;
    }

    private int parseColor(final StringIteratorParser parser) {
        parser.consume(COLOR_START);
        final var builder = new StringBuilder();

        do {
            builder.append(parser.current());
            parser.consume();
        } while (parser.current() != COLOR_END);
        parser.consume(COLOR_END);

        return Integer.parseInt(builder.toString(), 16);
    }
}
