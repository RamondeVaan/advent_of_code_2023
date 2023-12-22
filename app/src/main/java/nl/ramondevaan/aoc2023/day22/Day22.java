package nl.ramondevaan.aoc2023.day22;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day22 {

    private final List<Brick> bricks;
    private final SupportStructure supportStructure;
    private final Map<Boolean, List<Integer>> canDesintegrateSafely;

    public Day22(final List<String> lines) {
        final var parser = new BrickParser();
        final var supportStructureParser = new SupportStructureParser();
        this.bricks = parser.parse(lines);
        this.supportStructure = supportStructureParser.parse(bricks);
        this.canDesintegrateSafely = bricks.stream().map(Brick::id)
                .collect(Collectors.partitioningBy(id -> supportStructure.supports().get(id).stream()
                        .allMatch(other -> supportStructure.supportedBy().get(other).size() > 1)));
    }

    public long solve1() {
        return canDesintegrateSafely.get(true).size();
    }

    public long solve2() {
        return canDesintegrateSafely.get(false).stream()
                .mapToLong(this::fallingChildren)
                .sum();
    }

    private long fallingChildren(final int id) {
        var count = 0;

        final var remaining = new int[bricks.size() + 1];
        final var queue = new ArrayDeque<Integer>();
        queue.add(id);

        Integer current;
        while ((current = queue.poll()) != null) {
            for (final var child : supportStructure.supports().get(current)) {
                if (remaining[child] == 0) {
                    remaining[child] = supportStructure.supportedBy().get(child).size();
                }
                if (remaining[child] == 1) {
                    count++;
                    queue.add(child);
                } else {
                    remaining[child]--;
                }
            }
        }

        return count;
    }
}
