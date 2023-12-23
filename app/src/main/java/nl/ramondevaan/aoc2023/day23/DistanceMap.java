package nl.ramondevaan.aoc2023.day23;

import com.google.common.collect.ImmutableSetMultimap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2023.util.Coordinate;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DistanceMap {
    private final Map<Coordinate, Integer> coordinateToIndex;
    private final int[][] distanceMatrix;

    @Getter
    private final ImmutableSetMultimap<Integer, Integer> edges;

    public int getDistance(final int fromIndex, final int toIndex) {
        return distanceMatrix[fromIndex][toIndex];
    }

    public int getIndex(final Coordinate coordinate) {
        return coordinateToIndex.get(coordinate);
    }

    public int size() {
        return distanceMatrix.length;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int id;
        private Map<Coordinate, Integer> idMap;
        private Map<ImmutablePair<Integer, Integer>, Integer> distances;
        private ImmutableSetMultimap.Builder<Integer, Integer> edges;

        private Builder() {
            id = 0;
            idMap = new HashMap<>();
            distances = new HashMap<>();
            edges = ImmutableSetMultimap.builder();
        }

        public Builder add(final Coordinate from, final Coordinate to, final int length) {
            final var fromId = getId(from);
            final var toId = getId(to);

            edges.put(fromId, toId);
            edges.put(toId, fromId);
            final var key1 = ImmutablePair.of(fromId, toId);
            final var key2 = ImmutablePair.of(toId, fromId);
            final int value = distances.compute(key1,
                    (ints, integer) -> integer == null ? length : Math.max(length, integer));
            distances.put(key2, value);
            return this;
        }

        private int getId(final Coordinate coordinate) {
            return idMap.computeIfAbsent(coordinate, ignored -> id++);
        }

        public DistanceMap build() {
            final var distanceMatrix = new int[id][id];

            distances.forEach((pair, value) -> distanceMatrix[pair.left][pair.right] = value);

            final var ret = new DistanceMap(idMap, distanceMatrix, edges.build());
            idMap = null;
            distances = null;
            edges = null;
            return ret;
        }
    }
}
