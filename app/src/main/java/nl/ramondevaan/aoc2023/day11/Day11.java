package nl.ramondevaan.aoc2023.day11;

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
    final var galaxyRow = new long[galaxyImage.getRows()][galaxyImage.getColumns()];
    final var galaxyColumn = new long[galaxyImage.getRows()][galaxyImage.getColumns()];

    for (final var galaxy : galaxyImage.getGalaxies()) {
      galaxyRow[galaxy.row()][galaxy.column()] = galaxy.row();
      galaxyColumn[galaxy.row()][galaxy.column()] = galaxy.column();
    }

    for (final var emptyRow : galaxyImage.getEmptyRows()) {
      for (int row = emptyRow + 1; row < galaxyImage.getRows(); row++) {
        for (int column = 0; column < galaxyImage.getColumns(); column++) {
          galaxyRow[row][column] += multiplier;
        }
      }
    }

    for (final var emptyColumn : galaxyImage.getEmptyColumns()) {
      for (int column = emptyColumn + 1; column < galaxyImage.getColumns(); column++) {
        for (int row = 0; row < galaxyImage.getRows(); row++) {
          galaxyColumn[row][column] += multiplier;
        }
      }
    }

      return getDistanceSum(galaxyRow, galaxyColumn);
  }


  private long getDistanceSum(final long[][] galaxyRow, final long[][] galaxyColumn) {
    final var galaxies = galaxyImage.getGalaxies();

    long distanceSum = 0L;

    for (int i = 0; i < galaxies.size(); i++) {
      final var from = galaxies.get(i);
      final var fromRow = galaxyRow[from.row()][from.column()];
      final var fromColumn = galaxyColumn[from.row()][from.column()];
      for (int j = i + 1; j < galaxies.size(); j++) {
        final var to = galaxies.get(j);
        final var toRow = galaxyRow[to.row()][to.column()];
        final var toColumn = galaxyColumn[to.row()][to.column()];
        distanceSum += Math.abs(toRow - fromRow) + Math.abs(toColumn - fromColumn);
      }
    }
    return distanceSum;
  }
}
