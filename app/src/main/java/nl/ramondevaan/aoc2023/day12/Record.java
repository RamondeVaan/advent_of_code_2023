package nl.ramondevaan.aoc2023.day12;

import java.util.ArrayList;
import java.util.List;

import static nl.ramondevaan.aoc2023.day12.SpringCondition.*;

public record Record(List<SpringCondition> springConditions, List<Integer> damagedGroups) {

    public Record skipOneSpringCondition() {
        return new Record(springConditions.subList(1, springConditions.size()), damagedGroups);
    }

    public Record next(final int skipSpringConditions) {
        final var newSpringConditions = springConditions.subList(skipSpringConditions, springConditions.size());
        final var newDamagedGroups = damagedGroups.subList(1, damagedGroups.size());
        return new Record(newSpringConditions, newDamagedGroups);
    }

    public boolean hasDamagedSpring() {
        return springConditions.contains(DAMAGED);
    }

    public boolean hasOperational(final int groupSize) {
        final var to = Math.min(groupSize, springConditions.size());

        for (int i = 0; i < to; i++) {
            final var springCondition = springConditions.get(i);
            if (springCondition == OPERATIONAL) {
                return true;
            }
        }

        return false;
    }

    public SpringCondition firstSpringCondition() {
        return springConditions.getFirst();
    }

    public Record unfold(final int times) {
        final var newSpringConditions = new ArrayList<SpringCondition>(springConditions.size() * times + times - 1);
        newSpringConditions.addAll(springConditions);

        final var newDamagedGroups = new ArrayList<Integer>(damagedGroups.size() * times);
        newDamagedGroups.addAll(damagedGroups);

        for (int i = 1; i < times; i++) {
            newSpringConditions.add(UNKNOWN);
            newSpringConditions.addAll(springConditions);
            newDamagedGroups.addAll(damagedGroups);
        }

        return new Record(List.copyOf(newSpringConditions), List.copyOf(newDamagedGroups));
    }
}
