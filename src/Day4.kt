import java.io.File
import kotlin.math.pow

fun main() {

    val scratchcards = File("src/Day4.txt").readLines().map {
        it.split(":")[1]
    }.map {
        val (winningNum, numYouHave) = it.split("|")
        Pair(winningNum.parseToInts(), numYouHave.parseToInts())
    }

    val part1 = scratchcards.map {
        it.first.intersect(it.second)
    }.filter {
        it.isNotEmpty()
    }.sumOf {
        2.0.pow(it.size - 1).toInt()
    }

    println(part1)

    val foo = winningNumbersPerGame(scratchcardsSmall)
    println(part2(foo))
}

private fun part2(nums: List<Int>): Int {
    val part2Cheat = nums.foldRight(listOf()) { numMatched: Int, acc: List<Int> ->
        listOf(acc.take(numMatched).sum() + 1) + acc
    }.sum()


    val res2 = buildList<Int> {
        this.addAll(List(nums.size){1})

        for ((idx, numWon) in nums.withIndex()) {
            for(j in idx + 1 .. numWon) {
                this[j] += get(idx)
            }
            println(this)
            println()
        }
    }
    println(res2)
    return part2Cheat
}

private fun winningNumbersPerGame(scratchcards: List<Pair<Set<Int>, Set<Int>>>): List<Int> = scratchcards.map {
    it.first.intersect(it.second).size
}

private fun String.parseToInts() = this.split(' ')
    .filter { it.isNotEmpty() }
    .map { it.toInt() }
    .toSet()


private val scratchcardsSmall = """
    Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
    Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
    Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
    Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
    Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
""".trimIndent().lines()
    .map {
        it.split(":")[1]
    }.map {
        val (winningNum, numYouHave) = it.split("|")
        Pair(winningNum.parseToInts(), numYouHave.parseToInts())
    }