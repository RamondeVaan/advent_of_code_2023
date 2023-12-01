package nl.ramondevaan.aoc2023.day01;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableMap;

@RequiredArgsConstructor
public class TextualNumbersParser implements Parser<URL, Map<String, Long>> {

    private final static char COMMA = ',';

    @Override
    public Map<String, Long> parse(URL url) {
        return getLines(url)
                .map(this::parseLine)
                .collect(toUnmodifiableMap(ImmutablePair::getLeft, ImmutablePair::getRight));
    }

    private Stream<String> getLines(URL url) {
        try {
            final var path = Path.of(Objects.requireNonNull(url).toURI());
            return Files.lines(path);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ImmutablePair<String, Long> parseLine(final String line) {
        final var parser = new StringIteratorParser(line);

        final var text = parser.parseString();
        parser.consume(COMMA);
        final var value = parser.parseLong();

        return ImmutablePair.of(text, value);
    }
}
