package nl.ramondevaan.aoc2023.util;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.stream.IntStream;

public class ImmutableIntArray {

    private final int[] array;
    @Getter
    private final int first;
    @Getter
    private final int last;

    private ImmutableIntArray(int[] array) {
        this.array = array;
        this.first = array[0];
        this.last = array[array.length - 1];
    }

    public int get(final int index) {
        return array[index];
    }

    public int length() {
        return array.length;
    }

    public IntStream stream() {
        return Arrays.stream(array);
    }

    public boolean allEqual() {
        for (int last = 0, next = 1; next < array.length; last = next++) {
            if (array[last] != array[next]) {
                return false;
            }
        }

        return true;
    }

    public ImmutableIntArray reversed() {
        final var copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        ArrayUtils.reverse(copy);
        return new ImmutableIntArray(copy);
    }

    public ImmutableIntArray difference() {
        final var diff = new int[array.length - 1];
        for (int last = 0, next = 1; next < array.length; last = next++) {
            diff[last] = array[next] - array[last];
        }
        return new ImmutableIntArray(diff);
    }

    public static ImmutableIntArray of(final int[] of, final int size) {
        final int[] copy = new int[size];
        System.arraycopy(of, 0, copy, 0, size);
        return new ImmutableIntArray(copy);
    }
}
