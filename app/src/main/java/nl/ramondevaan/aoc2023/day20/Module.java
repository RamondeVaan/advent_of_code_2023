package nl.ramondevaan.aoc2023.day20;

import java.util.Optional;

public interface Module {

//    public final List<PulseRequest> apply(final Pulse pulse, final Memory.Operations operations) {
//        return applyImpl(pulse, operations).stream()
//                .flatMap(type -> destinationModules.stream().map(destination -> new PulseRequest(type, destination)))
//                .toList();
//    }

    String name();

    Optional<PulseType> apply(final Pulse pulse, final Memory.Operations operations);
}
