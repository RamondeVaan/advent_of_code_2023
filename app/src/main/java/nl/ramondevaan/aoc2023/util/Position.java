package nl.ramondevaan.aoc2023.util;

import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class Position {
    public final int x;
    public final int y;

    public Position add(Position position) {
        return new Position(this.x + position.x, this.y + position.y);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
