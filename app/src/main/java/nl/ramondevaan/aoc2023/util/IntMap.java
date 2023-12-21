package nl.ramondevaan.aoc2023.util;

import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@EqualsAndHashCode
public class IntMap {

  private final int[][] map;
  private final int rows;
  private final int columns;
  private final int size;

  public IntMap(Stream<IntStream> stream) {
    this.map = stream.map(IntStream::toArray).toArray(int[][]::new);
    this.rows = this.map.length;
    this.columns = this.rows == 0 ? 0 : this.map[0].length;
    this.size = this.rows * this.columns;
    if (Arrays.stream(this.map, 1, rows).anyMatch(row -> row.length != columns)) {
      throw new IllegalStateException();
    }
  }

  private IntMap(final int[][] map) {
    this.map = map;
    this.rows = map.length;
    this.columns = this.rows == 0 ? 0 : this.map[0].length;
    this.size = this.rows * this.columns;
  }

  public IntStream values() {
    return IntStream.range(0, rows).flatMap(row -> Arrays.stream(this.map[row], 0, columns));
  }

  public boolean contains(Coordinate coordinate) {
    return coordinate.row() >= 0 && coordinate.row() < rows
            && coordinate.column() >= 0 && coordinate.column() < columns;
  }

  public int rows() {
    return rows;
  }

  public int columns() {
    return columns;
  }

  public int size() {
    return size;
  }

  public int valueAt(int row, int column) {
    return map[row][column];
  }

  public int valueAt(Coordinate coordinate) {
    return map[coordinate.row()][coordinate.column()];
  }

  public IntMapEntry withValueAt(Coordinate coordinate) {
    return new IntMapEntry(coordinate, map[coordinate.row()][coordinate.column()]);
  }

  public void copyInto(int row, int[] destination) {
    System.arraycopy(this.map[row], 0, destination, 0, columns);
  }

  public void copyInto(final int row, final int[] destination, final int sourceOffset, final int length) {
    System.arraycopy(this.map[row], sourceOffset, destination, 0, length);
  }

  public void copyInto(final int row, final int[] destination, final int sourceOffset, int destinationOffset, final int length) {
    System.arraycopy(this.map[row], sourceOffset, destination, destinationOffset, length);
  }

  public IntMap part(final int row, final int column, final int rows, final int columns) {
    final var builder = IntMap.builder(rows, columns);
    builder.copyFrom(this, row, 0, column, 0, rows, columns);
    return builder.build();
  }

  public IntMap rotateClockwise() {
    final var newMap = new int[columns][rows];

    for (int oldRow = 0, newColumn = rows - 1; oldRow < rows; oldRow++, newColumn--) {
      for (int oldColumn = 0, newRow = 0; oldColumn < columns; oldColumn++, newRow++) {
        newMap[newRow][newColumn] = map[oldRow][oldColumn];
      }
    }

    return new IntMap(newMap);
  }

  public IntMap rotateCounterClockwise() {
    final var newMap = new int[columns][rows];

    for (int oldRow = 0, newColumn = 0; oldRow < rows; oldRow++, newColumn++) {
      for (int oldColumn = 0, newRow = columns - 1; oldColumn < columns; oldColumn++, newRow--) {
        newMap[newRow][newColumn] = map[oldRow][oldColumn];
      }
    }

    return new IntMap(newMap);
  }

  public IntMap rotateTwice() {
    final var newMap = new int[rows][columns];

    for (int oldRow = 0, newRow = rows - 1; oldRow < rows; oldRow++, newRow--) {
      for (int oldColumn = 0, newColumn = columns - 1; oldColumn < columns; oldColumn++, newColumn--) {
        newMap[newRow][newColumn] = map[oldRow][oldColumn];
      }
    }

    return new IntMap(newMap);
  }

  public IntMap rotate(final int rotations) {
    final var clockwiseRotations = Math.floorMod(rotations, 4);

    return switch (clockwiseRotations) {
      case 0 -> this;
      case 1 -> rotateClockwise();
      case 2 -> rotateTwice();
      case 3 -> rotateCounterClockwise();
      default -> throw new IllegalStateException();
    };
  }

  public boolean isWithinRange(final Coordinate coordinate) {
    return coordinate.row() >= 0 && coordinate.row() < rows &&
            coordinate.column() >= 0 && coordinate.column() < columns;
  }

  public boolean isWithinRange(final int row, final int column) {
    return row >= 0 && row < rows && column >= 0 && column < columns;
  }

  public Builder toBuilder() {
    final var builder = new Builder(rows, columns);

    for (int row = 0; row < rows; row++) {
      copyInto(row, builder.values[row]);
    }

    return builder;
  }

  public static Builder builder(int rows, int columns) {
    return new Builder(rows, columns);
  }

  public static class Builder {
    public final int rows;
    public final int columns;
    int[][] values;

    public Builder(int rows, int columns) {
      this.values = new int[rows][columns];
      this.rows = rows;
      this.columns = columns;
    }

    public Builder set(final Coordinate coordinate, int value) {
      values[coordinate.row()][coordinate.column()] = value;
      return this;
    }

    public Builder set(int row, int column, int value) {
      values[row][column] = value;
      return this;
    }

    public Builder flag(int row, int column, int value) {
      values[row][column] |= value;
      return this;
    }

    public boolean hasFlag(int row, int column, int flag) {
      return (values[row][column] & flag) == flag;
    }

    public int get(final Coordinate coordinate) {
      return values[coordinate.row()][coordinate.column()];
    }

    public int get(final int row, final int column) {
      return values[row][column];
    }

    public Builder fill(final int value) {
      for (final int[] ints : values) {
        Arrays.fill(ints, value);
      }
      return this;
    }

    public Builder copyFrom(final IntMap map, final int sourceRowOffset, final int rowOffset,
            final int sourceColumnOffset, final int columnOffset, final int rows, final int columns) {
      final int targetRow = rowOffset + rows;

      for (int row = rowOffset, sourceRow = sourceRowOffset; row < targetRow; row++, sourceRow++) {
        map.copyInto(sourceRow, values[row], sourceColumnOffset, columnOffset, columns);
      }
      return this;
    }

    public boolean isWithinRange(final Coordinate coordinate) {
      return coordinate.row() >= 0 && coordinate.row() < rows &&
              coordinate.column() >= 0 && coordinate.column() < columns;
    }

    public IntMap build() {
      final var ret = new IntMap(values);
      this.values = null;
      return ret;
    }
  }
}
