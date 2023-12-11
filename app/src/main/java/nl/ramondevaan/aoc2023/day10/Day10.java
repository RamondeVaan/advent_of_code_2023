package nl.ramondevaan.aoc2023.day10;

import nl.ramondevaan.aoc2023.util.Coordinate;

import java.util.List;

public class Day10 {

    private final PipeMap pipeMap;
    private final List<Coordinate> path;

    public Day10(final List<String> lines) {
        final var pipeMapParser = new PipeMapParser();
        final var distanceMapParser = new PathParser();
        this.pipeMap = pipeMapParser.parse(lines);
        this.path = distanceMapParser.parse(pipeMap);
    }

    public long solve1() {
        return path.size() / 2;
    }

    public long solve2() {
        final var isPath = new boolean[this.pipeMap.getRows()][this.pipeMap.getColumns()];
        path.forEach(coordinate -> isPath[coordinate.row()][coordinate.column()] = true);

        var count = 0L;
        for (int row = 0; row < isPath.length; row++) {
            var inside = false;

            for (int column = 0; column < isPath[0].length; column++) {
                if (isPath[row][column]) {
                    if (pipeMap.get(Coordinate.of(row, column)).isNorth()) {
                        inside = !inside;
                    }
                } else if (inside) {
                    count++;
                }
            }
        }

        return count;
    }
}
