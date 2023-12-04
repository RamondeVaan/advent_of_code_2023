package nl.ramondevaan.aoc2023.day04;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardParser implements Parser<String, Card> {

    private final static char[] CARD = new char[]{'C', 'a', 'r', 'd', ' '};
    private final static char NUMBER_SEPARATOR = ' ';
    private final static char CARD_SEPARATOR = ':';
    private final static char NUMBER_LIST_SEPARATOR = '|';

    @Override
    public Card parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        parseCard(parser);
        final var winningNumbers = parseWinningNumbers(parser);
        parser.consume(NUMBER_LIST_SEPARATOR);
        parser.exhaust(NUMBER_SEPARATOR);
        final var numbers = parseNumbers(parser);

        return new Card(winningNumbers, numbers);
    }

    private void parseCard(final StringIteratorParser parser) {
        parser.consume(CARD);
        parser.exhaust(NUMBER_SEPARATOR);
        parser.parseInteger();
        parser.consume(CARD_SEPARATOR);

        parser.exhaust(NUMBER_SEPARATOR);
    }

    private Set<Integer> parseWinningNumbers(final StringIteratorParser parser) {
        final var winningNumbers = new HashSet<Integer>();

        do {
            winningNumbers.add(parser.parseInteger());
            parser.exhaust(NUMBER_SEPARATOR);
        } while (parser.current() != NUMBER_LIST_SEPARATOR);

        return Set.copyOf(winningNumbers);
    }

    private List<Integer> parseNumbers(final StringIteratorParser parser) {
        final var numbers = new ArrayList<Integer>();

        do {
            numbers.add(parser.parseInteger());
            parser.exhaust(NUMBER_SEPARATOR);
        } while (parser.hasNext());

        return List.copyOf(numbers);
    }
}
