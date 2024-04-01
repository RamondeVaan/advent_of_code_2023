package nl.ramondevaan.aoc2023.day11;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GalaxyImageParser implements Parser<List<String>, GalaxyImage> {
    @Override
    public GalaxyImage parse(final List<String> toParse) {
        final var rows = toParse.size();
        final var columns = toParse.getFirst().length();
        final var galaxies = new ArrayList<Coordinate>();
        final var galaxyFoundByRow = new boolean[rows];
        final var galaxyFoundByColumn = new boolean[columns];

        for (int row = 0; row < rows; row++) {
            final var chars = toParse.get(row).toCharArray();
            for (int column = 0; column < columns; column++) {
                if (chars[column] == '#') {
                    galaxyFoundByRow[row] = true;
                    galaxyFoundByColumn[column] = true;
                    galaxies.add(Coordinate.of(row, column));
                }
            }
        }

        final var emptyRows = IntStream.range(0, rows).filter(i -> !galaxyFoundByRow[i]).boxed().toList();
        final var emptyColumns = IntStream.range(0, columns).filter(i -> !galaxyFoundByColumn[i]).boxed().toList();

        return new GalaxyImage(rows, columns, galaxies, emptyRows, emptyColumns);
    }
}
