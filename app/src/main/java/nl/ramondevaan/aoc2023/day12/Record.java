package nl.ramondevaan.aoc2023.day12;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.UNKNOWN;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Record {

    private final SpringCondition[] springConditions;
    private final List<Integer> damagedGroups;

    public List<Integer> damagedGroups() {
        return damagedGroups;
    }

    public SpringCondition getSpringCondition(final int index) {
        return springConditions[index];
    }

    public int numberOfSpringConditions() {
        return springConditions.length;
    }

    public Record unfold(final int times) {
        final var newSpringConditions = new SpringCondition[springConditions.length * times + times - 1];

        System.arraycopy(springConditions, 0, newSpringConditions, 0, springConditions.length);
        var dest = springConditions.length;

        do {
            newSpringConditions[dest++] = UNKNOWN;
            System.arraycopy(springConditions, 0, newSpringConditions, dest, springConditions.length);
            dest += springConditions.length;
        } while (dest < newSpringConditions.length);

        final var newDamagedGroups = new ArrayList<Integer>(damagedGroups.size() * times);
        newDamagedGroups.addAll(damagedGroups);

        for (int i = 1; i < times; i++) {
            newDamagedGroups.addAll(damagedGroups);
        }

        return new Record(newSpringConditions, List.copyOf(newDamagedGroups));
    }

    public static Builder builder(final int numberOfSpringConditions) {
        return new Builder(numberOfSpringConditions);
    }

    public static class Builder {

        private SpringCondition[] springConditions;
        private List<Integer> damagedGroups;

        public Builder(final int numberOfSpringConditions) {
            this.springConditions = new SpringCondition[numberOfSpringConditions];
            this.damagedGroups = new ArrayList<>();
        }

        public Builder set(final int index, final SpringCondition springCondition) {
            springConditions[index] = springCondition;
            return this;
        }

        public Builder add(final int damagedGroup) {
            damagedGroups.add(damagedGroup);
            return this;
        }

        public Record build() {
            return new Record(springConditions, damagedGroups);
        }
    }
}
