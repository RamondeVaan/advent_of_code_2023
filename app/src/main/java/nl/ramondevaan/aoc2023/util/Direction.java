package nl.ramondevaan.aoc2023.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Direction {
    NORTH(0b0001, -1, 0) {
        @Override
        public Direction opposite() {
            return SOUTH;
        }

        @Override
        public Direction left() {
            return Direction.WEST;
        }

        @Override
        public Direction right() {
            return Direction.EAST;
        }
    }, EAST(0b0010, 0, 1) {
        @Override
        public Direction opposite() {
            return WEST;
        }

        @Override
        public Direction left() {
            return Direction.NORTH;
        }

        @Override
        public Direction right() {
            return Direction.SOUTH;
        }
    }, SOUTH(0b0100, 1, 0) {
        @Override
        public Direction opposite() {
            return NORTH;
        }

        @Override
        public Direction left() {
            return Direction.EAST;
        }

        @Override
        public Direction right() {
            return Direction.WEST;
        }
    }, WEST(0b1000, 0, -1) {
        @Override
        public Direction opposite() {
            return EAST;
        }

        @Override
        public Direction left() {
            return Direction.SOUTH;
        }

        @Override
        public Direction right() {
            return Direction.NORTH;
        }
    };

    private final int flag;
    private final int rowDiff;
    private final int columnDiff;

    public abstract Direction opposite();
    public abstract Direction left();
    public abstract Direction right();

    public Coordinate apply(final Coordinate coordinate) {
        return Coordinate.of(coordinate.row() + rowDiff, coordinate.column() + columnDiff);
    }
}
