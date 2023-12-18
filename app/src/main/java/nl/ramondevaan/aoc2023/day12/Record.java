package nl.ramondevaan.aoc2023.day12;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.UNKNOWN;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Record {

    private final SpringCondition[] springConditions;
    private final int[] damagedGroups;

    public SpringCondition getSpringCondition(final int index) {
        return springConditions[index];
    }

    public int getDamagedGroup(final int index) {
        return damagedGroups[index];
    }

    public int numberOfSpringConditions() {
        return springConditions.length;
    }

    public int numberOfDamagedGroups() {
        return damagedGroups.length;
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

        final var newDamagedGroups = new int[damagedGroups.length * times];

        for (int i = 0; i < times; i++) {
            System.arraycopy(damagedGroups, 0, newDamagedGroups, damagedGroups.length * i, damagedGroups.length);
        }

        return new Record(newSpringConditions, newDamagedGroups);
    }

    public static Builder builder(final int numberOfSpringConditions, final int numberOfDamagedGroups) {
        return new Builder(numberOfSpringConditions, numberOfDamagedGroups);
    }

    public static class Builder {

        private SpringCondition[] springConditions;
        private int[] damagedGroups;

        public Builder(final int numberOfSpringConditions, final int numberOfDamagedGroups) {
            this.springConditions = new SpringCondition[numberOfSpringConditions];
            this.damagedGroups = new int[numberOfDamagedGroups];
        }

        public Builder set(final int index, final SpringCondition springCondition) {
            springConditions[index] = springCondition;
            return this;
        }

        public Builder set(final int index, final int damagedGroup) {
            damagedGroups[index] = damagedGroup;
            return this;
        }

        public Record build() {
            final var ret = new Record(springConditions, damagedGroups);
            this.springConditions = null;
            this.damagedGroups = null;
            return ret;
        }
    }
}
