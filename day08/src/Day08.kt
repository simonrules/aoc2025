import java.io.File

class Day08(input: String) {
    private val junction = File(input).readLines().map {
        val parts = it.split(",")
        Point3D(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    }
    private val distances = findDistances()

    data class Point3DDistances(val a: Point3D, val b: Point3D, val distance: Double)

    private fun findDistances(): List<Point3DDistances> {
        val distances = mutableListOf<Point3DDistances>()

        for (i in junction.indices) {
            for (j in i + 1 until junction.size) {
                val dist = junction[i].euclideanSquare(junction[j])
                distances.add(Point3DDistances(junction[i], junction[j], dist))
            }
        }

        return distances.sortedBy { it.distance }
    }

    private fun mergeCircuits(
        circuits: MutableList<MutableSet<Point3D>>,
        pointToCircuit: MutableMap<Point3D, Int>,
        aCircuit: Int, bCircuit: Int
    ) {
        val bJunctions = circuits[bCircuit]
        for (j in bJunctions) {
            pointToCircuit[j] = aCircuit
        }
        circuits[aCircuit].addAll(circuits[bCircuit])
        circuits[bCircuit].clear()
    }

    fun part1(): Int {
        val circuits = mutableListOf<MutableSet<Point3D>>()
        val pointToCircuit = mutableMapOf<Point3D, Int>()
        for (j in junction) {
            circuits.add(mutableSetOf(j))
            pointToCircuit[j] = circuits.lastIndex
        }

        repeat(1000) { index ->
            val pair = distances[index]

            val aCircuit = pointToCircuit[pair.a]!!
            val bCircuit = pointToCircuit[pair.b]!!

            if (aCircuit != bCircuit) {
                mergeCircuits(circuits, pointToCircuit, aCircuit, bCircuit)
            }
        }

        val sorted = circuits.sortedBy { it.size }.reversed()

        return sorted[0].size * sorted[1].size * sorted[2].size
    }

    fun part2(): Long {
        val circuits = mutableListOf<MutableSet<Point3D>>()
        val pointToCircuit = mutableMapOf<Point3D, Int>()
        for (j in junction) {
            circuits.add(mutableSetOf(j))
            pointToCircuit[j] = circuits.lastIndex
        }
        var circuitCount = circuits.size
        var index = 0

        while (circuitCount > 1) {
            val pair = distances[index]
            index++

            val aCircuit = pointToCircuit[pair.a]!!
            val bCircuit = pointToCircuit[pair.b]!!

            if (aCircuit != bCircuit) {
                mergeCircuits(circuits, pointToCircuit, aCircuit, bCircuit)
                circuitCount--
                if (circuitCount == 1) {
                    // last pair just merged
                    return pair.a.x.toLong() * pair.b.x.toLong()
                }
            }
        }

        return 0
    }
}

fun main() {
    val aoc = Day08("day08/input.txt")
    println(aoc.part1())
    println(aoc.part2())
}