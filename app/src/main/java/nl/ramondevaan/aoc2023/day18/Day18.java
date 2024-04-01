package nl.ramondevaan.aoc2023.day18;

import java.util.List;

public class Day18 {

    private final List<Instruction> digPlan;
    private final List<Instruction> actualDigPlan;

    public Day18(final List<String> lines) {
        final var parser = new InstructionParser();
        final var actualParser = new ActualInstructionParser();
        this.digPlan = lines.stream().map(parser::parse).toList();
        this.actualDigPlan = digPlan.stream().map(actualParser::parse).toList();
    }

    public long solve1() {
        return solve(digPlan);
    }

    public long solve2() {
        return solve(actualDigPlan);
    }

    private static long solve(final List<Instruction> instructions) {
        var areaTimesTwo = 0L;
        long xCurrent = 0L, xNew;
        long yCurrent = 0L, yNew;

        for (final var instruction : instructions) {
            xNew = xCurrent + instruction.direction().getColumnDiff() * (long) instruction.meters();
            yNew = yCurrent + instruction.direction().getRowDiff() * (long) instruction.meters();
            areaTimesTwo += xCurrent * yNew - xNew * yCurrent + instruction.meters();
            xCurrent = xNew;
            yCurrent = yNew;
        }

        return areaTimesTwo / 2L + 1;
    }
}
