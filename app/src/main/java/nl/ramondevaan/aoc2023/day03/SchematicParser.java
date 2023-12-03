package nl.ramondevaan.aoc2023.day03;

import nl.ramondevaan.aoc2023.util.Coordinate;
import nl.ramondevaan.aoc2023.util.IntMap;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SchematicParser implements Parser<List<String>, Schematic> {
    @Override
    public Schematic parse(final List<String> toParse) {
        final var rows = toParse.size();
        final var columns = toParse.get(0).length();
        final var symbolLocationList = new ArrayList<ImmutablePair<Character, Coordinate>>();
        final var numbers = new ArrayList<Number>();

        for (int row = 0; row < toParse.size(); row++) {
            final var line = toParse.get(row);
            final var parser = new StringIteratorParser(line);
            do {
                final Character current = parser.current();

                switch (current) {
                    case Character c when Character.isDigit(c) -> numbers.add(parseNumber(parser, row));
                    case '.' -> parser.consume();
                    default -> {
                        symbolLocationList.add(ImmutablePair.of(current, Coordinate.of(row, parser.getIndex())));
                        parser.consume();
                    }
                }
            } while (parser.hasNext());
        }

        final var symbols = symbolLocationList.stream()
                .collect(Collectors.groupingBy(ImmutablePair::getLeft,
                        Collectors.mapping(ImmutablePair::getRight, Collectors.toUnmodifiableList())));

        return new Schematic(Map.copyOf(symbols), List.copyOf(numbers), buildNumberIndexMap(rows, columns, numbers));
    }

    private Number parseNumber(final StringIteratorParser parser, int row) {
        final var columnStart = parser.getIndex();
        final var value = parser.parseLong();
        final var columnEndExclusive = parser.getIndex();
        final var locations = IntStream.range(columnStart, columnEndExclusive)
                .mapToObj(column -> Coordinate.of(row, column))
                .toList();

        return new Number(value, locations);
    }

    private IntMap buildNumberIndexMap(final int rows, final int columns, final List<Number> numbers) {
        final var builder = IntMap.builder(rows, columns).fill(-1);

        IntStream.range(0, numbers.size()).forEach(index -> numbers.get(index).locations()
                .forEach(coordinate -> builder.set(coordinate.row(), coordinate.column(), index)));

        return builder.build();
    }
}
