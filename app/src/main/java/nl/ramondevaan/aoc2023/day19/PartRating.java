package nl.ramondevaan.aoc2023.day19;

public record PartRating(int x, int m, int a, int s) {

    public int sum() {
        return x + m + a + s;
    }
}
