package nl.ramondevaan.aoc2023.day08;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class NodeMap {
    @Getter
    private final List<Instruction> instructions;
    @Getter
    private final List<Integer> endsWithA;
    @Getter
    private final List<Integer> endsWithZ;
    private final Map<String, Integer> nameToIdMap;
    private final String[] names;
    private final int[] left;
    private final int[] right;

    public int getId(final String name) {
        return Optional.ofNullable(nameToIdMap.get(name))
                .orElseThrow();
    }

    public int getLeftId(final int id) {
        return left[id];
    }

    public int getRightId(final int id) {
        return right[id];
    }

    public static Builder builder(final int nodes) {
        return new Builder(nodes);
    }

    @RequiredArgsConstructor
    public static class Builder {
        private final int nodes;
        private final List<Instruction> instructions;
        private final List<Integer> endsWithA;
        private final List<Integer> endsWithZ;
        private final Map<String, Integer> nameToIdMap;
        private final String[] names;
        private final int[] left;
        private final int[] right;
        private int current;

        public Builder(final int nodes) {
            this.nodes = nodes;
            this.instructions = new ArrayList<>();
            this.endsWithA = new ArrayList<>(nodes);
            this.endsWithZ = new ArrayList<>(nodes);
            this.nameToIdMap = new HashMap<>(nodes);
            this.names = new String[nodes];
            this.left = new int[nodes];
            this.right = new int[nodes];
        }

        public Builder add(final Instruction instruction) {
            instructions.add(instruction);
            return this;
        }

        public Builder add(final String name, final String left, final String right) {
            final var id = getId(name);
            this.left[id] = getId(left);
            this.right[id] = getId(right);
            return this;
        }

        private int getId(final String name) {
            return nameToIdMap.computeIfAbsent(name, key -> {
                final int ret = current++;

                names[ret] = name;
                if (name.endsWith("A")) {
                    endsWithA.add(ret);
                } else if (name.endsWith("Z")) {
                    endsWithZ.add(ret);
                }

                return ret;
            });
        }

        public NodeMap build() {
            final var endsWithASorted = endsWithA.stream().sorted().toList();
            final var endsWithZSorted = endsWithZ.stream().sorted().toList();

            return new NodeMap(List.copyOf(instructions), endsWithASorted, endsWithZSorted, Map.copyOf(nameToIdMap), names, left, right);
        }
    }
}
