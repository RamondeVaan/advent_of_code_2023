package nl.ramondevaan.aoc2023.day16;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Mirrors {
    public final int rows;
    public final int columns;
    private final Mirror[][] mirrors;

    public Mirror get(final int row, final int column) {
        return mirrors[row][column];
    }

    public static Builder builder(final int rows, final int columns) {
        return new Builder(rows, columns);
    }

    public static class Builder {

        public final int rows;
        public final int columns;
        private Mirror[][] mirrors;

        public Builder(final int rows, final int columns) {
            this.rows = rows;
            this.columns = columns;
            this.mirrors = new Mirror[rows][columns];
        }

        public Builder set(final int row, final int column, final Mirror mirror) {
            mirrors[row][column] = mirror;
            return this;
        }

        public Mirrors build() {
            final var ret = new Mirrors(rows, columns, mirrors);
            this.mirrors = null;
            return ret;
        }
    }
}
