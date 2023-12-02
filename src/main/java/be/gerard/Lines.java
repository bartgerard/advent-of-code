package be.gerard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import static java.util.Objects.requireNonNull;

public final class Lines {
    private Lines() {
        // no-op
    }

    public static List<String> fromFile(final String uri) {
        try (final InputStream is = ClassLoader.getSystemResourceAsStream(uri)) {
            return toLines(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("uri is invalid", e);
        }
    }

    public static List<String> fromUrl(final String url) {
        try (final InputStream is = new URL(url).openStream()) {
            return toLines(is);
        } catch (final IOException e) {
            throw new IllegalArgumentException("url is invalid", e);
        }
    }

    private static List<String> toLines(final InputStream is) {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(requireNonNull(is)));
        return reader.lines().toList();
    }
}
