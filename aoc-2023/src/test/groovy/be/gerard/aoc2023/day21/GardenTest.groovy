package be.gerard.aoc2023.day21


import spock.lang.Specification

import static be.gerard.aoc.util.input.Lines.fromFile

class GardenTest extends Specification {

    def "how many garden plots could the Elf reach in exactly #steps steps"() {
        when:
        final Garden counter = Garden.parse(lines)
        final long numberOfPlots = counter.numberOfPlotsReachableAfter(steps)

        then:
        numberOfPlots == expectedResult

        where:
        lines                                  | steps | expectedResult | comment
        fromFile("day21/example_1.txt")        | 6     | 16             | ""
        fromFile("day21/input.txt")            | 64    | 3768           | ""

        fromFile("day21/extra_1_expanded.txt") | 1     | 4              | ""
        fromFile("day21/extra_1_expanded.txt") | 2     | 9              | ""
        fromFile("day21/extra_1_expanded.txt") | 3     | 16             | ""
        fromFile("day21/extra_1_expanded.txt") | 4     | 25             | ""
        fromFile("day21/extra_1_expanded.txt") | 5     | 36             | ""
        fromFile("day21/extra_1_expanded.txt") | 6     | 49             | ""

        fromFile("day21/extra_3_expanded.txt") | 1     | 4              | ""
        fromFile("day21/extra_3_expanded.txt") | 2     | 5              | ""
        fromFile("day21/extra_3_expanded.txt") | 3     | 8              | ""
        fromFile("day21/extra_3_expanded.txt") | 4     | 17             | ""
        fromFile("day21/extra_3_expanded.txt") | 5     | 20             | ""
        fromFile("day21/extra_3_expanded.txt") | 6     | 25             | ""
    }

    def "how many garden plots could the Elf reach in exactly #steps steps in an infinite garden"() {
        when:
        final Garden counter = Garden.parse(lines)
        final long numberOfPlots = counter.numberOfPlotsReachableAfter(steps)

        then:
        numberOfPlots == expectedResult

        where:
        lines                           | steps | expectedResult | comment
        //fromFile("day21/extra_1.txt")   | 1        | 4              | ""
        //fromFile("day21/extra_1.txt")   | 2        | 9              | ""
        //fromFile("day21/extra_1.txt")   | 3        | 16             | ""
        //fromFile("day21/extra_1.txt")   | 4        | 25             | ""
        //fromFile("day21/extra_1.txt")   | 5        | 36             | ""
        //fromFile("day21/extra_1.txt")   | 6        | 49             | ""

        //fromFile("day21/example_1.txt") | 6     | 16             | ""
        fromFile("day21/example_1.txt") | 10       | 50             | ""
        //fromFile("day21/example_1.txt") | 50       | 1594           | ""
        //fromFile("day21/example_1.txt") | 100      | 6536           | ""
        //fromFile("day21/example_1.txt") | 500      | 167004         | ""
        //fromFile("day21/example_1.txt") | 1000     | 668697         | ""
        //fromFile("day21/example_1.txt") | 5000     | 16733044       | ""

        //fromFile("day21/input.txt") | 26501365 | 3768           | "65 + 131 * 202300"
    }

    // [4,8,14,24,33,48,59,76,85,106,120,147,169,198,224,260,287,320,353,394,430,465,499,544,587,642,683,744,782,842,881,947,999,1080,1127,1208,1260,1339,1396,1484,1542,1629,1693,1789,1852,1946,2015,2107,2184,2278,2359,2449,2538,2642,2732,2843,2934,3056,3148,3261,3356,3508,3617,3768,3877,4032,4145,4304,4396,4553,4647,4811,4914,5086,5187,5357,5465,5636,5743,5927,6036,6236,6334,6533,6631,6835,6931,7149,7254,7466,7580,7801,7917,8147,8251,8496,8598,8843,8958,9199,9317,9551,9683,9915,10057,10297,10451,10688,10844,11066,11237,11459,11634,11848,12022,12258,12438,12697,12877,13120,13309,13552,13749,13982,14189,14420,14645,14866,15105,15327,15569,15812,16043,16275,16517,16756,17017,17242,17505,17723,18011,18219,18519,18743,19039,19270,19560,19787,20069,20305,20614,20850,21147,21367,21702,21933,22277,22492,22840,23033,23397,23593,23969,24178,24589,24785,25180,25396,25770,26001,26390,26608,27000,27225,27622,27848,28234,28467,28856,29119,29493,29760,30116,30400,30780,31060,31456,31738,32149,32427,32812,33092,33555,33886,34347,34674,35135,35466,35931,36216,36645,36922,37361,37664,38121,38408,38854,39154,39590,39886,40344,40640,41130,41398,41884,42144,42643,42908,43433,43706,44216,44507,45028,45310,45853,46108,46674,46925,47486,47757,48301,48573,49102,49404,49922,50251,50778,51123,51632,51979,52458,52836,53319,53695,54156,54527,55030,55412,55957,56333,56842,57230,57732,58136,58617,59039,59513,59970,60416,60897,61342,61825,62308,62766,63224,63698,64167,64674,65113,65622,66049,66604,67006,67579,68005,68568,69004,69548,69975,70506,70945,71522,71958,72517,72923,73548,73967,74600,74989,75624,75972,76640,76993,77679,78045,78786,79131,79840,80220,80889,81294,81984,82362,83059,83445,84143,84532,85210,85607,86293,86742,87396,87849,88471,88950,89606,90076,90757,91230,91930,92394,93051,93516,94290,94843,95614,96159]

    def "special"() {
        when:
        final long numberOfPlots = Garden.special(b0, b1, b2, n)

        then:
        numberOfPlots == expectedResult

        where:
        b0   | b1    | b2    | n      | expectedResult  | comment
        22   | 99    | 427   | 50     | 731730879386267 | ""
        4145 | 16275 | 64167 | 202300 | 731730879386267 | "26501365 = 65 + 131 * 202300" // < 731730879386267, 169593238552881? 627957743627204?
    }

}
