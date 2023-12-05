import java.io.File

data class AlmanacMap(val lines: List<AlmanacLine>) {
    fun findDestination(source: Long): Long {
        val line = lines.find { it.containsSource(source) }
        return if (line != null) {
            val res = line.destinationStart + (source - line.sourceStart)
            res
        } else {
            source
        }
    }

    fun findSource(destination: Long?): Long? {
        if (destination == null) return null
        val line = lines.find { it.containsDestination(destination) }
        return if (line != null) {
            val res = line.sourceStart + (destination - line.destinationStart)
            res
        } else {
            null
        }
    }


}

data class AlmanacLine(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    fun containsSource(source: Long) = (sourceStart..<sourceStart + rangeLength).contains(source)
    fun containsDestination(desination: Long) = (destinationStart..<destinationStart + rangeLength).contains(desination)
}

private fun List<String>.toAlmanacMap() = AlmanacMap(this.map { it.toAlmanacLine() }.sortedBy { it.destinationStart })
private fun String.toAlmanacLine() = this.split(" ")
    .map { it.toLong() }
    .let {
        AlmanacLine(it[0], it[1], it[2])
    }

fun main() {
    part1()
    part2()
}

private fun part1() {
    val almanac = File("src/Day5Test.txt")
        .readText()

    val seeds = almanac
        .split("seeds: ")[1]
        .split('\n')[0]
        .split(" ")
        .map { it.toLong() }

    //val seeds = listOf(26L)

    val seedToSoil = almanac
        .split("seed-to-soil map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val soilToFertilizer = almanac
        .split("soil-to-fertilizer map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val fertilizerToWater = almanac
        .split("fertilizer-to-water map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val waterToLight = almanac
        .split("water-to-light map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val lightToTemp = almanac
        .split("light-to-temperature map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val tempToHumidity = almanac
        .split("temperature-to-humidity map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val humidityToLocation = almanac
        .split("humidity-to-location map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    seeds
        .map { seedToSoil.findDestination(it) }
        .map { soilToFertilizer.findDestination(it) }
        .map { fertilizerToWater.findDestination(it) }
        .map { waterToLight.findDestination(it) }
        .map { lightToTemp.findDestination(it) }
        .map { tempToHumidity.findDestination(it) }
        .map { humidityToLocation.findDestination(it) }
        .minBy { it }
        .also { println(it) }
}

private fun part2() {

    val almanac = File("src/Day5.txt")
        .readText()

    val seedRanges = almanac
        .split("seeds: ")[1]
        .split('\n')[0]
        .split(" ")
        .map { it.toLong() }
        .windowed(2, 2)
        .map { it[0]..<it[0] + it[1] }

    val seeds = sequence {
        for (range in seedRanges) {
            yieldAll(range)
        }
    }

    println("b")
    val seedToSoil = almanac
        .split("seed-to-soil map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val soilToFertilizer = almanac
        .split("soil-to-fertilizer map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val fertilizerToWater = almanac
        .split("fertilizer-to-water map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val waterToLight = almanac
        .split("water-to-light map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val lightToTemp = almanac
        .split("light-to-temperature map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val tempToHumidity = almanac
        .split("temperature-to-humidity map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    val humidityToLocation = almanac
        .split("humidity-to-location map:\n")[1]
        .split("\n")
        .takeWhile { it.isNotEmpty() }
        .toAlmanacMap()

    println("c")


    ///seeds.drop(26).toList()
    seeds
        //.also { println(it) }
        .map { seedToSoil.findDestination(it) }
        //.also { println(it) }
        .map { soilToFertilizer.findDestination(it) }
        .map { fertilizerToWater.findDestination(it) }
        .map { waterToLight.findDestination(it) }
        //.also { println(it) }
        .map { lightToTemp.findDestination(it) }
        .map { tempToHumidity.findDestination(it) }
        //.also { println(it) }
        .map { humidityToLocation.findDestination(it) }
        //.toList()
        //.also { println(it) }
        .minBy { it }
        .also { println(it) }
}

/*seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4*/
