package nl.ramondevaan.aoc2023.day07;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.stream.Stream;

public class HandBidParser implements Parser<String, HandBid> {

    @Override
    public HandBid parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);
        final var hand = parseHand(parser);
        parser.consume(' ');
        final var bid = parser.parseLong();
        parser.verifyIsDone();

        return new HandBid(hand, bid);
    }

    private Hand parseHand(final StringIteratorParser parser) {
        final var cardStream = Stream.generate(() -> parseCard(parser)).limit(5);
        return new Hand(cardStream);
    }

    private Card parseCard(final StringIteratorParser parser) {
        final var card = switch (parser.current()) {
            case '2' -> Card.TWO;
            case '3' -> Card.THREE;
            case '4' -> Card.FOUR;
            case '5' -> Card.FIVE;
            case '6' -> Card.SIX;
            case '7' -> Card.SEVEN;
            case '8' -> Card.EIGHT;
            case '9' -> Card.NINE;
            case 'T' -> Card.TEN;
            case 'J' -> Card.JACK;
            case 'Q' -> Card.QUEEN;
            case 'K' -> Card.KING;
            case 'A' -> Card.ACE;
            default -> throw new IllegalArgumentException("Unexpected value: " + parser.current());
        };
        parser.consume();
        return card;
    }
}
