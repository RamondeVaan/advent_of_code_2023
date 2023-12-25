package nl.ramondevaan.aoc2023.day24;

import com.microsoft.z3.Context;
import com.microsoft.z3.Status;
import nl.ramondevaan.aoc2023.util.CombinatoricsUtils;

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
                .filter(pair -> futureIntersectionBetweenBox(hailstones.get(pair.left()), hailstones.get(pair.right())))
                .count();
    }

    public long solve2() {
        try(final var context = new Context()) {
            final var solver = context.mkSolver();
            final var rx = context.mkRealConst("rx");
            final var ry = context.mkRealConst("ry");
            final var rz = context.mkRealConst("rz");
            final var rdx = context.mkRealConst("rdx");
            final var rdy = context.mkRealConst("rdy");
            final var rdz = context.mkRealConst("rdz");

            for (int i = 0; i < 3; i++) {
                final var hailstone = hailstones.get(i);
                final var x = context.mkReal(hailstone.x());
                final var y = context.mkReal(hailstone.y());
                final var z = context.mkReal(hailstone.z());
                final var dx = context.mkReal(hailstone.dx());
                final var dy = context.mkReal(hailstone.dy());
                final var dz = context.mkReal(hailstone.dz());
                final var t = context.mkRealConst(String.format("t%d", i));
                solver.add(context.mkEq(context.mkAdd(rx, context.mkMul(rdx, t)), context.mkAdd(x, context.mkMul(dx, t))));
                solver.add(context.mkEq(context.mkAdd(ry, context.mkMul(rdy, t)), context.mkAdd(y, context.mkMul(dy, t))));
                solver.add(context.mkEq(context.mkAdd(rz, context.mkMul(rdz, t)), context.mkAdd(z, context.mkMul(dz, t))));
            }
            if (solver.check() != Status.SATISFIABLE) {
                throw new IllegalStateException();
            }

            final var model = solver.getModel();
            final var x = Long.valueOf(model.eval(rx, false).toString());
            final var y = Long.valueOf(model.eval(ry, false).toString());
            final var z = Long.valueOf(model.eval(rz, false).toString());

            return x + y + z;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean futureIntersectionBetweenBox(final Hailstone h1, final Hailstone h2) {
        final var n1 = StrictMath.sqrt(h1.dx() * h1.dx() + h1.dy() * h1.dy());
        final var n2 = StrictMath.sqrt(h2.dx() * h2.dx() + h2.dy() * h2.dy());

        final var d1x = n1 * h1.dx();
        final var d1y = n1 * h1.dy();
        final var d2x = n2 * h2.dx();
        final var d2y = n2 * h2.dy();

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
