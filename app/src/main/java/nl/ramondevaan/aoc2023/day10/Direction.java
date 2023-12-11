package nl.ramondevaan.aoc2023.day10;

import nl.ramondevaan.aoc2023.util.Coordinate;

public enum Direction {
    NORTH {
        @Override
        public Direction opposite() {
            return SOUTH;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row() - 1, coordinate.column());
        }
    }, EAST {
        @Override
        public Direction opposite() {
            return WEST;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row(), coordinate.column() + 1);
        }
    }, SOUTH {
        @Override
        public Direction opposite() {
            return NORTH;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row() + 1, coordinate.column());
        }
    }, WEST {
        @Override
        public Direction opposite() {
            return EAST;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row(), coordinate.column() - 1);
        }
    }, NORTH_EAST {
        @Override
        public Direction opposite() {
            return SOUTH_WEST;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row() - 1, coordinate.column() + 1);
        }
    }, SOUTH_EAST {
        @Override
        public Direction opposite() {
            return NORTH_WEST;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row() + 1, coordinate.column() + 1);
        }
    }, SOUTH_WEST {
        @Override
        public Direction opposite() {
            return NORTH_EAST;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row() + 1, coordinate.column() - 1);
        }
    }, NORTH_WEST {
        @Override
        public Direction opposite() {
            return SOUTH_EAST;
        }

        @Override
        public Coordinate apply(final Coordinate coordinate) {
            return Coordinate.of(coordinate.row() - 1, coordinate.column() - 1);
        }
    };

    public abstract Direction opposite();

    public abstract Coordinate apply(final Coordinate coordinate);
}
