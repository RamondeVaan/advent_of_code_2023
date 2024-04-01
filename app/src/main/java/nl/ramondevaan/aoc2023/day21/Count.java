package nl.ramondevaan.aoc2023.day21;

import lombok.Getter;

@Getter
public class Count {
    private long twoRoundsAgo;
    private long last;

    public void count(long size) {
        long temp = twoRoundsAgo + size;
        twoRoundsAgo = last;
        last = temp;
    }
}
