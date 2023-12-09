package nl.ramondevaan.aoc2023.day09;

import nl.ramondevaan.aoc2023.util.ImmutableIntArray;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.List;

public class ReportParser implements Parser<List<String>, Report> {
    @Override
    public Report parse(final List<String> toParse) {
        return new Report(toParse.stream().map(this::parse));
    }

    private ImmutableIntArray parse(final String toParse) {
        final int[] values = new int[toParse.length()];
        var index = 1;

        final var parser = new StringIteratorParser(toParse);
        values[0] = parser.parseInteger();

        for (; parser.hasNext(); index++) {
            parser.consume(' ');
            values[index] = parser.parseInteger();
        }

        parser.verifyIsDone();
        return ImmutableIntArray.of(values, index);
    }
}
