import java.io.File
import kotlin.math.max
import kotlin.math.min

private fun LongRange.overlap(other: LongRange): Boolean {
    return first.coerceAtLeast(other.first) <=
            last.coerceAtMost(other.last)
}

private fun LongRange.merge(other: LongRange): LongRange {
    return LongRange(min(first, other.first), max(last, other.last))
}

class Day05(input: String) {
    val ranges: List<LongRange>
    val ingredients: List<Long>

    init {
        val parsed = parseInput(input)
        ranges = parsed.first
        ingredients = parsed.second
    }

    private fun toRange(s: String): LongRange {
        val p = s.split('-')
        return LongRange(p[0].toLong(), p[1].toLong())
    }

    private fun parseInput(input: String): Pair<List<LongRange>, List<Long>> {
        val blocks = File(input).readText().trim().split("\n\n")

        val r = blocks[0].trim().split('\n').map { toRange(it) }
        val i = blocks[1].trim().split('\n').map { it.toLong() }

        return Pair(r, i)
    }

    private fun isFresh(id: Long): Boolean {
        ranges.forEach { range ->
            if (id in range) {
                return true
            }
        }

        return false
    }

    fun part1(): Long {
        var count = 0L

        ingredients.forEach {
            if (isFresh(it)) {
                count++
            }
        }

        return count
    }

    fun part2(): Long {
        val sorted = ranges.sortedBy { it.first }.toMutableList()

        // now merge adjacent
        var i = 0
        do {
            if (sorted[i].overlap(sorted[i + 1])) {
                val merged = sorted[i].merge(sorted[i + 1])
                sorted.removeAt(i)
                sorted[i] = merged
            } else {
                i++
            }
        } while (i < sorted.size - 1)

        // then sum all the ranges
        var sum = 0L
        sorted.forEach {
            sum += (it.last - it.first + 1L)
        }

        return sum
    }
}

fun main() {
    val aoc = Day05("day05/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}