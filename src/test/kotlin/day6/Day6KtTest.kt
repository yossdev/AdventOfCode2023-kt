package day6

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Day6KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day6.part1()
        val expected = 512295.toBigInteger()
        assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day6.part2()
        val expected = 36530883.toBigInteger()
        assertEquals(expected, res)
    }
}