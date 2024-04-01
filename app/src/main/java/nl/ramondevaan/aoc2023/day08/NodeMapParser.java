package nl.ramondevaan.aoc2023.day08;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.List;

public class NodeMapParser implements Parser<List<String>, NodeMap> {

    private final static char[] NAME_SEPARATOR = new char[]{' ', '=', ' ', '('};
    private final static char[] LEFT_RIGHT_SEPARATOR = new char[]{',', ' '};
    private final static char[] END = new char[]{')'};

    @Override
    public NodeMap parse(final List<String> toParse) {
        final var builder = NodeMap.builder(toParse.size() - 2);

        toParse.getFirst().chars().mapToObj(this::parseInstruction).forEach(builder::add);
        toParse.stream().skip(2).forEach(line -> parseNode(builder, line));

        return builder.build();
    }

    private Instruction parseInstruction(final int character) {
        return switch (character) {
            case 'R' -> Instruction.RIGHT;
            case 'L' -> Instruction.LEFT;
            default -> throw new IllegalArgumentException();
        };
    }

    private void parseNode(final NodeMap.Builder builder, final String toParse) {
        final var parser = new StringIteratorParser(toParse);
        final var name = parser.parseAlphaNumeric();
        parser.consume(NAME_SEPARATOR);
        final var left = parser.parseAlphaNumeric();
        parser.consume(LEFT_RIGHT_SEPARATOR);
        final var right = parser.parseAlphaNumeric();
        parser.consume(END);
        parser.verifyIsDone();

        builder.add(name, left, right);
    }
}
