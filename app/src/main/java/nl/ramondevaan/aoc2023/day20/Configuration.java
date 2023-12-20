package nl.ramondevaan.aoc2023.day20;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class Configuration {
    private final Module broadcaster;
    private final List<Module> modules;
    private final Map<Module, List<Module>> connections;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Map<String, Module> modules = new HashMap<>();
        private Map<String, Collection<String>> connections = new HashMap<>();

        public Builder add(final Module module) {
            modules.put(module.name(), module);
            return this;
        }

        public Builder add(final String from, final Collection<String> to) {
            connections.put(from, to);
            return this;
        }

        public Configuration build() {
            connections.values().stream()
                    .flatMap(Collection::stream)
                    .filter(not(modules::containsKey))
                    .forEach(name -> modules.put(name, new NoopModule(name)));
            final var moduleConnections = connections.entrySet().stream().collect(toUnmodifiableMap(
                    entry -> modules.get(entry.getKey()),
                    entry -> entry.getValue().stream()
                            .map(name -> modules.get(name))
                            .toList()));
            final var modulesList = modules.values().stream().toList();
            final var broadcaster = modules.get("broadcaster");
            this.modules = null;
            this.connections = null;
            return new Configuration(broadcaster, modulesList, moduleConnections);
        }
    }
}
