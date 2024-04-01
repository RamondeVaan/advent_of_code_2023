package nl.ramondevaan.aoc2023.day20;

import java.util.Map;

public record Iteration(Map<Module, Memory.Operations> memory, int lowPulses, int highPulses) {
}
