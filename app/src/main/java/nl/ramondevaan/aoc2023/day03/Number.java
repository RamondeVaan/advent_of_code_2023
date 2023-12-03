package nl.ramondevaan.aoc2023.day03;

import nl.ramondevaan.aoc2023.util.Coordinate;

import java.util.List;

public record Number (long value, List<Coordinate> locations) {
}
