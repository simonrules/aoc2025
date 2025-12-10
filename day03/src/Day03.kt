import java.io.File

data class Joltage(val amount: String): Comparable<Joltage> {
    override fun compareTo(other: Joltage): Int {
        if (amount == other.amount) {
            return 0
        }

        val diff = amount.length - other.amount.length
        if (diff != 0) {
            return diff
        }

        // same length, so compare numbers starting with most significant digit
        for (i in amount.indices) {
            val diff = amount[i].digitToInt() - other.amount[i].digitToInt()
            if (diff != 0) {
                return diff
            }
        }

        return 0
    }
}

class Day03(input: String) {
    val banks = File(input).readLines()

    private fun simpleMaxJoltage(bank: String): Long {
        // take all but the final digit (no point in finding the highest there)
        var subStr = bank.take(bank.length - 1)
        // sort so that the highest digit is last
        val firstDigit = subStr.toCharArray().sorted().joinToString("").takeLast(1)
        // find the first index of the highest digit
        val index = bank.indexOf(firstDigit)
        // from here, find the next highest digit
        subStr = bank.substring(index + 1)
        val secondDigit = subStr.toCharArray().sorted().joinToString("").takeLast(1)

        return "$firstDigit$secondDigit".toLong()
    }

    fun part1(): Long {
        return banks.sumOf { simpleMaxJoltage(it) }
    }

    private fun reduceBank(bank: String): String {
        var maxJoltage = Joltage("0")
        var newBank = ""
        for (i in bank.indices) {
            // try turning one off at a time
            val left = bank.take(i)
            val right = bank.substring(i + 1)
            val joltage = Joltage("$left$right")
            if (joltage > maxJoltage) {
                maxJoltage = Joltage(joltage.amount)
                newBank = "$left$right"
            }
        }

        return newBank
    }

    fun part2(): Long {
        return banks.sumOf { bank ->
            var newBank = bank

            // greedy algorithm: reduce the bank length by 1 until it reaches 12
            while (newBank.length > 12) {
                newBank = reduceBank(newBank)
            }
            newBank.toLong()
        }
    }
}

fun main() {
    val aoc = Day03("day03/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
