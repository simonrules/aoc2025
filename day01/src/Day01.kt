import java.io.File
import kotlin.math.absoluteValue

class Day01(input: String) {
    val moves: List<Int>

    init {
        moves = File(input).readLines().map { lineToMove(it) }
    }

    private fun lineToMove(line: String): Int {
        val value = line.substring(1).toInt()
        return if (line[0] == 'L') -value else value
    }

    fun part1(): Int {
        var pos = 50
        var zeroCount = 0

        moves.forEach { clicks ->
            pos = (pos + clicks).mod(100)
            if (pos == 0) {
                zeroCount++
            }
        }

        return zeroCount
    }

    fun part2(): Int {
        var pos = 50
        var crossZeroCount = 0
        var endOnZeroCount = 0

        moves.forEach { clicks ->
            val oldPos = pos
            val absPos = pos + clicks
            pos = absPos.mod(100)
            if (pos == 0) {
                // landed on zero
                if (absPos > 0) {
                    // right turn
                    val count = absPos / 100 - 1
                    if (count > 0) {
                        crossZeroCount += count
                    }
                } else if (absPos < 0) {
                    // left turn
                    val count = absPos.absoluteValue / 100
                    if (count > 0) {
                        crossZeroCount += count
                    }
                }
                endOnZeroCount++
            } else if (clicks > 0) {
                // right turn
                if (absPos > 100) {
                    crossZeroCount += absPos / 100
                }
            } else if (clicks < 0) {
                // left turn
                if (absPos < 0) {
                    var count = (-absPos / 100)
                    if (oldPos != 0) {
                        count++
                    }
                    crossZeroCount += count
                }
            }
        }

        return crossZeroCount + endOnZeroCount
    }
}

fun main() {
    val aoc = Day01("day01/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
