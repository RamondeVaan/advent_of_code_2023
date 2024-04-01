package nl.ramondevaan.aoc2023.day02;

public record Cubes(int red, int green, int blue) {
    long power() {
        return ((long) red) * green * blue;
    }

    boolean possibleWithCubes(final Cubes cubes) {
        return red <= cubes.red() && green <= cubes.green() && blue <= cubes.blue();
    }
}
