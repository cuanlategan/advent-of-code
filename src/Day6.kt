fun main() {
    part1()
    part2()
}

private fun part1() {
    testInput
        .lines()
        .map { line ->
            line.split("Time:", "Distance: ", " ")
                .filter { it.isNotBlank() }
                .map { it.toInt() }
        }
        .let { (times, distances) -> times.zip(distances) }
        .map { (time, distance) -> (1..time).filter { (time - it) * it > distance } }
        .map { it.size }
        .reduce { acc, i -> acc * i }
        .also { println(it) }
}

private fun part2() {
    val (time, distance) = testInput
        .lines()
        .map { it.replace(" ", "") }
        .let { (first, second) ->
            first.split("Time:")[1].toLong() to second.split("Distance:")[1].toLong()
        }
        .also { println(it) }

    val min = (1..time).find { (time - it) * it > distance }
    val max = (time downTo min!!).find { (time - it) * it > distance }

    println("$min $max")
    println((max!! - min) + 1)
}

private val testInputSmall = """
    Time:      7  15   30
    Distance:  9  40  200
""".trimIndent()

private val testInput = """
    Time:        53     83     72     88
    Distance:   333   1635   1289   1532
""".trimIndent()