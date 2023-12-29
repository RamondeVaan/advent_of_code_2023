package nl.ramondevaan.aoc2023.day24;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

public record Hailstone3D(Vector3D position, Vector3D velocity) {

    public static Hailstone3D of(final Hailstone hailstone) {
        return new Hailstone3D(new Vector3D(hailstone.x(), hailstone.y(), hailstone.z()),
                (new Vector3D(hailstone.dx(), hailstone.dy(), hailstone.dz())));
    }
}
