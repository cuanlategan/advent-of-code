import java.io.File

fun main() {
    val games = File("src/Day2.txt").readLines()
    part1(games)
    winningNumbersPerGame(games)
}

private fun part1(games: List<String>) {
    val result = parseGamesToListsOfPairs(games)
        .mapIndexed { index, game ->
            if (game.isGamePossible()) index + 1 else 0
        }.sum()

    println(result)
}

private fun winningNumbersPerGame(games: List<String>) {
    val result = parseGamesToListsOfPairs(games)
        .map { it.minNeeded() }
        .map { it.toMap() }
        .sumOf { it.values.reduce { acc, i -> acc * i } }

    println(result)
}

private fun parseGamesToListsOfPairs(games: List<String>) = games
    .map { it.split(":") }
    .map { it.drop(1) }
    .map { inner -> inner.map { it.split(";", ",") }.flatten() }
    .map { it.toGamePairs() }

private fun List<Pair<String, Int>>.minNeeded() = this.sortedBy { it.first }.sortedBy { it.second }

private fun List<Pair<String, Int>>.isGamePossible() = this.all { bagContents[it.first]!! >= it.second }
private fun List<String>.toGamePairs(): List<Pair<String, Int>> = this.map { it.toGamePair() }
private fun String.toGamePair(): Pair<String, Int> =
    this.dropWhile { !it.isLetter() } to this.filter { it.isDigit() }.toInt()

private val bagContents = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

private val gamesShort = """
    Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
    Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
    Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
    Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
""".trimIndent().lines()


//fun addPossibleIDs(sequence: Sequence<String>) = parseLine(sequence)
//fun parseLine(sequence: Sequence<String>) = sequence.mapIndexed { idx, line ->
//    line.substringAfter(": ")
//        .replace(";", ",")
//        .split(", ")
//        .map { it.takeWhile { it != ' ' }.toInt() to it.dropWhile { it != ' ' }.trim() }
//        .filter { bagContents[it.second]!! < it.first }
//
//}


