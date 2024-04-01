package nl.ramondevaan.aoc2023.day04;

import com.google.common.math.LongMath;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day04 {

    private final List<Card> cards;

    public Day04(final List<String> lines) {
        final var parser = new CardParser();
        this.cards = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        return cards.stream()
                .mapToInt(Day04::matchingNumbers)
                .filter(matchingNumbers -> matchingNumbers > 0)
                .mapToLong(matchingNumbers -> LongMath.pow(2L, matchingNumbers - 1))
                .sum();
    }

    public long solve2() {
        final int[] matchingNumbers = cards.stream().mapToInt(Day04::matchingNumbers).toArray();
        final long[] multiplier = new long[cards.size()];
        Arrays.fill(multiplier, 1);

        IntStream.range(0, multiplier.length).forEach(id ->
            IntStream.rangeClosed(id + 1, id + matchingNumbers[id]).forEach(i -> multiplier[i] += multiplier[id]));

        return Arrays.stream(multiplier).sum();
    }

    private static int matchingNumbers(final Card card) {
        final var matchingNumbers = card.numbers().stream()
                .filter(card.winningNumbers()::contains)
                .count();
        return Math.toIntExact(matchingNumbers);
    }
}
