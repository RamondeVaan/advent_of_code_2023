package nl.ramondevaan.aoc2023.day10;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.ArrayList;
import java.util.List;

public class PathParser implements Parser<PipeMap, List<Coordinate>> {
    @Override
    public List<Coordinate> parse(final PipeMap toParse) {
        final var start = toParse.getStart();
        final var startPipe = toParse.get(start);
        final var startDirection = startPipe.getDirections().stream().findFirst().orElseThrow();
        final var path = new ArrayList<Coordinate>();

        var current = start;
        var pipe = startPipe;
        var direction = startDirection;

        do {
            path.add(current);

            direction = pipe.next(direction);
            current = direction.apply(current);
            pipe = toParse.get(current);
            direction = direction.opposite();
        } while (!current.equals(start));

        return List.copyOf(path);
    }
}
