package nl.ramondevaan.aoc2023.day13;

import nl.ramondevaan.aoc2023.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2023.util.IntMap;

import java.util.List;

public class Day13 {

  private final List<IntMap> patterns;

  public Day13(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var parser = new PatternsParser();
    this.patterns = partitioner.partition(lines).stream().map(parser::parse).toList();
  }

  public long solve1() {
    return solve(0);
  }

  public long solve2() {
    return solve(1);
  }

  private long solve(final int smudgeCount) {
    return patterns.stream()
            .mapToLong(pattern -> getValue(pattern, smudgeCount))
            .sum();
  }

  private static long getValue(final IntMap pattern, final int smudgeCount) {
    final var columnCount = getMirrorColumnCount(pattern, smudgeCount);
    final var rowCount = getMirrorRowCount(pattern, smudgeCount);

    return columnCount + 100L * rowCount;
  }

  private static long getMirrorColumnCount(final IntMap pattern, final int smudgeCount) {
    for (int column = 1; column < pattern.columns(); column++) {
      if (columnIsMirror(pattern, column, smudgeCount)) {
        return column;
      }
    }
    return 0L;
  }

  private static long getMirrorRowCount(final IntMap pattern, final int smudgeCount) {
    for (int row = 1; row < pattern.rows(); row++) {
      if (rowIsMirror(pattern, row, smudgeCount)) {
        return row;
      }
    }

    return 0L;
  }

  private static boolean columnIsMirror(final IntMap pattern, final int column, int smudgeCount) {
    var smudges = 0;

    for (int left = column - 1, right = column; left >= 0 && right < pattern.columns(); left--, right++) {
      for (int row = 0; row < pattern.rows(); row++) {
        if (pattern.valueAt(row, left) != pattern.valueAt(row, right)) {
          if (++smudges > smudgeCount) {
            return false;
          }
        }
      }
    }

    return smudges == smudgeCount;
  }

  private static boolean rowIsMirror(final IntMap pattern, final int row, int smudgeCount) {
    var smudges = 0;

    for (int left = row - 1, right = row; left >= 0 && right < pattern.rows(); left--, right++) {
      for (int column = 0; column < pattern.columns(); column++) {
        if (pattern.valueAt(left, column) != pattern.valueAt(right, column)) {
          if (++smudges > smudgeCount) {
            return false;
          }
        }
      }
    }

    return smudges == smudgeCount;
  }
}
