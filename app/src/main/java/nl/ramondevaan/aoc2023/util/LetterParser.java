package nl.ramondevaan.aoc2023.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class LetterParser implements Parser<URL, List<Letter>> {

  private final BlankStringPartitioner partitioner;

  public LetterParser() {
    partitioner = new BlankStringPartitioner();
  }

  @Override
  public List<Letter> parse(URL url) {
    return getPartitions(url).stream().map(this::parseLetter).toList();
  }

  private List<List<String>> getPartitions(URL url) {
    try {
      final var path = Path.of(Objects.requireNonNull(url).toURI());
      final var lines = Files.readAllLines(path);
      return partitioner.partition(lines);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Letter parseLetter(final List<String> toParse) {
    final var letter = toParse.get(0);
    final var pattern = parsePattern(toParse.subList(1, toParse.size()));

    return new Letter(letter, pattern);
  }

  private IntMap parsePattern(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = rows == 0 ? 0 : toParse.stream().mapToInt(String::length).max().orElseThrow();
    final var builder = IntMap.builder(rows, columns);

    for (int row = 0; row < rows; row++) {
      final char[] chars = toParse.get(row).toCharArray();
      for (int column = 0; column < chars.length; column++) {
        if (!Character.isSpaceChar(chars[column])) {
          builder.set(row, column, 1);
        }
      }
    }

    return builder.build();
  }
}
