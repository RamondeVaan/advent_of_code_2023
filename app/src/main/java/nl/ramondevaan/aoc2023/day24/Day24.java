package nl.ramondevaan.aoc2023.day24;

import nl.ramondevaan.aoc2023.util.CombinatoricsUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;

import java.util.Arrays;
import java.util.List;

public class Day24 {
    private static final double TOLERANCE = 1.0e-10;
    private static final double BOX_FROM = 200000000000000L;
    private static final double BOX_TO = 400000000000000L;

    private final List<Hailstone> hailstones;

    public Day24(final List<String> lines) {
        final var parser = new HailstoneParser();
        this.hailstones = lines.stream().map(parser::parse).toList();
    }

    public long solve1() {
        return CombinatoricsUtils.pairs(hailstones.size())
                .map(pair -> Pair.of(hailstones.get(pair.left()), hailstones.get(pair.right())))
                .filter(pair -> futureIntersectionBetweenBox(pair.getLeft(), pair.getRight()))
                .count();
    }

    public long solve2() {
        final var h0 = Hailstone3D.of(hailstones.get(0));
        final var h1 = Hailstone3D.of(hailstones.get(1));
        final var h2 = Hailstone3D.of(hailstones.get(2));

        final var c01 = h0.position().subtract(h1.position()).crossProduct(h0.velocity().subtract(h1.velocity()));
        final var c02 = h0.position().subtract(h2.position()).crossProduct(h0.velocity().subtract(h2.velocity()));
        final var c12 = h1.position().subtract(h2.position()).crossProduct(h1.velocity().subtract(h2.velocity()));

        final var a = MatrixUtils.createRealMatrix(new double[][]{c01.toArray(), c02.toArray(), c12.toArray()});

        final var determinant = (new LUDecomposition(a)).getDeterminant();

        if (Math.abs(determinant) < TOLERANCE) {
            throw new IllegalArgumentException();
        }

        final var s01 = h0.position().subtract(h1.position()).dotProduct(h0.velocity().crossProduct(h1.velocity()));
        final var s02 = h0.position().subtract(h2.position()).dotProduct(h0.velocity().crossProduct(h2.velocity()));
        final var s12 = h1.position().subtract(h2.position()).dotProduct(h1.velocity().crossProduct(h2.velocity()));
        final var rhs = MatrixUtils.createColumnRealMatrix(new double[] {s01, s02, s12});
        final var aInv = MatrixUtils.inverse(a);
        final var w = new Vector3D(aInv.multiply(rhs).getColumn(0));
        final var wr = new Vector3D(Math.round(w.getX()), Math.round(w.getY()), Math.round(w.getZ()));
        final var h0v = h0.velocity().subtract(wr);
        final var h1v = wr.subtract(h1.velocity());
        final var b = MatrixUtils.createRealMatrix(new double[][] {{h0v.getX(), h1v.getX()}, {h0v.getY(), h1v.getY()}});
        final var bInv = MatrixUtils.inverse(b);
        final var diff = h1.position().subtract(h0.position());
        final var rhs2 = MatrixUtils.createColumnRealMatrix(new double[] {diff.getX(), diff.getY()});
        final var t = bInv.multiply(rhs2).getEntry(0, 0);
        final var v = h0v.scalarMultiply(t).add(h0.position());

        return Arrays.stream(v.toArray()).mapToLong(Math::round).sum();
    }

    private boolean futureIntersectionBetweenBox(final Hailstone h1, final Hailstone h2) {
        final var n1 = StrictMath.sqrt(h1.dx() * h1.dx() + h1.dy() * h1.dy());
        final var n2 = StrictMath.sqrt(h2.dx() * h2.dx() + h2.dy() * h2.dy());

        final var d1x = h1.dx() / n1;
        final var d1y = h1.dy() / n1;
        final var d2x = h2.dx() / n2;
        final var d2y = h2.dy() / n2;

        final var det = d2x * d1y - d2y * d1x;

        if (Math.abs(det) < TOLERANCE) {
            return false;
        }

        final var dx = h2.x() - h1.x();
        final var dy = h2.y() - h1.y();

        final var u = (dy * d2x - dx * d2y) / det;
        final var v = (dy * d1x - dx * d1y) / det;

        if (u < 0 || v < 0) {
            return false;
        }

        final var p1x = d1x * u + h1.x();
        final var p1y = d1y * u + h1.y();

        return p1x >= BOX_FROM && p1x <= BOX_TO && p1y >= BOX_FROM && p1y <= BOX_TO;
    }
}
