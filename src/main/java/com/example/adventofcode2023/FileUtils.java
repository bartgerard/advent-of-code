package com.example.adventofcode2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

public final class FileUtils {
    private FileUtils() {
        // no-op
    }

    public static List<String> read(final String filename) {
        try (final InputStream is = ClassLoader.getSystemResourceAsStream(filename)) {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(requireNonNull(is)));
            return reader.lines().toList();
        } catch (final IOException e) {
            return emptyList();
        }
    }
}
