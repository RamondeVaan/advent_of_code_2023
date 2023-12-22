package nl.ramondevaan.aoc2023.day22;

import com.google.common.collect.ImmutableSetMultimap;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SupportStructureParser implements Parser<List<Brick>, SupportStructure> {
    @Override
    public SupportStructure parse(final List<Brick> bricks) {
        final var highestZ = preparePlane(bricks);
        final var idAtHighestZ = new int[highestZ.length][highestZ[0].length];

        final var supportsMapBuilder = ImmutableSetMultimap.<Integer, Integer>builder();
        final var supportedByMapBuilder = ImmutableSetMultimap.<Integer, Integer>builder();

        final var sorted = bricks.stream().sorted(Comparator.comparingInt(Brick::lowestZ)).toList();

        for (final var brick : sorted) {
            final var highestPositions = getHighestPositions(highestZ, brick);
            final var newHeight = highestPositions.height() + brick.height();

            for (final var position : highestPositions.positions()) {
                final var supportedBy = idAtHighestZ[position.x][position.y];
                supportsMapBuilder.put(supportedBy, brick.id());
                supportedByMapBuilder.put(brick.id(), supportedBy);
            }

            for (final var position : brick.groundPlane()) {
                highestZ[position.x][position.y] = newHeight;
                idAtHighestZ[position.x][position.y] = brick.id();
            }
        }

        return new SupportStructure(supportsMapBuilder.build(), supportedByMapBuilder.build());
    }

    private int[][] preparePlane(List<Brick> bricks) {
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (final Brick brick : bricks) {
            maxX = Math.max(maxX, brick.from().x());
            maxY = Math.max(maxY, brick.from().y());
        }

        return new int[maxX + 1][maxY + 1];
    }

    private HighestPositions getHighestPositions(final int[][] highestZ, final Brick brick) {
        final var list = new ArrayList<Position>();
        var max = Integer.MIN_VALUE;
        int value;

        for (final var position : brick.groundPlane()) {
            value = highestZ[position.x][position.y];
            if (value == max) {
                list.add(position);
            } else if (value > max) {
                list.clear();
                list.add(position);
                max = value;
            }
        }

        return new HighestPositions(max, list);
    }
}
