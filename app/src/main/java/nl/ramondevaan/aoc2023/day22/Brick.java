package nl.ramondevaan.aoc2023.day22;

import nl.ramondevaan.aoc2023.util.Position;

public record Brick(int id, Position3d from, Position3d to) {

    public int lowestZ() {
        return from.z();
    }

    public int height() {
        return to.z() - from.z() + 1;
    }

    public Position[] groundPlane() {
        final int size = (to.x() - from.x() + 1) * (to.y() - from.y() + 1);
        final var ret = new Position[size];

        int index = 0;
        for (int x = from.x(); x <= to.x(); x++) {
            for (int y = from.y(); y <= to.y(); y++) {
                ret[index++] = new Position(x, y);
            }
        }

        return ret;
    }
}
