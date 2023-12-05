import java.io.File

fun main() {
    val schematicSmall = """
       467..114..
       ...*......
       ..35..633.
       ......#...
       617*......
       .....+.58.
       ..592.....
       ......755.
       ...$.*....
       .664.598.
    """.trimIndent().lines()

    val schematic = File("src/Day3.txt").readLines()

    val parts = schematic.mapIndexed() { row, str ->
        str.foldIndexed(listOf(NumAcc())) { col: Int, acc: List<NumAcc>, c: Char ->
            if (c.isDigit()) {
                val (num, cols, _) = acc.last()
                acc.dropLast(1) + NumAcc(num + c, cols + col, row)
            } else {
                acc + NumAcc()
            }
        }.filter { it.num != "" }
    }.flatten()

    parts.forEach { println(it) }

    part1(schematic, parts)
    winningNumbersPerGame(schematic, parts)


}

private fun part1(schematic: List<String>, parts: List<NumAcc>) {
    val joints = schematic.mapIndexed { y, s ->
        s.mapIndexed { x, char -> Triple(char, x, y) }
    }.flatten().filter { !it.first.isDigit() && it.first != '.' }

    joints.forEach { println(it) }

    val sum = parts.filter { part ->
        joints.filter { it.jointTouchesPart(part) }.isNotEmpty()
    }.map { it.num.toInt() }.sum()

    println(sum)
}

private fun winningNumbersPerGame(schematic: List<String>, parts: List<NumAcc>) {
    val joints = schematic.mapIndexed { y, s ->
        s.mapIndexed { x, char -> Triple(char, x, y) }
    }.flatten().filter { it.first == '*' }

    joints.forEach { println(it) }

    val sum = joints.map {
        val partsTouchingGear = it.jointTouchesParts(parts)
        if(partsTouchingGear.size == 2)
            partsTouchingGear[0].num.toInt() * partsTouchingGear[1].num.toInt()
        else
            0
    }.sum()


    println(sum)
}

private data class NumAcc(
    val num: String = "",
    val cols: List<Int> = emptyList(),
    val row: Int = 0,
)

private fun Triple<Char, Int, Int>.jointTouchesParts(parts: List<NumAcc>): List<NumAcc> {
    return parts.filter { jointTouchesPart(it) }
}
private fun Triple<Char, Int, Int>.jointTouchesPart(part: NumAcc): Boolean {
    val xTouches = IntRange(part.cols.first() - 1, part.cols.last() + 1).contains(second)
    val yIsSameAboveOrBelow = third == part.row || third - 1 == part.row || third + 1 == part.row

    return xTouches && yIsSameAboveOrBelow
}

/*val line = "467..114..".foldIndexed(emptyList<Int>()) {

}*/
