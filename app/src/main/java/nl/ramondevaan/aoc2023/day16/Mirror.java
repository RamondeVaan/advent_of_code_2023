package nl.ramondevaan.aoc2023.day16;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2023.util.Direction;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Mirror {
    NORTH_EAST {
        @Override
        public Set<Direction> next(final Direction direction) {
            return switch (direction) {
                case NORTH -> WEST;
                case EAST -> SOUTH;
                case SOUTH -> EAST;
                case WEST -> NORTH;
            };
        }
    },
    NORTH_WEST {
        @Override
        public Set<Direction> next(final Direction direction) {
            return switch (direction) {
                case NORTH -> EAST;
                case EAST -> NORTH;
                case SOUTH -> WEST;
                case WEST -> SOUTH;
            };
        }
    },
    WEST_EAST {
        @Override
        public Set<Direction> next(final Direction direction) {
            return switch (direction) {
                case EAST -> EAST;
                case WEST -> WEST;
                case NORTH, SOUTH -> EAST_AND_WEST;
            };
        }
    },
    NORTH_SOUTH {
        @Override
        public Set<Direction> next(final Direction direction) {
            return switch (direction) {
                case NORTH -> NORTH;
                case SOUTH -> SOUTH;
                case EAST, WEST -> NORTH_AND_SOUTH;
            };
        }
    },
    EMPTY_SPACE {
        @Override
        public Set<Direction> next(final Direction direction) {
            return Set.of(direction);
        }
    };

    public abstract Set<Direction> next(final Direction direction);

    private final static Set<Direction> NORTH = Set.of(Direction.NORTH);
    private final static Set<Direction> EAST = Set.of(Direction.EAST);
    private final static Set<Direction> SOUTH = Set.of(Direction.SOUTH);
    private final static Set<Direction> WEST = Set.of(Direction.WEST);

    private final static Set<Direction> NORTH_AND_SOUTH = Set.of(Direction.NORTH, Direction.SOUTH);
    private final static Set<Direction> EAST_AND_WEST = Set.of(Direction.EAST, Direction.WEST);

}
