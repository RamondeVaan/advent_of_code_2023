package nl.ramondevaan.aoc2023.day10;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PipeMapParser implements Parser<List<String>, PipeMap> {
    @Override
    public PipeMap parse(List<String> toParse) {
        final var rows = toParse.size();
        final var columns = toParse.getFirst().length();
        final var builder = PipeMap.builder(rows, columns);
        Coordinate start = null;

        for (int row = 0; row < rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < columns; column++) {
                final var c = chars[column];
                if (c == 'S') {
                    start = Coordinate.of(row, column);
                    continue;
                }

                parsePipe(builder, row, column, c);
            }
        }
        parseStartPipe(builder, start);

        return builder.build();
    }

    private void parsePipe(final PipeMap.Builder builder, final int row, final int column, final char c) {
        final var pipe = switch (c) {
            case 'L' -> Pipe.NORTH_EAST;
            case 'J' -> Pipe.NORTH_WEST;
            case 'F' -> Pipe.SOUTH_EAST;
            case '7' -> Pipe.SOUTH_WEST;
            case '-' -> Pipe.WEST_EAST;
            case '|' -> Pipe.NORTH_SOUTH;
            case '.' -> Pipe.GROUND;
            default -> throw new IllegalArgumentException();
        };

        builder.set(row, column, pipe);
    }

    private void parseStartPipe(final PipeMap.Builder builder, final Coordinate start) {
        final var validDirections = Arrays.stream(Direction.values())
                .filter(direction -> isValid(builder, direction.apply(start), direction.opposite()))
                .collect(Collectors.toSet());

        final var pipes = Arrays.stream(Pipe.values())
                .filter(pipe -> pipe.getDirections().equals(validDirections))
                .toList();

        if (pipes.size() != 1) {
            throw new IllegalStateException();
        }

        builder.setStart(start.row(), start.column());
        builder.set(start.row(), start.column(), pipes.getFirst());
    }

    private boolean isValid(final PipeMap.Builder builder, final Coordinate coordinate, final Direction from) {
        final var row = coordinate.row();
        final var column = coordinate.column();

        return builder.isInRange(row, column) && builder.get(row, column).getDirections().contains(from);
    }
}
