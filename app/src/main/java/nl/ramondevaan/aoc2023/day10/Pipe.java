package nl.ramondevaan.aoc2023.day10;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum Pipe {
    NORTH_EAST(Set.of(Direction.NORTH, Direction.EAST)) {
        private final static Set<Direction> INSIDE = Set.of(Direction.NORTH_EAST);
        private final static Set<Direction> OUTSIDE = Set.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH);

        @Override
        public Direction next(final Direction from) {
            return switch (from) {
                case EAST -> Direction.NORTH;
                case NORTH -> Direction.EAST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            return switch (from) {
                case EAST -> INSIDE;
                case NORTH -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            return switch (from) {
                case NORTH -> INSIDE;
                case EAST -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return switch (from) {
                case NORTH -> false;
                case EAST -> true;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return switch (from) {
                case NORTH -> true;
                case EAST -> false;
                default -> throw new IllegalArgumentException();
            };
        }
    },
    NORTH_WEST(Set.of(Direction.NORTH, Direction.WEST)) {
        private final static Set<Direction> INSIDE = Set.of(Direction.NORTH_WEST);
        private final static Set<Direction> OUTSIDE = Set.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH);

        @Override
        public Direction next(final Direction from) {
            return switch (from) {
                case WEST -> Direction.NORTH;
                case NORTH -> Direction.WEST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            return switch (from) {
                case NORTH -> INSIDE;
                case WEST -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            return switch (from) {
                case WEST -> INSIDE;
                case NORTH -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return switch (from) {
                case NORTH -> true;
                case WEST -> false;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return switch (from) {
                case NORTH -> false;
                case WEST -> true;
                default -> throw new IllegalArgumentException();
            };
        }
    },
    SOUTH_EAST(Set.of(Direction.SOUTH, Direction.EAST)) {
        private final static Set<Direction> INSIDE = Set.of(Direction.SOUTH_EAST);
        private final static Set<Direction> OUTSIDE = Set.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH);

        @Override
        public Direction next(final Direction from) {
            return switch (from) {
                case EAST -> Direction.SOUTH;
                case SOUTH -> Direction.EAST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            return switch (from) {
                case SOUTH -> INSIDE;
                case EAST -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            return switch (from) {
                case EAST -> INSIDE;
                case SOUTH -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return switch (from) {
                case SOUTH -> true;
                case EAST -> false;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return switch (from) {
                case SOUTH -> false;
                case EAST -> true;
                default -> throw new IllegalArgumentException();
            };
        }
    },
    SOUTH_WEST(Set.of(Direction.SOUTH, Direction.WEST)) {
        private final static Set<Direction> INSIDE = Set.of(Direction.SOUTH_WEST);
        private final static Set<Direction> OUTSIDE = Set.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH);

        @Override
        public Direction next(final Direction from) {
            return switch (from) {
                case WEST -> Direction.SOUTH;
                case SOUTH -> Direction.WEST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            return switch (from) {
                case WEST -> INSIDE;
                case SOUTH -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            return switch (from) {
                case SOUTH -> INSIDE;
                case WEST -> OUTSIDE;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return switch (from) {
                case SOUTH -> false;
                case WEST -> true;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return switch (from) {
                case SOUTH -> true;
                case WEST -> false;
                default -> throw new IllegalArgumentException();
            };
        }
    },
    WEST_EAST(Set.of(Direction.WEST, Direction.EAST)) {
        private final static Set<Direction> DIR_NORTH = Set.of(Direction.NORTH);
        private final static Set<Direction> DIR_SOUTH = Set.of(Direction.SOUTH);

        @Override
        public Direction next(final Direction from) {
            return switch (from) {
                case EAST -> Direction.WEST;
                case WEST -> Direction.EAST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            return switch (from) {
                case WEST -> DIR_SOUTH;
                case EAST -> DIR_NORTH;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            return switch (from) {
                case WEST -> DIR_NORTH;
                case EAST -> DIR_SOUTH;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return false;
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return false;
        }
    },
    NORTH_SOUTH(Set.of(Direction.NORTH, Direction.SOUTH)) {
        private final static Set<Direction> DIR_WEST = Set.of(Direction.WEST);
        private final static Set<Direction> DIR_EAST = Set.of(Direction.EAST);

        @Override
        public Direction next(final Direction from) {
            return switch (from) {
                case SOUTH -> Direction.NORTH;
                case NORTH -> Direction.SOUTH;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            return switch (from) {
                case SOUTH -> DIR_EAST;
                case NORTH -> DIR_WEST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            return switch (from) {
                case NORTH -> DIR_EAST;
                case SOUTH -> DIR_WEST;
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return false;
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return false;
        }
    },
    GROUND(Set.of()) {
        @Override
        public Direction next(final Direction from) {
            throw new IllegalArgumentException();
        }

        @Override
        public Set<Direction> getRightAdjacent(final Direction from) {
            throw new IllegalArgumentException();
        }

        @Override
        public Set<Direction> getLeftAdjacent(final Direction from) {
            throw new IllegalArgumentException();
        }

        @Override
        public boolean isRightCorner(final Direction from) {
            return false;
        }

        @Override
        public boolean isLeftCorner(final Direction from) {
            return false;
        }
    };

    private final Set<Direction> directions;

    public abstract Direction next(final Direction from);

    public abstract Set<Direction> getRightAdjacent(final Direction from);

    public abstract Set<Direction> getLeftAdjacent(final Direction from);

    public abstract boolean isRightCorner(final Direction from);

    public abstract boolean isLeftCorner(final Direction from);

}
