package nl.ramondevaan.aoc2023.day06;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

public class RacesParser implements Parser<List<String>, List<Race>> {
    private final static char[] TIME = new char[]{'T', 'i', 'm', 'e', ':'};
    private final static char[] DISTANCE = new char[]{'D', 'i', 's', 't', 'a', 'n', 'c', 'e', ':'};
    private final static char SEPARATOR = ' ';

    @Override
    public List<Race> parse(final List<String> toParse) {
        final var timeParser = new StringIteratorParser(toParse.get(0));
        final var distanceParser = new StringIteratorParser(toParse.get(1));
        timeParser.consume(TIME);
        distanceParser.consume(DISTANCE);

        final var races = new ArrayList<Race>();

        do {
            timeParser.exhaust(SEPARATOR);
            distanceParser.exhaust(SEPARATOR);
            races.add(new Race(timeParser.parseLong(), distanceParser.parseLong()));
        } while (timeParser.hasNext());

        return List.copyOf(races);
    }
}
