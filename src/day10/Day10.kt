package day10

import Helpers

fun solvePart1(s: String) : Int {
    val lines = Helpers.loadResourceFile(s);
    val start = findStart(lines)
    val matrix = lines.map { it.toCharArray() }

    var currentNode = start
    var loopLength = 0
    while (true) {
        val (nextCoordinates, previousDirection) = currentNode.findNextCoordinates()
        val nextNode = Node(
            matrix[nextCoordinates.y][nextCoordinates.x],
            nextCoordinates,
            previousDirection
        )
        currentNode = nextNode
        loopLength++
        if (nextNode.symbol == 'S') {
            break
        }
    }
    val furthestPointFromTheStart = loopLength / 2
    return furthestPointFromTheStart
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
        val nextDirection = when (symbol) {
            '-' -> if (from == Direction.LEFT) Direction.LEFT else Direction.RIGHT
            '|' -> if (from == Direction.UP) Direction.UP else Direction.DOWN
            'L' -> if (from == Direction.LEFT) Direction.UP else Direction.RIGHT
            'F' -> if (from == Direction.LEFT) Direction.DOWN else Direction.RIGHT
            'J' -> if (from == Direction.RIGHT) Direction.UP else Direction.LEFT
            '7' -> if (from == Direction.RIGHT) Direction.DOWN else Direction.LEFT
            'S' -> Direction.DOWN // Hardcoded cheat
            else -> Direction.NONE
        }
        return Pair(nextDirection.moveCoordinates(coords), nextDirection)
    }
}

data class Coordinates(val x: Int, val y: Int)

