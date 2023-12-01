package nl.ramondevaan.aoc2023.util;

import java.util.List;

public class Cycle<T> {

  private final List<T> list;
  private int nextIndex;

  public Cycle(final List<T> list) {
    if (list.isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.list = list;
  }

  public T next() {
    final var ret = list.get(nextIndex);
    nextIndex = (nextIndex + 1) % list.size();
    return ret;
  }

  public int nextIndex() {
    return nextIndex;
  }
}
