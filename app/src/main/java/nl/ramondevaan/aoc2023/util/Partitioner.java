package nl.ramondevaan.aoc2023.util;

import java.util.List;

public interface Partitioner<T> {
    List<List<T>> partition(List<T> toPartition);
}
