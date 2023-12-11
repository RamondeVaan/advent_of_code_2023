package nl.ramondevaan.aoc2023.day11;

import lombok.Getter;
import nl.ramondevaan.aoc2023.util.Coordinate;

import java.util.List;

@Getter
public class GalaxyImage {

    private final int rows;
    private final int columns;
    private final List<Coordinate> galaxies;
    private final List<Integer> emptyRows;
    private final List<Integer> emptyColumns;

    public GalaxyImage(int rows, int columns, List<Coordinate> galaxies, List<Integer> emptyRows, List<Integer> emptyColumns) {
        this.rows = rows;
        this.columns = columns;
        this.galaxies = List.copyOf(galaxies);
        this.emptyRows = List.copyOf(emptyRows);
        this.emptyColumns = List.copyOf(emptyColumns);
    }
}
