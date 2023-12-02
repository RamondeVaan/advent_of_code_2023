package nl.ramondevaan.aoc2023.day02;

import java.util.List;

public class Day02 {

    private final static Cubes CUBES_PRESENT = new Cubes(12, 13, 14);
    private final List<Game> games;

    public Day02(final List<String> lines) {
        final var parser = new GameParser();

        this.games = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        return games.stream()
                .filter(game -> game.cubes().stream().allMatch(cubes -> cubes.possibleWithCubes(CUBES_PRESENT)))
                .mapToLong(Game::id)
                .sum();
    }

    public long solve2() {
        return games.stream()
                .map(Day02::minCubesNecessary)
                .mapToLong(Cubes::power)
                .sum();
    }

    private static Cubes minCubesNecessary(final Game game) {
        int red = 0;
        int green = 0;
        int blue = 0;

        for (final var cubes : game.cubes()) {
            red = Math.max(red, cubes.red());
            green = Math.max(green, cubes.green());
            blue = Math.max(blue, cubes.blue());
        }

        return new Cubes(red, green, blue);
    }
}
