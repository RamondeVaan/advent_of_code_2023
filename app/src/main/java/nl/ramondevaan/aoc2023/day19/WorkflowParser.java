package nl.ramondevaan.aoc2023.day19;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

import static nl.ramondevaan.aoc2023.day19.Category.*;

public class WorkflowParser implements Parser<String, Workflow> {
    @Override
    public Workflow parse(final String toParse) {
        final var parser = new StringIteratorParser(toParse);

        final var name = parser.parseString();
        final var rules = parseRules(parser);
        parser.verifyIsDone();

        return new Workflow(name, rules);
    }

    private List<Rule> parseRules(final StringIteratorParser parser) {
        parser.consume('{');

        final var rules = new ArrayList<Rule>();

        rules.add(parseRule(parser));
        while (parser.hasNext() && parser.current() == ',') {
            parser.consume();
            rules.add(parseRule(parser));
        }

        parser.consume('}');
        return List.copyOf(rules);
    }

    private Rule parseRule(final StringIteratorParser parser) {
        final String variableName = parser.parseString();

        return switch (variableName) {
            case "x" -> parseComparisonRule(parser, X);
            case "m" -> parseComparisonRule(parser, M);
            case "a" -> parseComparisonRule(parser, A);
            case "s" -> parseComparisonRule(parser, S);
            default -> new ResultRule(parseResult(variableName));
        };
    }

    private Rule parseComparisonRule(final StringIteratorParser parser, final Category category) {
        final var comparison = parser.current();
        parser.consume();
        final var value = parser.parseInteger();
        parser.consume(':');
        final var result = parseResult(parser.parseString());

        return switch (comparison) {
            case '<' -> new LessThanRule(category, value, result);
            case '>' -> new GreaterThanRule(category, value, result);
            default -> throw new IllegalArgumentException();
        };
    }

    private Result parseResult(final String name) {
        return switch (name) {
            case "A" -> new FinishedResult(true);
            case "R" -> new FinishedResult(false);
            default -> new ForwardResult(name);
        };
    }
}
