package nl.ramondevaan.aoc2023.day01;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Day01 {

  private final List<String> lines;
  private final Map<String, Long> stringValueMap;

  public Day01(final List<String> lines) {
    final var parser = new TextualNumbersParser();
    this.stringValueMap = parser.parse(Day01.class.getResource("/day_01_textual_numbers.txt"));

    this.lines = lines.stream().toList();
  }

  public long solve1() {
    return lines.stream().mapToLong(Day01::calibrationValue).sum();
  }

  private static long calibrationValue(final String line) {
    long first = 0L;
    var index = 0;

    final var chars = line.toCharArray();

    for (; index < chars.length; index++) {
      if (Character.isDigit(chars[index])) {
        first = chars[index] - '0';
        break;
      }
    }
    for (int i = chars.length - 1; i >= index; i--) {
      if (Character.isDigit(chars[i])) {
        final long last = chars[i] - '0';
        return 10 * first + last;
      }
    }

    throw new IllegalStateException("Line did not contain digit");
  }

  public long solve2() {
    return lines.stream().mapToLong(this::calibrationValueByMap).sum();
  }

  private long calibrationValueByMap(final String line) {
    final var first = stringValueMap.entrySet().stream()
            .map(entry -> ImmutablePair.of(entry, line.indexOf(entry.getKey())))
            .filter(pair -> pair.getRight() >= 0)
            .min(Comparator.comparingInt(ImmutablePair::getRight))
            .map(pair -> pair.getLeft().getValue())
            .orElseThrow();
    final var last = stringValueMap.entrySet().stream()
            .map(entry -> ImmutablePair.of(entry, line.lastIndexOf(entry.getKey())))
            .filter(pair -> pair.getRight() >= 0)
            .max(Comparator.comparingInt(ImmutablePair::getRight))
            .map(pair -> pair.getLeft().getValue())
            .orElseThrow();
    return first * 10 + last;
  }
}
