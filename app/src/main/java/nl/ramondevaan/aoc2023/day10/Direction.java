package nl.ramondevaan.aoc2023.day10;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2023.util.Coordinate;

@RequiredArgsConstructor
public enum Direction {
    NORTH(-1, 0) {
        @Override
        public Direction opposite() {
            return SOUTH;
        }
    }, EAST(0, 1) {
        @Override
        public Direction opposite() {
            return WEST;
        }
    }, SOUTH(1, 0) {
        @Override
        public Direction opposite() {
            return NORTH;
        }
    }, WEST(0, -1) {
        @Override
        public Direction opposite() {
            return EAST;
        }
    };

    private final int rowDiff;
    private final int columnDiff;

    public abstract Direction opposite();

    public Coordinate apply(final Coordinate coordinate) {
        return Coordinate.of(coordinate.row() + rowDiff, coordinate.column() + columnDiff);
    }
}
