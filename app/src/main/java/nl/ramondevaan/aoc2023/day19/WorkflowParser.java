package nl.ramondevaan.aoc2023.day19;

import nl.ramondevaan.aoc2023.util.Parser;
import nl.ramondevaan.aoc2023.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

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
            case "x", "m", "a", "s" -> parseComparisonRule(parser, variableName);
            default -> new ResultRule(parseResult(variableName));
        };
    }

    private Rule parseComparisonRule(final StringIteratorParser parser, final String variable) {
        final var comparison = parser.current();
        parser.consume();
        final var value = parser.parseInteger();
        parser.consume(':');
        final var result = parseResult(parser.parseString());

        return switch (comparison) {
            case '<' -> getLessThanRule(variable, value, result);
            case '>' -> getGreaterThanRule(variable, value, result);
            default -> throw new IllegalArgumentException();
        };
    }

    private LessThanRule getLessThanRule(final String variable, final int value, final Result result) {
        return switch (variable) {
            case "x" -> new LessThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.x();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getX();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withX(range);
                }
            };
            case "m" -> new LessThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.m();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getM();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withM(range);
                }
            };
            case "a" -> new LessThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.a();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getA();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withA(range);
                }
            };
            case "s" -> new LessThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.s();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getS();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withS(range);
                }
            };
            default -> throw new IllegalArgumentException();
        };
    }

    private GreaterThanRule getGreaterThanRule(final String variable, final int value, final Result result) {
        return switch (variable) {
            case "x" -> new GreaterThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.x();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getX();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withX(range);
                }
            };
            case "m" -> new GreaterThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.m();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getM();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withM(range);
                }
            };
            case "a" -> new GreaterThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.a();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getA();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withA(range);
                }
            };
            case "s" -> new GreaterThanRule(value, result) {
                @Override
                protected int getValue(final PartRating partRating) {
                    return partRating.s();
                }

                @Override
                protected Range getRange(final PartRatingRange partRatingRange) {
                    return partRatingRange.getS();
                }

                @Override
                protected PartRatingRange withRange(final PartRatingRange partRatingRange, final Range range) {
                    return partRatingRange.withS(range);
                }
            };
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
