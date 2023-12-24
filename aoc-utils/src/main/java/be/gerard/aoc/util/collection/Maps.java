package be.gerard.aoc.util.collection;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public final class Maps {
    private Maps() {
        // no-op
    }

    public static <K, V> Map<K, V> put(
            final Map<K, V> map,
            final K key,
            final V value
    ) {
        final Map<K, V> mutableMap = new HashMap<>(map);
        mutableMap.put(key, value);
        return unmodifiableMap(mutableMap);
    }
}
