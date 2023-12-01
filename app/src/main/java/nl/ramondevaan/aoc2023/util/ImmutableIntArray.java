package nl.ramondevaan.aoc2023.util;

public class ImmutableIntArray {

  private final int[] array;

  private ImmutableIntArray(int[] array) {
    this.array = array;
  }

  public int get(final int index) {
    return array[index];
  }

  public int length() {
    return array.length;
  }

  public static ImmutableIntArray of(final int[] of, final int size) {
    final int[] copy = new int[size];
    System.arraycopy(of, 0, copy, 0, size);
    return new ImmutableIntArray(copy);
  }
}
