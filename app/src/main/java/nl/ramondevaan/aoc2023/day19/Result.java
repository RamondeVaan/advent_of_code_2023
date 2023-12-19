package nl.ramondevaan.aoc2023.day19;

public sealed interface Result permits FinishedResult, ForwardResult, NotApplicableResult {
}
