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
    }, EAST(0b0010, 0, 1) {
        @Override
        public Direction opposite() {
            return WEST;
        }
    }, SOUTH(0b0100, 1, 0) {
        @Override
        public Direction opposite() {
            return NORTH;
        }
    }, WEST(0b1000, 0, -1) {
        @Override
        public Direction opposite() {
            return EAST;
        }
    };

    private final int flag;
    private final int rowDiff;
    private final int columnDiff;

    public abstract Direction opposite();

    public Coordinate apply(final Coordinate coordinate) {
        return Coordinate.of(coordinate.row() + rowDiff, coordinate.column() + columnDiff);
    }
}
