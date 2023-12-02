package day1

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day1KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day1.part1()
        val expected = 53334
        assertEquals(expected, res)
    }

    @Test
    fun calibrationValue() {
        val res = day1.calibrationValue("1abc2")
        val expected = 12
        assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day1.part2()
        val expected = 52834
        assertEquals(expected, res)
    }

    @Test
    fun parseDigit() {
        val res = day1.parseDigit("thfjtb56c")
        val expected = 56
        assertEquals(expected, res)
    }
}