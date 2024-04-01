package nl.ramondevaan.aoc2023.day04;

import java.util.List;
import java.util.Set;

public record Card(Set<Integer> winningNumbers, List<Integer> numbers) {
}
