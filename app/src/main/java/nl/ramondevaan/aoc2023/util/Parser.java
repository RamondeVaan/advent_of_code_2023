package nl.ramondevaan.aoc2023.util;

public interface Parser<T, U> {
    U parse(T toParse);
}
