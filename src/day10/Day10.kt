package day10

import Helpers
import kotlin.math.max
import kotlin.math.min

private val CORNERS = listOf("F", "L", "J", "7", "S")

fun solveDay10(s: String) : Pair<Int, Int> {
    val lines = Helpers.loadResourceFile(s);
    val start = findStart(lines)
    val matrix = lines.map { it.toCharArray() }

    var currentNode = start
    var loopLength = 0
    var allLoopPoints = ArrayList<Coordinates>()
    while (true) {
        val (nextCoordinates, previousDirection) = currentNode.findNextCoordinates()
        val nextNode = Node(
            matrix[nextCoordinates.y][nextCoordinates.x],
            nextCoordinates,
            previousDirection
        )
        currentNode = nextNode
        allLoopPoints += nextNode.coords
        loopLength++
        if (nextNode.symbol == 'S') {
            break
        }
    }
    val furthestPointFromTheStart = loopLength / 2

    val pointsInside = countPointsInside(allLoopPoints, matrix)
    return Pair(furthestPointFromTheStart, pointsInside)
}

private const val START = "S"


fun findStart(lines: List<String>): Node {
    val y = lines.indexOfFirst { it.contains(START) }
    val x = lines[y].indexOf(START)
    return Node('S', Coordinates(x, y), Direction.NONE)
}

enum class Direction {
    NONE, UP, DOWN, LEFT, RIGHT;

    fun moveCoordinates(c: Coordinates): Coordinates {
        return when (this) {
            UP -> c.copy(y = c.y - 1)
            DOWN -> c.copy(y = c.y + 1)
            LEFT -> c.copy(x = c.x - 1)
            RIGHT -> c.copy(x = c.x + 1)
            else -> c
        }
    }
}

data class Node(val symbol: Char, val coords: Coordinates, val from: Direction) {

    fun findNextCoordinates(): Pair<Coordinates, Direction> {
        val dir = when (symbol) {
            '-' -> if (from == Direction.LEFT) Direction.LEFT else Direction.RIGHT
            '|' -> if (from == Direction.UP) Direction.UP else Direction.DOWN
            'L' -> if (from == Direction.LEFT) Direction.UP else Direction.RIGHT
            'F' -> if (from == Direction.LEFT) Direction.DOWN else Direction.RIGHT
            'J' -> if (from == Direction.RIGHT) Direction.UP else Direction.LEFT
            '7' -> if (from == Direction.RIGHT) Direction.DOWN else Direction.LEFT
            'S' -> Direction.DOWN // Hardcoded cheat
            else -> Direction.NONE
        }
        return Pair(dir.moveCoordinates(coords), dir)
    }
}

data class Coordinates(val x: Int, val y: Int)

fun countPointsInside(allLoopPoints: java.util.ArrayList<Coordinates>, matrix: List<CharArray>): Int {
    var counter = 0
    for (y in 0 until matrix.size) {
        for (x in 0 until matrix[y].size) {
            val pointCheck = Coordinates(x, y)
            if (!allLoopPoints.contains(pointCheck) && pointInPolygon(pointCheck, allLoopPoints)) {
                counter++
            }
        }
    }
    return counter
}

//https://www.naukri.com/code360/library/check-if-a-point-lies-in-the-interior-of-a-polygon
fun pointInPolygon(coordinate: Coordinates, polygonVerticies: ArrayList<Coordinates>): Boolean {
    val x = coordinate.x
    val y = coordinate.y
    var inside = false
    var currentVertex = polygonVerticies.first()
    for (i in 1..<polygonVerticies.size + 1) {
        var nextVertex = polygonVerticies[i % polygonVerticies.size]

        if (y > min(currentVertex.y, nextVertex.y) && y <= max(currentVertex.y, nextVertex.y)) {
            if (x <= max(currentVertex.x, nextVertex.x)) {
                val xIntersection = ((y - currentVertex.y) * (nextVertex.x - currentVertex.x) / (nextVertex.y - currentVertex.y) + currentVertex.x)

                if (currentVertex.x == nextVertex.x || x <= xIntersection) {
                    inside = !inside
                }
            }
        }
        currentVertex = nextVertex
    }
    return inside
}
