package day5

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day5KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day5.part1()
        val expected = 806029445.toBigInteger()
        assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day5.part2()
        val expected = 59370572.toBigInteger()
        assertEquals(expected, res)
    }

    @Test
    fun partitionToBuckets() {
        val res = day5.partitionToBuckets(10, 14.toBigInteger())
        val range = 1.toBigInteger()
        val remainder = range + 4.toBigInteger() - 1.toBigInteger()
        val expected = listOf(range,range,range,range,range,range,range,range,range,remainder)
        assertEquals(expected, res)

        val res2 = day5.partitionToBuckets(10, 56865175.toBigInteger())
        val range2 = 5686517.toBigInteger()
        val remainder2 = range2 + 5.toBigInteger() - 1.toBigInteger()
        val expected2 = listOf(range2,range2,range2,range2,range2,range2,range2,range2,range2,remainder2)
        assertEquals(expected2, res2)
    }
}