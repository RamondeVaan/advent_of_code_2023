package nl.ramondevaan.aoc2023.day10;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2023.util.Direction;

import java.util.Map;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Pipe {
    NORTH_EAST(Direction.NORTH, Direction.EAST),
    NORTH_WEST(Direction.NORTH, Direction.WEST),
    SOUTH_EAST(Direction.SOUTH, Direction.EAST),
    SOUTH_WEST(Direction.SOUTH, Direction.WEST),
    WEST_EAST(Direction.WEST, Direction.EAST),
    NORTH_SOUTH(Direction.NORTH, Direction.SOUTH),
    GROUND;

    private final Set<Direction> directions;
    private final Map<Direction, Direction> directionMap;
    private final boolean north;

    Pipe() {
        this.directions = Set.of();
        this.directionMap = Map.of();
        this.north = false;
    }

    Pipe(final Direction from, final Direction to) {
        this.directions = Set.of(from, to);
        this.directionMap = Map.of(from, to, to, from);
        this.north = from == Direction.NORTH || to == Direction.NORTH;
    }

    public Direction next(final Direction from) {
        final var ret = directionMap.get(from);

        if (ret == null) {
            throw new IllegalArgumentException();
        }

        return ret;
    }

}
