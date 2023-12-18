package nl.ramondevaan.aoc2023.day18;

import nl.ramondevaan.aoc2023.util.Direction;
import nl.ramondevaan.aoc2023.util.Parser;

public class ActualInstructionParser implements Parser<Instruction, Instruction> {
    @Override
    public Instruction parse(final Instruction toParse) {
        final var color = toParse.color();
        var direction = switch (color & 0xF) {
            case 0 -> Direction.EAST;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.WEST;
            case 3 -> Direction.NORTH;
            default -> throw new IllegalArgumentException();
        };
        var meters = color >> 4;
        return new Instruction(direction, meters, 0);
    }
}
