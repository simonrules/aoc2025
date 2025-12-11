import java.io.File
import kotlin.CharArray

class Day04(input: String) {
    private val map: CharArray
    private val width: Int
    private val height: Int

    init {
        val text = File(input).readLines()
        width = text[0].length
        height = text.size
        map = CharArray(width * height)
        for (i in 0 until height) {
            for (j in 0 until width) {
                setMapAt(map, Point2D(j, i), text[j][i])
            }
        }
    }

    private fun getMapAt(m: CharArray, p: Point2D): Char {
        return if (isInBounds(p)) m[p.y * width + p.x] else '.'
    }

    private fun setMapAt(m: CharArray, p: Point2D, value: Char) {
        m[p.y * width + p.x] = value
    }

    private fun isInBounds(p: Point2D): Boolean {
        return ((p.x in 0..<width) && (p.y in 0..<height))
    }

    private fun countNeighbours(m: CharArray, p: Point2D): Int {
        var count = 0
        for (i in -1..1) {
            for (j in -1..1) {
                if ((i == 0 && j == 0)) {
                    continue
                }
                if (getMapAt(m, Point2D(p.x + j, p.y + i)) == '@') {
                    count++
                }
            }
        }
        return count
    }

    private fun getRemovalList(m: CharArray): List<Point2D> {
        val list = mutableListOf<Point2D>()

        for (i in 0..<height) {
            for (j in 0..<width) {
                val p = Point2D(j, i)
                if (getMapAt(m, p) != '@') {
                    continue
                }

                if (countNeighbours(m, p) < 4) {
                    list.add(p)
                }
            }
        }

        return list.toList()
    }

    fun part1(): Int {
        return getRemovalList(map).count()
    }

    fun part2(): Int {
        var count = 0
        val newMap = map.clone()
        do {
            val list = getRemovalList(newMap)
            list.forEach {
                // remove this roll
                setMapAt(newMap, it, '.')
            }
            count += list.count()
        } while (list.count() > 0)

        return count
    }
}

fun main() {
    val aoc = Day04("day04/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}
