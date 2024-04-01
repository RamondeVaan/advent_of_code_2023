package nl.ramondevaan.aoc2023.day19;

import nl.ramondevaan.aoc2023.util.BlankStringPartitioner;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day19 {

    private final Map<String, List<Rule>> workflows;
    private final List<PartRating> partRatings;

    public Day19(final List<String> lines) {
        final var partitioner = new BlankStringPartitioner();
        final var workflowParser = new WorkflowParser();
        final var partRatingparser = new PartRatingParser();

        final var partitions = partitioner.partition(lines);
        this.workflows = partitions.getFirst().stream().map(workflowParser::parse)
                .collect(Collectors.toUnmodifiableMap(Workflow::name, Workflow::rules));
        this.partRatings = partitions.get(1).stream().map(partRatingparser::parse).toList();
    }

    public long solve1() {
        return partRatings.stream()
                .mapToLong(this::solve)
                .sum();
    }

    public long solve2() {
        return solve(new PartRatingRange(1, 4000));
    }

    private int solve(final PartRating partRating) {
        Result current = apply(partRating, "in");

        while (true) {
            switch (current) {
                case ForwardResult(String name):
                    current = apply(partRating, name);
                    break;
                case FinishedResult(boolean accepted):
                    return accepted ? partRating.sum() : 0;
                default:
                    throw new IllegalStateException("Unexpected value: " + current);
            }
        }
    }

    private Result apply(final PartRating partRating, final String workflow) {
        final var rules = workflows.get(workflow);

        for (final var rule : rules) {
            final var result = rule.apply(partRating);
            if (result instanceof NotApplicableResult) {
                continue;
            }
            return result;
        }

        throw new IllegalStateException();
    }

    private long solve(final PartRatingRange range) {
        var count = 0L;

        final var todo = new ArrayDeque<>(apply(range, "in"));

        RangeResult rangeResult;
        while ((rangeResult = todo.poll()) != null) {
            switch (rangeResult.result()) {
                case ForwardResult(String name) -> todo.addAll(apply(rangeResult.range(), name));
                case FinishedResult(boolean accepted) -> {
                    if (accepted) {
                        count += rangeResult.range().size();
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + rangeResult.result());
            }
        }

        return count;
    }

    private List<RangeResult> apply(final PartRatingRange range, final String workflow) {
        final var rules = workflows.get(workflow);
        final var result = new ArrayList<RangeResult>();

        var current = new ArrayList<PartRatingRange>();
        current.add(range);

        for (final var rule : rules) {
            final var next = new ArrayList<PartRatingRange>();
            for (final var partRatingRange : current) {
                final var rangeResults = rule.apply(partRatingRange);
                for (final var rangeResult : rangeResults) {
                    if (rangeResult.result() instanceof NotApplicableResult) {
                        if (!rangeResult.range().isEmpty()) {
                            next.add(rangeResult.range());
                        }
                    } else if (!rangeResult.range().isEmpty()) {
                        result.add(rangeResult);
                    }
                }
            }
            current = next;
        }

        return result;
    }
}
