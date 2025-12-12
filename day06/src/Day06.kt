import java.io.File

class Day06(val input: String) {
    val columns = mutableListOf<MutableList<Long>>()
    val operators: List<Char>

    init {
        val lines = File(input).readLines()
        lines.subList(0, lines.size - 1).forEach { line ->
            val numbers = line.trim().split(Regex("\\s+")).map { it.toLong() }
            if (columns.isEmpty()) {
                repeat(numbers.size) {
                    columns.add(mutableListOf())
                }
            }
            numbers.forEachIndexed { i, number ->
                columns[i].add(number)
            }

        }
        operators = lines[lines.lastIndex].trim().split(Regex("\\s+")).map { it[0] }
    }

    fun part1(): Long {
        var sum = 0L

        for (j in operators.indices) {
            val op = operators[j]
            var calc = columns[j][0]
            if (op == '*') {
                for (i in 1 until columns[j].size) {
                    calc *= columns[j][i]
                }
            } else if (op == '+') {
                for (i in 1 until columns[j].size) {
                    calc += columns[j][i]
                }
            }
            sum += calc
        }

        return sum
    }

    private fun readColumn(text: List<String>, col: Int): String {
        val output = StringBuilder()
        for (i in 0 until text.size) {
            val c = text[i][col]
            if (c != ' ') {
                output.append(c)
            }
        }
        return output.toString()
    }

    fun part2(): Long {
        var sum = 0L
        val text = File(input).readLines()
        val cols = text[0].length

        val numbers = mutableListOf<Long>()

        for (i in cols - 1 downTo 0) {
            val n = readColumn(text, i)
            if (n.isEmpty()) {
                // start a new calculation
                numbers.clear()
            } else if (n[n.lastIndex].isDigit()) {
                numbers.add(n.toLong())
            } else {
                val op = n[n.lastIndex]
                numbers.add(n.dropLast(1).toLong())
                if (op == '*') {
                    sum += numbers.reduce { acc, element -> acc * element }
                } else {
                    sum += numbers.sum()
                }
            }
        }
        return sum
    }
}

fun main() {
    val aoc = Day06("day06/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}