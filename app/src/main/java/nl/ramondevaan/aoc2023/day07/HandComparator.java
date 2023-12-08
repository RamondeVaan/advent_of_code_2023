package nl.ramondevaan.aoc2023.day07;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Comparator;

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
        final var cardOccurrence = new int[Card.values().length];
        for (final var card : hand.getCards()) {
            if (card != Card.JOKER) {
                cardOccurrence[card.ordinal()]++;
            }
        }

        final var occurrenceValues = new int[6];
        Arrays.stream(cardOccurrence).forEach(i -> occurrenceValues[i]++);

        if (occurrenceValues[5] >= 1) {
            return HandType.FIVE_OF_A_KIND;
        } else if (occurrenceValues[4] >= 1) {
            return HandType.FOUR_OF_A_KIND;
        } else if (occurrenceValues[3] >= 1) {
            if (occurrenceValues[2] >= 1) {
                return HandType.FULL_HOUSE;
            }
            return HandType.THREE_OF_A_KIND;
        } else if (occurrenceValues[2] == 2) {
            return HandType.TWO_PAIR;
        } else if (occurrenceValues[2] >= 1) {
            return HandType.ONE_PAIR;
        }

        return HandType.HIGH_CARD;
    }
}
