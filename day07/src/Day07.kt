import java.io.File

class Day07(input: String) {
    val splitters = mutableListOf<Set<Int>>()
    var startX = -1
    var width = -1

    init {
        // load the graph
        File(input).forEachLine { line ->
            val s = line.indexOf('S')
            if (s != -1) {
                startX = s
            }
            if (width == -1) {
                width = line.length
            }
            val row = mutableSetOf<Int>()
            if (line.contains('^')) {
                line.forEachIndexed { index, c ->
                    if (c == '^') {
                        row.add(index)
                    }
                }
            }
            if (row.isNotEmpty()) {
                splitters.add(row.toSet())
            }
        }
    }

    fun part1(): Int {
        var tachyons = setOf(startX)
        var splits = 0

        splitters.forEach { row ->
            val newTachyons = mutableSetOf<Int>()
            tachyons.forEach { x ->
                if (row.contains(x)) {
                    splits++
                    newTachyons.add(x - 1)
                    newTachyons.add(x + 1)
                } else {
                    // Same x position
                    newTachyons.add(x)
                }
            }
            tachyons = newTachyons
        }

        return splits
    }

    private fun part2Recurse(point: Point2D, paths: MutableMap<Point2D, Long>): Long {
        if (paths.contains(point)) {
            // already been here, so return memoised value
            return paths[point]!!
        }

        if (point.y == splitters.size) {
            // reached the end
            paths[point] = 1
        } else if (splitters[point.y].contains(point.x)) {
            // go left/right
            paths[point] = part2Recurse(Point2D(point.x - 1, point.y + 1), paths) +
                    part2Recurse(Point2D(point.x + 1, point.y + 1), paths)
        } else {
            paths[point] = part2Recurse(Point2D(point.x, point.y + 1), paths) // down
        }

        return paths[point]!!
    }

    fun part2(): Long {
        val start = Point2D(startX, 0)
        val paths = mutableMapOf<Point2D, Long>()

        return part2Recurse(start, paths)
    }
}

fun main() {
    val aoc = Day07("day07/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}