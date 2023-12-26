package nl.ramondevaan.aoc2023.day25;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.SetMultimap;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Day25 {

    private final Random random;
    private final SetMultimap<String, String> connections;

    public Day25(final List<String> lines) {
        final var parser = new ConnectionsParser();
        this.connections = parser.parse(lines);
        this.random = new Random(connections.hashCode());
    }

    public long solve1() {
        ListMultimap<String, String> map;
        do {
            map = contract(ArrayListMultimap.create(connections), 2);
        } while (map.values().size() != 6);

        return map.keySet().stream()
                .mapToLong(key -> StringUtils.countMatches(key, ',') + 1)
                .reduce((left, right) -> left * right)
                .orElseThrow();
    }

    private ListMultimap<String, String> contract(final ListMultimap<String, String> connections, final int t) {
        final var vertices = new ArrayList<>(connections.keySet());
        final var edges = ArrayListMultimap.create(connections);

        while (vertices.size() > t) {
            final var vertex1 = vertices.get(random.nextInt(vertices.size()));
            final var otherVertices = edges.get(vertex1);
            final var vertex2 = otherVertices.get(random.nextInt(otherVertices.size()));
            vertices.remove(vertex1);
            vertices.remove(vertex2);
            final var newVertex = vertex1 + "," + vertex2;
            vertices.add(newVertex);
            while (edges.remove(vertex1, vertex2)) ;
            while (edges.remove(vertex2, vertex1)) ;
            for (final var s : edges.removeAll(vertex1)) {
                while (edges.remove(s, vertex1)) {
                    edges.put(s, newVertex);
                    edges.put(newVertex, s);
                }
            }
            for (final var s : edges.removeAll(vertex2)) {
                while (edges.remove(s, vertex2)) {
                    edges.put(s, newVertex);
                    edges.put(newVertex, s);
                }
            }
        }

        return edges;
    }
}
