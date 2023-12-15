package nl.ramondevaan.aoc2023.day15;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

public class StepsParser implements Parser<String, Step> {

    @Override
    public Step parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        final var label = parser.parseString();
        final var operation = parser.current();
        parser.consume();

        final var step = switch (operation) {
            case '=' -> new AddStep(label, parser.parseLong());
            case '-' -> new RemoveStep(label);
            default -> throw new IllegalArgumentException();
        };

        parser.verifyIsDone();

        return step;
    }
}
