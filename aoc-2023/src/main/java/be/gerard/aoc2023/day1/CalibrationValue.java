package be.gerard.aoc2023.day1;


import be.gerard.aoc.util.Line;
import be.gerard.aoc.util.Lines;
import be.gerard.aoc.util.Numbers;
import be.gerard.aoc.util.Tokens;

public record CalibrationValue(
        int value
) {
    static final String NUMBER_REGEX = "\\d";
    static final String LENIENT_NUMBER_REGEX = "(?=(\\d|one|two|three|four|five|six|seven|eight|nine)).";

    static Lines<CalibrationValue> calibrationValues(
            final Lines<String> lines
    ) {
        return calibrationValues(lines, NUMBER_REGEX);
    }

    static Lines<CalibrationValue> lenientCalibrationValues(
            final Lines<String> lines
    ) {
        return calibrationValues(lines, LENIENT_NUMBER_REGEX);
    }

    private static Lines<CalibrationValue> calibrationValues(
            final Lines<String> lines,
            final String numberRegex
    ) {
        return lines.mapLine(Line::new)
                .map(line -> line.tokenize(numberRegex)
                        .firstAndLast()
                        .map(Numbers::parseInt)
                )
                .map(CalibrationValue::from);
    }

    static CalibrationValue from(final Tokens<Integer> tokens) {
        return new CalibrationValue(tokens.first() * 10 + tokens.last());
    }
}
