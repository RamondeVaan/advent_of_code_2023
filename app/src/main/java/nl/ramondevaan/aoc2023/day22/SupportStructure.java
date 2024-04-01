package nl.ramondevaan.aoc2023.day22;

import com.google.common.collect.SetMultimap;

public record SupportStructure(SetMultimap<Integer, Integer> supports, SetMultimap<Integer, Integer> supportedBy) {
}
