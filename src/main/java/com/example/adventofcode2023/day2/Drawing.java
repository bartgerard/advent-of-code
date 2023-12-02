package com.example.adventofcode2023.day2;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toUnmodifiableMap;

record Drawing(
        Map<String, Integer> cubesByColour
) {
    static Drawing parse(final String draws) {
        return new Drawing(Arrays.stream(draws.split(", "))
                .map(draw -> draw.split(" "))
                .collect(toUnmodifiableMap(
                        draw -> draw[1],
                        draw -> Integer.parseInt(draw[0])
                ))
        );
    }
}
