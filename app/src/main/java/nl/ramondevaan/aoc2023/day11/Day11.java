package nl.ramondevaan.aoc2023.day11;

import nl.ramondevaan.aoc2023.util.Coordinate;

import java.util.List;

public class Day11 {

  private final GalaxyImage galaxyImage;

  public Day11(final List<String> lines) {
    final var parser = new GalaxyImageParser();
    this.galaxyImage = parser.parse(lines);
  }

  public long solve1() {
    return solve(1L);
  }

  public long solve2() {
    return solve(1_000_000L - 1L);
  }

  private long solve(final long multiplier) {
    final var galaxies = galaxyImage.getGalaxies();

    long distanceSum = 0L;

    for (int i = 0; i < galaxies.size(); i++) {
      for (int j = i + 1; j < galaxies.size(); j++) {
        distanceSum += distance(galaxies.get(i), galaxies.get(j), multiplier);
      }
    }

    return distanceSum;
  }

  private long distance(final Coordinate a, final Coordinate b, final long multiplier) {
    final int minRow = Math.min(a.row(), b.row());
    final int maxRow = Math.max(a.row(), b.row());
    final int minColumn = Math.min(a.column(), b.column());
    final int maxColumn = Math.max(a.column(), b.column());

    final var emptyRowsBetween = galaxyImage.getEmptyRows().stream()
            .filter(i -> minRow < i && i < maxRow).count();
    final var emptyColumnsBetween = galaxyImage.getEmptyColumns().stream()
            .filter(i -> minColumn < i && i < maxColumn).count();

    final var rowDistance = maxRow - minRow + emptyRowsBetween * multiplier;
    final var columnDistance = maxColumn - minColumn + emptyColumnsBetween * multiplier;

    return rowDistance + columnDistance;
  }
}
