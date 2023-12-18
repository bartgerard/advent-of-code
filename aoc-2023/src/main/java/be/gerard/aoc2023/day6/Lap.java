package be.gerard.aoc2023.day6;

import be.gerard.aoc.util.math.LongRange;

import java.util.stream.LongStream;

record Lap(
        long duration,
        long currentRecordDistance
) {
    long margin() {
        final long halfTime = duration / 2;
        final boolean isEven = duration % 2 == 0;

        final long recordBreakingStartSpeed = LongStream.rangeClosed(minimalRequiredSpeedToBreakRecord(), halfTime)
                .filter(this::isNewRecord)
                .findFirst()
                .orElse(0);

        if (recordBreakingStartSpeed == 0) {
            return 0;
        }

        //    [1, 2, 3, 4, 5] with >= 2 m/s
        // -> [   *  *      ]

        //    [1, 2, 3, 4, 5, 6] with >= 2 m/s
        // -> [   *  *  *      ]
        // (6 - 3) should not be counted twice
        final long modifier = isEven ? 1 : 0;

        return LongRange.of(recordBreakingStartSpeed, halfTime).length() * 2 - modifier;
    }

    private long currentMaxRecordSpeed() {
        // 10m in 5s => v_current_max = 2 m/s
        return currentRecordDistance / duration;
    }

    private long minimalRequiredSpeedToBreakRecord() {
        return currentMaxRecordSpeed() + 1;
    }

    private boolean isNewRecord(final long startSpeed) {
        return toDistance(startSpeed) > currentRecordDistance;
    }

    private long toDistance(final long startSpeed) {
        return startSpeed * (duration - startSpeed);
    }
}
