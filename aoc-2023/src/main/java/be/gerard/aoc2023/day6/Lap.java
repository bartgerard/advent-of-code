package be.gerard.aoc2023.day6;

import java.util.stream.LongStream;

record Lap(
        long duration,
        long currentRecordDistance
) {
    long margin() {
        final long halfTime = duration / 2;
        final boolean isEven = duration % 2 == 0;

        final long recordBreakingStartSpeed = LongStream.rangeClosed(currentRecordDistance / duration + 1, halfTime)
                .filter(this::isNewRecord)
                .findFirst()
                .orElse(-1);

        if (recordBreakingStartSpeed < 0 && !isEven) {
            return isNewRecord(duration - halfTime) ? 1 : 0;
        }

        final long modifier = isEven ? 1 : 0;

        return (halfTime - recordBreakingStartSpeed + 1) * 2 - modifier;
    }

    private boolean isNewRecord(final long startSpeed) {
        return toDistance(startSpeed) > currentRecordDistance;
    }

    private long toDistance(final long startSpeed) {
        return startSpeed * (duration - startSpeed);
    }
}
