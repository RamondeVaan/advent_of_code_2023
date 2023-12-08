package nl.ramondevaan.aoc2023.day07;

import java.util.Comparator;
import java.util.List;

public class Day07 {

    private final List<HandBid> handBids;

    public Day07(final List<String> lines) {
        final var parser = new HandBidParser();
        this.handBids = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        final var sorted = handBids.stream()
                .sorted(Comparator.comparing(HandBid::hand, new HandComparator()))
                .toList();

        return computeWinnings(sorted);
    }

    public long solve2() {
        final var sorted = handBids.stream()
                .sorted(Comparator.comparing(handBid -> mapJackToJoker(handBid.hand()), new HandComparator()))
                .toList();

        return computeWinnings(sorted);
    }

    private static Hand mapJackToJoker(final Hand hand) {
        final var cardStream = hand.getCards().stream()
                .map(card -> card == Card.JACK ? Card.JOKER : card);

        return new Hand(cardStream);
    }

    private static long computeWinnings(final List<HandBid> handBids) {
        var multiplier = 1;
        var sum = 0L;

        for (final var handBid : handBids) {
            sum += handBid.bid() * multiplier++;
        }

        return sum;
    }
}
