import java.io.File

class Day02(input: String) {
    val ranges: List<Pair<Long, Long>>

    init {
        ranges = File(input).readText().split(",").map { rangeToPair(it) }
    }

    private fun rangeToPair(range: String): Pair<Long, Long> {
        val parts = range.split('-')
        return Pair(parts[0].toLong(), parts[1].toLong())
    }

    private fun isValid(digit: Long): Boolean {
        // Look for any ID which is made only of some sequence of digits repeated twice.
        // So, 55 (5 twice), 6464 (64 twice), and 123123 (123 twice) would all be invalid IDs.
        // None of the numbers have leading zeroes; 0101 isn't an ID at all.
        // 101 is a valid ID that you would ignore.

        val digitString = digit.toString()
        if (digitString.length % 2 == 1) {
            // odd length strings are always valid
            return true
        }

        val left = digitString.substring(0, digitString.length / 2).toLong()
        val right = digitString.substring(digitString.length / 2).toLong()

        return left != right
    }

    fun part1(): Long {
        var sum = 0L
        ranges.forEach { range ->
            for (i in range.first..range.second) {
                if (!isValid(i)) {
                    sum += i
                }
            }
        }

        return sum
    }

    private fun isValid2(digit: Long): Boolean {
        // An ID is invalid if it is made only of some sequence of digits repeated at least twice.
        // So, 12341234 (1234 two times), 123123123 (123 three times), 1212121212 (12 five times),
        // and 1111111 (1 seven times) are all invalid IDs.

        val digitString = digit.toString()
        if (digitString.length == 1) {
            return true
        }

        for (i in 1..digitString.length / 2) {
            if (digitString.length % i == 0) {
                // divides with no remainder
                val list = digitString.chunked(i)
                val firstElement = list[0]

                if (list.all { it == firstElement }) {
                    // all elements are the same
                    return false
                }
            }
        }

        return true
    }

    fun part2(): Long {
        var sum = 0L
        ranges.forEach { range ->
            for (i in range.first..range.second) {
                if (!isValid2(i)) {
                    sum += i
                }
            }
        }

        return sum
    }
}

fun main() {
    val aoc = Day02("day02/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}