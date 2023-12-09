package nl.ramondevaan.aoc2023.day09;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.List;
import java.util.stream.IntStream;

public class ReportParser implements Parser<List<String>, Report> {
    @Override
    public Report parse(final List<String> toParse) {
        return new Report(toParse.stream().map(this::parse));
    }

    private IntStream parse(final String toParse) {
        final var builder = IntStream.builder();
        final var parser = new StringIteratorParser(toParse);
        builder.add(parser.parseInteger());

        while (parser.hasNext()) {
            parser.exhaust(' ');
            builder.add(parser.parseInteger());
        }

        return builder.build();
    }
}
