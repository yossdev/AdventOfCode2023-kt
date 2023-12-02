package day2

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2KtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun part1() {
        val res = day2.part1()
        val expected = 2716
        assertEquals(expected, res)
    }

    @Test
    fun part2() {
        val res = day2.part2()
        val expected = 72227
        assertEquals(expected, res)
    }
}