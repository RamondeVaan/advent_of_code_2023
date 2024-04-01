package nl.ramondevaan.aoc2023.day23;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.IntMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Day23 {

    private final static Direction[] DIRECTIONS = Direction.values();
    private final IntMap map;
    private final int targetRow;
    private final int targetColumn;

    public Day23(final List<String> lines) {
        final var parser = new MapParser();
        this.map = parser.parse(lines);
        targetRow = map.rows() - 1;
        targetColumn = map.columns() - 2;
    }

    public long solve1() {
        final var queue = new ArrayDeque<PosDir>();
        var max = Long.MIN_VALUE;

        PosDir current = new PosDir(null, 0, 1, 0, Direction.NORTH);
        do {
            if (current.row() == targetRow && current.column() == targetColumn) {
                max = Math.max(current.length(), max);
                continue;
            }

            for (final var direction : DIRECTIONS) {
                if (direction == current.direction()) {
                    continue;
                }
                var nextRow = current.row() + direction.getRowDiff();
                var nextColumn = current.column() + direction.getColumnDiff();
                var nextValue = map.valueAt(nextRow, nextColumn);
                if (nextValue < 0) {
                    continue;
                }
                var newLength = current.length() + 1;
                if (nextValue < DIRECTIONS.length) {
                    if (nextValue != direction.ordinal()) {
                        continue;
                    }
                    nextRow += direction.getRowDiff();
                    nextColumn += direction.getColumnDiff();
                    newLength++;
                }
                queue.add(new PosDir(null, nextRow, nextColumn, newLength, direction.opposite()));
            }
        } while ((current = queue.poll()) != null);

        return max;
    }

    public long solve2() {
        final var map = computeDistanceMap();
        final var startIndex = map.getIndex(Coordinate.of(0,1));
        final var targetIndex = map.getIndex(Coordinate.of(targetRow, targetColumn));

        long max = Long.MIN_VALUE;
        final var queue = new ArrayDeque<Path>();

        var current = new Path(startIndex, 0, 1L << startIndex);
        do {
            if (current.index() == targetIndex && current.distance() > max) {
                max = current.distance();
                continue;
            }

            for (final var neighbor : map.getEdges().get(current.index())) {
                final var flag = (1L << neighbor);
                if ((current.seen() & flag) == 0) {
                    final var newDistance = current.distance() + map.getDistance(current.index(), neighbor);
                    final var newSeen = current.seen() | flag;
                    queue.add(new Path(neighbor, newDistance, newSeen));
                }
            }
        } while ((current = queue.poll()) != null);

        return max;
    }

    private DistanceMap computeDistanceMap() {
        final var builder = DistanceMap.builder();
        final var visited = new boolean[map.rows()][map.columns()];
        final var queue = new ArrayDeque<PosDir>();

        PosDir current = new PosDir(Coordinate.of(0, 1), 0, 1, 0, Direction.NORTH);
        do {
            if (visited[current.row()][current.column()]) {
                continue;
            }
            visited[current.row()][current.column()] = true;
            if (current.row() == targetRow && current.column() == targetColumn) {
                builder.add(current.splitPoint(), Coordinate.of(targetRow, targetColumn), current.length());
                continue;
            }

            var possibleDirections = new ArrayList<PosDir>();
            for (final var direction : DIRECTIONS) {
                if (direction == current.direction()) {
                    continue;
                }
                var nextRow = current.row() + direction.getRowDiff();
                var nextColumn = current.column() + direction.getColumnDiff();
                var nextValue = map.valueAt(nextRow, nextColumn);
                if (nextValue < 0) {
                    continue;
                }
                possibleDirections.add(new PosDir(current.splitPoint(), nextRow, nextColumn, current.length() + 1, direction.opposite()));
            }
            if (possibleDirections.size() > 1) {
                final var splitPoint = Coordinate.of(current.row(), current.column());
                builder.add(current.splitPoint(), splitPoint, current.length());
                for (final var coord : possibleDirections) {
                    queue.add(new PosDir(splitPoint, coord.row(), coord.column(), 1, coord.direction()));
                }
            } else {
                final var first = possibleDirections.getFirst();
                if (visited[first.row()][first.column()]) {
                    builder.add(current.splitPoint(), Coordinate.of(first.row(), first.column()), first.length());
                } else {
                    queue.addFirst(first);
                }
            }
        } while ((current = queue.poll()) != null);

        return builder.build();
    }
}
