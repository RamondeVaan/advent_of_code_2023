package nl.ramondevaan.aoc2023.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringIteratorParser {

  private final StringCharacterIterator iterator;

  public StringIteratorParser(final String toParse) {
    this.iterator = new StringCharacterIterator(toParse);
  }

  public boolean hasNext() {
    return iterator.current() != CharacterIterator.DONE;
  }

  public void verifyIsDone() {
    if (iterator.current() != CharacterIterator.DONE) {
      throw new IllegalArgumentException();
    }
  }

  public boolean consumeIfPresent(final char character) {
    if (iterator.current() == character) {
      iterator.next();
      return true;
    }

    return false;
  }

  public char current() {
    return iterator.current();
  }

  public boolean tryConsume(final char[] chars) {
    for (final var expected : chars) {
      if (iterator.current() != expected) {
        return false;
      }
      iterator.next();
    }

    return true;
  }

  public boolean consumeIfNotDone(final char[] chars) {
    if (iterator.current() == CharacterIterator.DONE) {
      return false;
    }
    for (final var expected : chars) {
      if (iterator.current() != expected) {
        throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
      }
      iterator.next();
    }

    return true;
  }

  public void consume() {
    iterator.next();
  }

  public void consume(final char character) {
    if (iterator.current() != character) {
      throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
    }
    iterator.next();
  }

  public void consume(final char[] chars) {
    for (final var expected : chars) {
      if (iterator.current() != expected) {
        throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
      }
      iterator.next();
    }
  }

  public void exhaust(final char character) {
    char current = iterator.current();

    while (current == character && current != CharacterIterator.DONE) {
      current = iterator.next();
    }
  }

  public int parseInteger() {
    return Integer.parseInt(parseNumber());
  }

  public long parseLong() {
    return Long.parseLong(parseNumber());
  }

  private String parseNumber() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return builder.toString();
  }

  public byte parseByte() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return Byte.parseByte(builder.toString());
  }

  public String parseString() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isAlphabetic(iterator.current())) {
      builder.append(iterator.current());
    }

    return builder.toString();
  }

  public String parseAlphaNumeric() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isLetterOrDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return builder.toString();
  }

  public int getIndex() {
    return iterator.getIndex();
  }
}
