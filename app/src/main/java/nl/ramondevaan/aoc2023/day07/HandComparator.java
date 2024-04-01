package nl.ramondevaan.aoc2023.day07;

import java.util.Comparator;

import static nl.ramondevaan.aoc2023.day07.HandType.*;

public class HandComparator implements Comparator<Hand> {

    private static final int CARD_TYPES = Card.values().length;
    private static final HandType[][][][][][] ARITY_COUNT_TO_HAND_TYPE = new HandType[6][6][6][6][6][6];

    static {
        ARITY_COUNT_TO_HAND_TYPE[0][5][0][0][0][0] = HIGH_CARD;
        ARITY_COUNT_TO_HAND_TYPE[0][3][1][0][0][0] = ONE_PAIR;
        ARITY_COUNT_TO_HAND_TYPE[0][1][2][0][0][0] = TWO_PAIR;
        ARITY_COUNT_TO_HAND_TYPE[0][2][0][1][0][0] = THREE_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[0][0][1][1][0][0] = FULL_HOUSE;
        ARITY_COUNT_TO_HAND_TYPE[0][1][0][0][1][0] = FOUR_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[0][0][0][0][0][1] = FIVE_OF_A_KIND;

        ARITY_COUNT_TO_HAND_TYPE[1][4][0][0][0][0] = ONE_PAIR;
        ARITY_COUNT_TO_HAND_TYPE[1][2][1][0][0][0] = THREE_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[1][0][2][0][0][0] = FULL_HOUSE;
        ARITY_COUNT_TO_HAND_TYPE[1][1][0][1][0][0] = FOUR_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[1][0][0][0][1][0] = FIVE_OF_A_KIND;

        ARITY_COUNT_TO_HAND_TYPE[2][3][0][0][0][0] = THREE_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[2][1][1][0][0][0] = FOUR_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[2][0][0][1][0][0] = FIVE_OF_A_KIND;

        ARITY_COUNT_TO_HAND_TYPE[3][2][0][0][0][0] = FOUR_OF_A_KIND;
        ARITY_COUNT_TO_HAND_TYPE[3][0][1][0][0][0] = FIVE_OF_A_KIND;

        ARITY_COUNT_TO_HAND_TYPE[4][1][0][0][0][0] = FIVE_OF_A_KIND;

        ARITY_COUNT_TO_HAND_TYPE[5][0][0][0][0][0] = FIVE_OF_A_KIND;
    }

    @Override
    public int compare(final Hand hand1, final Hand hand2) {
        final var handType1 = parse(hand1);
        final var handType2 = parse(hand2);

        var comparison = handType1.compareTo(handType2);

        if (comparison != 0) {
            return comparison;
        }

        final var c1 = hand1.getCards();
        final var c2 = hand2.getCards();

        for (int i = 0; i < hand1.getCards().size(); i++) {
            comparison = c1.get(i).compareTo(c2.get(i));

            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }

    private HandType parse(final Hand hand) {
        final var cardOccurrence = new int[CARD_TYPES];
        for (final var card : hand.getCards()) {
            cardOccurrence[card.ordinal()]++;
        }

        final var ac = new int[6];
        for (int i = 1; i < cardOccurrence.length; i++) {
            ac[cardOccurrence[i]]++;
        }
        ac[0] = cardOccurrence[0]; // Set joker

        return ARITY_COUNT_TO_HAND_TYPE[ac[0]][ac[1]][ac[2]][ac[3]][ac[4]][ac[5]];
    }
}
