package com.example.adventofcode2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class FileUtils {
    private FileUtils() {
        // no-op
    }

    public static List<String> read(final String filename) {
        try (final InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(filename)) {
            final BufferedReader br = new BufferedReader(new InputStreamReader(requireNonNull(is)));
            return br.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
