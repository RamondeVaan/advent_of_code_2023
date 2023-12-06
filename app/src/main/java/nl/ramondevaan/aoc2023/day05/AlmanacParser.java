package nl.ramondevaan.aoc2023.day05;

import nl.ramondevaan.aoc2023.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

public class AlmanacParser implements Parser<List<String>, Almanac> {

    private final static char[] SEEDS = new char[]{'s', 'e', 'e', 'd', 's', ':'};
    private final static char[] TO = new char[]{'-', 't', 'o', '-'};
    private final static char SEPARATOR = ' ';

    private final BlankStringPartitioner partitioner;

    public AlmanacParser() {
        partitioner = new BlankStringPartitioner();
    }

    @Override
    public Almanac parse(List<String> toParse) {
        final var partitions = partitioner.partition(toParse);

        final var seeds = parseSeeds(partitions.get(0));
        final var categoryMaps = partitions.stream()
                .skip(1)
                .map(this::parseCategoryMap)
                .toList();

        return new Almanac(seeds, categoryMaps);
    }

    private List<Long> parseSeeds(final List<String> toParse) {
        final var parser = new StringIteratorParser(toParse.get(0));
        parser.consume(SEEDS);

        final var seeds = new ArrayList<Long>();

        do {
            parser.consume(SEPARATOR);
            seeds.add(parser.parseLong());
        } while (parser.hasNext());

        return List.copyOf(seeds);
    }

    private CategoryMap parseCategoryMap(final List<String> toParse) {
        final var parser = new StringIteratorParser(toParse.get(0));
        final var source = parser.parseString();
        parser.consume(TO);
        final var destination = parser.parseString();

        final var ranges = toParse.stream()
                .skip(1)
                .map(this::parseRange)
                .toList();

        return new CategoryMap(source, destination, ranges);
    }

    private RangeMapping parseRange(final String toParse) {
        final var parser = new StringIteratorParser(toParse);
        final var destinationRangeStart = parser.parseLong();
        parser.consume(SEPARATOR);
        final var sourceRangeStart = parser.parseLong();
        parser.consume(SEPARATOR);
        final var length = parser.parseLong();

        return RangeMapping.of(sourceRangeStart, destinationRangeStart, length);
    }
}
