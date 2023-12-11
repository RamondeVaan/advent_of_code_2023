package nl.ramondevaan.aoc2023.day10;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2023.util.Coordinate;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PipeMap {
    @Getter
    private final Coordinate start;
    @Getter
    private final int rows;
    @Getter
    private final int columns;
    private final Pipe[][] pipes;

    public Pipe get(final Coordinate coordinate) {
        return pipes[coordinate.row()][coordinate.column()];
    }

    public static Builder builder(final int row, final int column) {
        return new Builder(row, column);
    }

    public static class Builder {
        private Pipe[][] pipes;
        @Getter
        private Coordinate start;

        public Builder(final int rows, final int columns) {
            this.pipes = new Pipe[rows][columns];
        }

        public Builder set(final int row, final int column, final Pipe pipe) {
            pipes[row][column] = pipe;
            return this;
        }

        public Builder setStart(final int row, final int column) {
            this.start = Coordinate.of(row, column);
            return this;
        }

        public Pipe get(final int row, final int column) {
            return pipes[row][column];
        }

        public boolean isInRange(final int row, final int column) {
            return row >= 0 && row < pipes.length && column >= 0 && column < pipes[0].length;
        }

        public PipeMap build() {
            final var ret = new PipeMap(start, pipes.length, pipes[0].length, pipes);
            this.pipes = null;
            return ret;
        }
    }
}
