package nl.ramondevaan.aoc2023.day14;

import nl.ramondevaan.aoc2023.util.IntMap;

import java.util.ArrayList;
import java.util.List;

import static nl.ramondevaan.aoc2023.day14.PlatformParser.CUBE_SHAPED_ROCK;
import static nl.ramondevaan.aoc2023.day14.PlatformParser.ROUNDED_ROCK;

public class Day14 {

    private final IntMap platform;


    public Day14(final List<String> lines) {
        final var parser = new PlatformParser();
        this.platform = parser.parse(lines);
    }

    public long solve1() {
        return load(tiltNorth(platform));
    }

    public long solve2() {
        final var cycles = new ArrayList<IntMap>();
        var last = platform;
        var indexOf = -1;

        do {
            cycles.add(last);
            last = cycle(last);
            indexOf = cycles.indexOf(last);
        } while (indexOf < 0);

        final var cycleLength = cycles.size() - indexOf;
        final var cycleOffset = (1_000_000_000 - cycles.size()) % cycleLength;
        final var cycleIndex = indexOf + cycleOffset;

        return load(cycles.get(cycleIndex));
    }

    private static IntMap cycle(final IntMap platform) {
        var current = platform;

        for (int i = 0; i < 4; i++) {
            current = tiltNorth(current).rotateClockwise();
        }

        return current;
    }

    private static IntMap tiltNorth(final IntMap platform) {
        final var builder = IntMap.builder(platform.rows(), platform.columns());

        for (int column = 0; column < builder.columns; column++) {
            int roundedRockFrom = 0, roundedRockUpTo = 0;
            for (int row = 0; row < builder.rows; row++) {
                final var value = platform.valueAt(row, column);
                if (value == CUBE_SHAPED_ROCK) {
                    builder.set(row, column, CUBE_SHAPED_ROCK);
                    for (int c = roundedRockFrom; c < roundedRockUpTo; c++) {
                        builder.set(c, column, ROUNDED_ROCK);
                    }
                    roundedRockFrom = roundedRockUpTo = row + 1;
                } else if (value == ROUNDED_ROCK) {
                    roundedRockUpTo++;
                }
            }
            for (int c = roundedRockFrom; c < roundedRockUpTo; c++) {
                builder.set(c, column, ROUNDED_ROCK);
            }
        }

        return builder.build();
    }

    private static long load(final IntMap platform) {
        var load = 0L;

        for (int row = 0; row < platform.rows(); row++) {
            final long multiplier = platform.rows() - row;
            for (int column = 0; column < platform.columns(); column++) {
                load += (platform.valueAt(row, column) >> 1) * multiplier;
            }
        }

        return load;
    }
}
