package be.gerard.aoc2023.day20

import be.gerard.aoc.util.signal.Pulse
import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class PulseModulesTest extends Specification {

    def "total of low pushes multiplied by the number of high pushes after pressing the button multiple times"() {
        when:
        final PulseModules modules = PulseModules.parse(lines)
        final Frequencies frequencies = modules.pulsesAfter(repetitions)
        final long lowAndHighMultiplied = frequencies.multiplied()

        then:
        lowAndHighMultiplied == expectedResult

        where:
        lines                           | repetitions | expectedResult | comment
        fromFile("day20/example_1.txt") | 1000        | 32000000       | ""
        fromFile("day20/example_2.txt") | 1000        | 11687500       | ""
        fromFile("day20/input.txt")     | 1000        | 666795063      | ""
    }

    def "qsdf"() {
        when:
        final PulseModules modules = PulseModules.parse(lines)
        final Map<String, Integer> pushes = modules.distancesFor(Set.of(PulseModule.Type.BROADCASTER, PulseModule.Type.CONJUNCTION))

        then:
        pushes == expectedResult

        where:
        lines                       | expectedResult | comment
        fromFile("day20/input.txt") | []             | ""
    }

    def "number of pushes until module rx receives a single low pulse"() {
        when:
        final PulseModules modules = PulseModules.parse(lines)
        final int pushes = modules.numberOfPushesRequiredForModuleToReceive(moduleName, frequency, Pulse.LOW)

        then:
        pushes == expectedResult

        where:
        lines                       | moduleName | frequency | expectedResult | comment
        fromFile("day20/input.txt") | "pp"       | 1         | 2              | ""
        fromFile("day20/input.txt") | "rg"       | 1         | 2              | ""
        fromFile("day20/input.txt") | "zp"       | 1         | 2              | ""
        fromFile("day20/input.txt") | "sj"       | 1         | 2              | ""
        fromFile("day20/input.txt") | "gp"       | 1         | 3833           | ""
        fromFile("day20/input.txt") | "xp"       | 1         | 4057           | ""
        fromFile("day20/input.txt") | "xl"       | 1         | 4051           | ""
        fromFile("day20/input.txt") | "ln"       | 1         | 4021           | ""
        fromFile("day20/input.txt") | "df"       | 1         | 4021           | ""
        fromFile("day20/input.txt") | "rx"       | 1         | 1              | ""
    }
}
