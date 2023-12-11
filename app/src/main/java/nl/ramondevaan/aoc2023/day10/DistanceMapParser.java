package nl.ramondevaan.aoc2023.day10;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.ArrayList;

public class DistanceMapParser implements Parser<PipeMap, DistanceMap> {
    @Override
    public DistanceMap parse(final PipeMap toParse) {
        final var builder = IntMap.builder(toParse.getRows(), toParse.getColumns());
        builder.fill(-1);
        final var start = toParse.getStart();
        final var startPipe = toParse.get(start);
        final var startDirection = startPipe.getDirections().stream().findFirst().orElseThrow();
        final var path = new ArrayList<Coordinate>();

        var rightCornerCount = 0L;
        var leftCornerCount = 0L;
        var current = start;
        var pipe = startPipe;
        var direction = startDirection;
        var distance = 0;

        do {
            path.add(current);
            builder.set(current.row(), current.column(), distance++);

            rightCornerCount = pipe.isRightCorner(direction) ? rightCornerCount + 1 : rightCornerCount;
            leftCornerCount = pipe.isLeftCorner(direction) ? leftCornerCount + 1 : leftCornerCount;

            direction = pipe.next(direction);
            current = direction.apply(current);
            pipe = toParse.get(current);
            direction = direction.opposite();
        } while (!current.equals(start));

        return new DistanceMap(builder.build(), path, startDirection, rightCornerCount > leftCornerCount);
    }
}
