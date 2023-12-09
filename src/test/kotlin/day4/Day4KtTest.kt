package day4

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day4KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day4.part1()
        val expected = 15268
        assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day4.part2()
        val expected = 6283755
        assertEquals(expected, res)
    }
}