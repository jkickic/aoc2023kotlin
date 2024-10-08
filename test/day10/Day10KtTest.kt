package day10

import org.junit.jupiter.api.Test

class Day10KtTest {

    @Test
    fun testMoving() {
        val start = Coordinates(5, 5)
        val wentRight = Coordinates(6, 5)
        val wentLeft = Coordinates(4, 5)
        val wentDown = Coordinates(5, 6)
        val wentUp = Coordinates(5, 4)

        Node('-', start, Direction.LEFT).apply { assert(findNextCoordinates().first == wentLeft) }
        Node('-', start, Direction.RIGHT).apply { assert(findNextCoordinates().first == wentRight) }
        Node('|', start, Direction.UP).apply { assert(findNextCoordinates().first == wentUp) }
        Node('|', start, Direction.DOWN).apply { assert(findNextCoordinates().first == wentDown) }
        Node('F', start, Direction.LEFT).apply { assert(findNextCoordinates().first == wentDown) }
        Node('F', start, Direction.UP).apply { assert(findNextCoordinates().first == wentRight) }
        Node('L', start, Direction.DOWN).apply { assert(findNextCoordinates().first == wentRight) }
        Node('L', start, Direction.LEFT).apply { assert(findNextCoordinates().first == wentUp) }
        Node('J', start, Direction.DOWN).apply { assert(findNextCoordinates().first == wentLeft) }
        Node('J', start, Direction.RIGHT).apply { assert(findNextCoordinates().first == wentUp) }
        Node('7', start, Direction.RIGHT).apply { assert(findNextCoordinates().first == wentDown) }
        Node('7', start, Direction.UP).apply { assert(findNextCoordinates().first == wentLeft) }
    }

    @Test
    fun testInputData() {
        val stepCount = solveDay10("day10/test_input.txt").first
        assert(stepCount == 8)
    }

    @Test
    fun testSolvePart1() {
        val stepCount = solveDay10("day10/input.txt").first
        assert(stepCount == 6786)
    }

    @Test
    fun testInputDataPart2a() {
        val insideCount = solveDay10("day10/test_input_part2a.txt").second
        assert(insideCount == 4)
    }

    @Test
    fun testInputDataPart2b() {
        val insideCount = solveDay10("day10/test_input_part2b.txt").second
        assert(insideCount == 4)
    }

    @Test
    fun testInputDataPart2c() {
        val insideCount = solveDay10("day10/test_input_part2c.txt").second
        assert(insideCount == 8)
    }

    @Test
    fun testInputDataPart2d() {
        val insideCount = solveDay10("day10/test_input_part2d.txt").second
        assert(insideCount == 10)
    }

    @Test
    fun testInputDataPart2() {
        val insideCount = solveDay10("day10/input.txt").second
        assert(insideCount == 495)
    }
}