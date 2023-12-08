package nl.ramondevaan.aoc2023.day07;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static nl.ramondevaan.aoc2023.day07.HandType.*;

@RequiredArgsConstructor
public class HandComparator implements Comparator<Hand> {

    @Override
    public int compare(final Hand hand1, final Hand hand2) {
        final var handType1 = parse(hand1);
        final var handType2 = parse(hand2);

        var comparison = handType1.compareTo(handType2);

        if (comparison != 0) {
            return comparison;
        }

        for (int i = 0; i < hand1.getCards().size(); i++) {
            comparison = hand1.getCards().get(i).compareTo(hand2.getCards().get(i));

            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }
    private HandType parse(final Hand hand) {
        final var handType = parseWithoutJoker(hand);
        final var numberOfJokers = Math.toIntExact(hand.getCards().stream().filter(card -> card == Card.JOKER).count());

        return switch (numberOfJokers) {
            case 0 -> handType;
            case 1 -> switch (handType) {
                case FOUR_OF_A_KIND -> FIVE_OF_A_KIND;
                case THREE_OF_A_KIND -> FOUR_OF_A_KIND;
                case TWO_PAIR -> FULL_HOUSE;
                case ONE_PAIR -> THREE_OF_A_KIND;
                default -> ONE_PAIR;
            };
            case 2 -> switch (handType) {
                case THREE_OF_A_KIND -> FIVE_OF_A_KIND;
                case ONE_PAIR -> FOUR_OF_A_KIND;
                default -> THREE_OF_A_KIND;
            };
            case 3 -> handType == HandType.ONE_PAIR ? FIVE_OF_A_KIND : FOUR_OF_A_KIND;
            default -> FIVE_OF_A_KIND;
        };
    }

    private HandType parseWithoutJoker(final Hand hand) {
        final var handWithoutJokers = new Hand(hand.getCards().stream().filter(card -> card != Card.JOKER));
        final var cardOccurenceMap = handWithoutJokers.getCards().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        final var occurrenceValues = cardOccurenceMap.values().stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        if (occurrenceValues.getOrDefault(5L, 0L) >= 1) {
            return HandType.FIVE_OF_A_KIND;
        } else if (occurrenceValues.getOrDefault(4L, 0L) >= 1) {
            return HandType.FOUR_OF_A_KIND;
        } else if (occurrenceValues.getOrDefault(3L, 0L) >= 1) {
            if (occurrenceValues.getOrDefault(2L, 0L) >= 1) {
                return HandType.FULL_HOUSE;
            }
            return HandType.THREE_OF_A_KIND;
        } else if (occurrenceValues.getOrDefault(2L, 0L) == 2L) {
            return HandType.TWO_PAIR;
        } else if (occurrenceValues.getOrDefault(2L, 0L) >= 1) {
            return HandType.ONE_PAIR;
        }

        return HandType.HIGH_CARD;
    }
}
