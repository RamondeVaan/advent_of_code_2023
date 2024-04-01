package nl.ramondevaan.aoc2023.day07;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Stream;

@Getter
@ToString
public class Hand {
    private final List<Card> cards;

    public Hand(Stream<Card> cards) {
        this.cards = cards.toList();
    }
}
