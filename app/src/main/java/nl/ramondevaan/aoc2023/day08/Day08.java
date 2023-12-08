package nl.ramondevaan.aoc2023.day08;

import com.google.common.collect.Iterators;
import org.apache.commons.math3.util.ArithmeticUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Day08 {

    private final NodeMap nodeMap;

    public Day08(final List<String> lines) {
        final var parser = new NodeMapParser();
        this.nodeMap = parser.parse(lines);
    }

    public long solve1() {
        final var goal = nodeMap.getId("ZZZ");
        var start = nodeMap.getId("AAA");

        final IntPredicate stoppingCondition = (id) -> id == goal;
        return minStepsTo(start, stoppingCondition);
    }

    public long solve2() {
        final IntPredicate stoppingCondition = (id) -> Collections.binarySearch(nodeMap.getEndsWithZ(), id) >= 0;
        return nodeMap.getEndsWithA().stream()
                .mapToLong(id -> minStepsTo(id, stoppingCondition))
                .reduce(ArithmeticUtils::lcm)
                .orElseThrow();
    }

    private long minStepsTo(final int id, IntPredicate stoppingCondition) {
        final var instructions = Iterators.cycle(nodeMap.getInstructions());
        final IntUnaryOperator next = current -> switch (instructions.next()) {
            case RIGHT -> nodeMap.getRightId(current);
            case LEFT -> nodeMap.getLeftId(current);
        };

        return IntStream.iterate(id, stoppingCondition.negate(), next).count();
    }
}
