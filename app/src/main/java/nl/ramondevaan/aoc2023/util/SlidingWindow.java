package nl.ramondevaan.aoc2023.util;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class SlidingWindow {

  public static <T, R> Stream<Window<R>> windowStream(final List<T> backingList, final int windowSize,
                                                   final WindowConsumer<T, R> consumer) {
    final var spliterator = new Spliterators.AbstractSpliterator<Window<R>>(
        Math.max(0, backingList.size() - windowSize + 1),
        Spliterator.ORDERED | Spliterator.SIZED) {
      private boolean initialize = true;
      private int startIndexInclusive = 0;
      private int endIndexExclusive = windowSize;

      @Override
      public boolean tryAdvance(Consumer<? super Window<R>> action) {
        if (endIndexExclusive < backingList.size()) {
          if (initialize) {
            for (int i = 0; i < windowSize; i++) {
              consumer.initializeAdd(backingList.get(i));
            }
            initialize = false;
            final var value = consumer.getValue();
            action.accept(new Window<>(startIndexInclusive, endIndexExclusive, value));
            return true;
          }
          final var value = consumer.slide(backingList.get(startIndexInclusive++), backingList.get(endIndexExclusive++));
          action.accept(new Window<>(startIndexInclusive, endIndexExclusive, value));
          return true;
        }

        return false;
      }
    };

    return StreamSupport.stream(spliterator, false);
  }

  public record Window<T>(int startIndexInclusive, int endIndexExclusive, T value) {

  }

  public interface WindowConsumer<T, R> {
    void initializeAdd(final T add);

    R getValue();

    R slide(final T remove, final T add);
  }
}
