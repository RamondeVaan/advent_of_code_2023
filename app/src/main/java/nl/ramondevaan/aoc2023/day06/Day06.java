package nl.ramondevaan.aoc2023.day06;

import java.util.List;

public class Day06 {

    private final List<Race> races;
    private final Race actualRace;

    public Day06(final List<String> lines) {
        final var parser = new RacesParser();
        this.races = parser.parse(lines);

        final var actualRaceParser = new ActualRaceParser();
        this.actualRace = actualRaceParser.parse(lines);
    }

    public long solve1() {
        return races.stream()
                .mapToLong(Day06::waysToBeatRecord)
                .reduce(1L, (a, b) -> a * b);
    }

    private static long waysToBeatRecord(final Race race) {
        final var discriminator = race.time() * race.time() - (4 * race.bestDistance());
        final var discriminatorRoot = Math.sqrt(discriminator);
        final long min = (long) Math.floor((race.time() - discriminatorRoot) / 2d) + 1;
        final long max = (long) Math.ceil((race.time() + discriminatorRoot) / 2d) - 1;
        return max - min + 1L;
    }

    public long solve2() {
        return waysToBeatRecord(actualRace);
    }
}
