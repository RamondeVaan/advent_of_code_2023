package nl.ramondevaan.aoc2023.day25;

import com.google.common.collect.ListMultimap;

import java.util.List;

public record Graph(List<String> vertices, ListMultimap<String, String> edges) {
}
